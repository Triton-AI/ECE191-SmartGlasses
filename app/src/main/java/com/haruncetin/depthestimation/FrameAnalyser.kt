package com.haruncetin.depthestimation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceView
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc


// Image Analyser for performing depth estimation on the selected camera frames.
@ExperimentalGetImage
class FrameAnalyser(
    private var depthModel : MidasNetSmall,
    private var depthView: SurfaceView
) : ImageAnalysis.Analyzer {

    var metrics: DisplayMetrics? = null

    private var inferenceTime: Long = 0
    private var mLastTime: Long = 0
    private var fps = 0
    private var ifps:Int = 0
    init{
        metrics = MainActivity.applicationContext().resources.displayMetrics
    }

    private var readyToProcess = true

    override fun analyze(image: ImageProxy) {

        // If the analyser is not ready to process the current frame, skip it.
        if ( !readyToProcess ) {
            image.close()
            return
        }

        readyToProcess = false

        if (image.image != null) {
            Log.i(MainActivity.APP_LOG_TAG, "Image.format: %d, Image.width: %d, Image.height: %d".format(image.image!!.format, image.image!!.width, image.image!!.height))
            val bitmap = image.image!!.toBitmap(image.imageInfo.rotationDegrees)
            image.close()
            CoroutineScope( Dispatchers.Main ).launch {
                run(bitmap)
            }
        }
    }

    private fun draw(imageDepth: Bitmap, imageEdge: Bitmap) {
        val canvas: Canvas = depthView.holder.lockCanvas() ?: return
        val now: Long = System.currentTimeMillis()
        synchronized(depthView.holder) {
            val scaledDepth: Bitmap = Bitmap.createScaledBitmap(
                imageDepth,
                depthView.width,
                depthView.height,
                true
            )
            val scaledEdge: Bitmap = Bitmap.createScaledBitmap(
                imageEdge,
                depthView.width,
                depthView.height,
                true
            )

            // Convert all edges from EdgeMap to overlay white lines in DepthMap
            val edgePixels = IntArray(scaledEdge.height * scaledEdge.width)
            val depthPixels = IntArray(scaledDepth.height * scaledDepth.width)

            scaledEdge.getPixels(edgePixels, 0, scaledEdge.width,0,0, scaledEdge.width, scaledEdge.height)
            scaledDepth.getPixels(depthPixels, 0, scaledDepth.width,0,0, scaledDepth.width, scaledDepth.height)
            for (i in edgePixels.indices) {
                if (edgePixels[i] == Color.WHITE) {
                    depthPixels[i] = Color.WHITE // Option to change this to any color the developer desires
                }
            }
            scaledDepth.setPixels(depthPixels, 0, scaledDepth.width,0,0, scaledDepth.width, scaledDepth.height)

            // Draw overlay
            canvas.drawBitmap(scaledDepth, 0f, 0f, null)

            // Write FPS data to canvas
            val paint = Paint()
            paint.color = Color.RED
            paint.isAntiAlias = true
            paint.textSize = 14f * (metrics!!.densityDpi/160f) // 14dp
            canvas.drawText("Inference Time : $inferenceTime ms", 50f, 80f, paint)
            canvas.drawText("FPS : $fps", 50f, 150f, paint)
            depthView.holder.unlockCanvasAndPost(canvas)
        }
        ifps++
        if(now > (mLastTime + 1000)) {
            mLastTime = now
            fps = ifps
            ifps = 0
        }
    }

    private suspend fun run(inputImage : Bitmap) = withContext(Dispatchers.Default) {

        // Compute the depth for the given frame Bitmap
        val depthOutput = depthModel.getDepthMap(inputImage)
        inferenceTime = depthModel.getInferenceTime()

        // Copy & convert inputImage to Mat from Bitmap
        val bmpEdge = inputImage.copy(Bitmap.Config.ARGB_8888, true)

        // Initialize OpenCV
        if(OpenCVLoader.initDebug()){
            val temp = Mat()
            val mat = Mat()

            // Run edge detection on inputImage
            Utils.bitmapToMat(bmpEdge, temp)
            Imgproc.cvtColor(temp, mat, Imgproc.COLOR_RGBA2GRAY)
            Imgproc.Canny(mat, mat, 150.0, 200.0)
            Utils.matToBitmap(mat, bmpEdge)

            // convert depthMap to Inferno colormap
            Utils.bitmapToMat(depthOutput, temp)
            Imgproc.cvtColor(temp, temp, Imgproc.COLOR_RGBA2RGB)
            Core.bitwise_not(temp, temp)
            Imgproc.applyColorMap(temp, mat, Imgproc.COLORMAP_INFERNO)
            Utils.matToBitmap(mat, depthOutput)
        }

        withContext( Dispatchers.Main ) {

            // Draw the depth Bitmap to the SurfaceView.
            // Please refer to the draw function for details.
            draw(depthOutput, bmpEdge)

            // Notify that the current frame is processed and
            // the pipeline is ready for the next frame.
            readyToProcess = true

        }
    }
}