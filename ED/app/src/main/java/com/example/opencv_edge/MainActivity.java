package com.example.opencv_edge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.osgi.OpenCVInterface;

import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity {

    CameraBridgeViewBase cameraBridgeViewBase;

    Mat gray;
//    Mat gaussFilter;
    Size gaussKernelDims;
    double x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getPermission();

        cameraBridgeViewBase = findViewById(R.id.cameraView);

        cameraBridgeViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {

            }

            @Override
            public void onCameraViewStopped() {

            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                gray = inputFrame.gray();

                /*
                If a pixel gradient is higher than threshold2, the pixel is accepted as an
                edge
                If a pixel gradient value is below threshold1, then it is rejected.
                If the pixel gradient is between the two thresholds, then it will be accepted only
                if it is connected to a pixel that is above the upper threshold.
                Canny recommended a upper:lower ratio between 2:1 and 3:1.
                 */
                Imgproc.Canny(gray,gray, 250,450);  // originally had 150, 200
//                tried 180, 500
/*
                Core.flip(gray, gray, 0);
                Core.flip(gray, gray, 1);
                Core.flip(gray, gray, -1);
*/
//                Log.d("opencv_edge", gray.getClass().getName());
//                Log.d("opencv_edge", "gray is " + gray.height() + "tall by " + gray.width() + "wide");
//                gaussFilter = new Mat(gray.height(), gray.width(), 1) ;
                x = gray.height();
                y = gray.width();
                gaussKernelDims = new Size((x+1)/100, (y+1)/100);
                Imgproc.GaussianBlur(gray, gray, gaussKernelDims, x/100, y/100);

                return gray;
            }
        });

    if(OpenCVLoader.initDebug()){
        cameraBridgeViewBase.enableView();
        }
    }
    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList(){
        return Collections .singletonList(cameraBridgeViewBase);
    }
    void getPermission(){
        if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 101);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]!=PackageManager.PERMISSION_GRANTED){
            getPermission();

        }
    }
}
