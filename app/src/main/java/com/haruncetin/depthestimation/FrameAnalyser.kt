package com.haruncetin.depthestimation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.media.Image.Plane
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.imgproc.Imgproc
import java.nio.ByteBuffer


// Image Analyser for performing depth estimation on the selected camera frames.
@ExperimentalGetImage
class FrameAnalyser(
    private var depthModel : MidasNetSmall,
    private var depthView: SurfaceView,
    private var zoom: Int
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

    @RequiresApi(Build.VERSION_CODES.P)
    override fun analyze(image: ImageProxy) {

        // If the analyser is not ready to process the current frame, skip it.
        if ( !readyToProcess ) {
            image.close()
            return
        }

        readyToProcess = false

        if (image.image != null) {
            Log.i(MainActivity.APP_LOG_TAG, "Image.format: %d, Image.width: %d, Image.height: %d".format(image.image!!.format, image.image!!.width, image.image!!.height))
            val imageProxy = image

            val buffer: ByteBuffer = imageProxy.planes[0].buffer
            val pixelStride: Int = imageProxy.planes[0].pixelStride
            val rowStride: Int = imageProxy.planes[0].rowStride
            val rowPadding = rowStride - pixelStride * imageProxy.width

            val rgbBitmap = Bitmap.createBitmap(
                imageProxy.width + rowPadding / pixelStride,
                imageProxy.height,
                Bitmap.Config.ARGB_8888
            )

            rgbBitmap.copyPixelsFromBuffer(buffer)

            image.close()
            CoroutineScope( Dispatchers.Main ).launch {
                run(rgbBitmap)
            }
        }
    }

    private fun draw(imageDepth: Bitmap, imageEdge: Bitmap) {
        val canvas: Canvas = depthView.holder.lockCanvas() ?: return
        val now: Long = System.currentTimeMillis()
        synchronized(depthView.holder) {

            canvas.drawBitmap(imageEdge, 0f, 0f,null)

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

        val bmpZoomed = inputImage

// Initialize OpenCV
        if (OpenCVLoader.initDebug()) {
            val temp = Mat()
            val mat = Mat()
            val factor = zoom +1
            // Convert inputImage to Mat from Bitmap
            Utils.bitmapToMat(bmpZoomed, temp)
            val centerX = temp.cols() / 2
            val centerY = temp.rows() / 2
            val cropWidth = temp.cols() / factor
            val cropHeight = temp.rows() / factor

            // Crop the image from the center
            val croppedMat = Mat(temp, Rect(centerX - cropWidth/2, centerY - cropHeight/2 , cropWidth, cropHeight))

            // Resize the cropped image using resize function
             Imgproc.resize(croppedMat, mat, temp.size())

            // Convert the resized Mat back to Bitmap
            Utils.matToBitmap(mat, bmpZoomed)
        }

// Use the bmpZoomed bitmap for further processing or display

        withContext( Dispatchers.Main ) {

            // Draw the depth Bitmap to the SurfaceView.
            // Please refer to the draw function for details.
            draw(bmpZoomed,inputImage )

            // Notify that the current frame is processed and
            // the pipeline is ready for the next frame.
            readyToProcess = true

        }
    }
}