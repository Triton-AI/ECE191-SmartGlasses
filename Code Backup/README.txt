This doc will outline how to install/run and provide valuable links for development.

Android Studio Folder
This directory contains scripts necessary to develop an android app for edge detection. This requires an installation of Android Studio (we used Version 4.0).

Python Scripts Folder 
This directory contains scripts for edge detection, stock yolov5 model, and OCR. 
OCR.py - converts image-to-mp3 files
OCR_box.py - draws recognition boxes on the image, for all the detected characters.
Note: use the command "python detect.py --weights yolov5s.pt --source 0"  inside the yolov5-master folder to deploy the stock object detection model that sources frames from your computers webcam.

Unity Code Folder
This directory contains all of the unity code as well as the SDK assets provided by SnapDragon Spaces as well as the OpenCVForUnity library necessary for A3 camera access.

Useful Links:

OpenCVSharp - library docs: https://shimat.github.io/opencvsharp_docs/html/d69c29a1-7fb1-4f78-82e9-79be971c3d03.htm 

OpenCVForUnity - library docs: https://enoxsoftware.github.io/OpenCVForUnity/3.0.0/doc/html/namespace_open_c_v_for_unity.html

Github for Yolov5 source: https://github.com/ultralytics/yolov5

SnapDragon Spaces SDK docs: https://docs.spaces.qualcomm.com/unity/setup/SetupGuideUnity.html