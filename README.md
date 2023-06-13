# TensorFlow Examples

<div align="center">
  <img src="https://www.tensorflow.org/images/tf_logo_social.png" /><br /><br />
</div>

<h2>Most important links!</h2>

* [Community examples](./community)
* [Course materials](./courses/udacity_deep_learning) for the [Deep Learning](https://www.udacity.com/course/deep-learning--ud730) class on Udacity

If you are looking to learn TensorFlow, don't miss the
[core TensorFlow documentation](http://github.com/tensorflow/docs)
which is largely runnable code.
Those notebooks can be opened in Colab from
[tensorflow.org](https://tensorflow.org).

<h2>What is this repo?</h2>

This is the repo for running OCR on detected cars via object detection. When the application detects a car or a bus it crops the image based on the bounding box and runs OCR on the cropped image. It doesn't display detected non-car objects. It is capable of running OCR, however performance is poor and it doesn't display the text on the UI. Object deteciton and OCR happens in ObjectDetectorHelper.kt and UI drawing happens in OverlayView.kt. The OCR being executed is in the OCRModelExecutor.kt file that uses the "execute" method.

This repo was worked on by Behrad, Shubham and Leo. Our emails are as follows:

Leo: ihpark@ucsd.edu

* Showcase examples and documentation for our fantastic [TensorFlow Community](https://tensorflow.org/community)
* Provide examples mentioned on TensorFlow.org
* Publish material supporting official TensorFlow courses
* Publish supporting material for the [TensorFlow Blog](https://blog.tensorflow.org) and [TensorFlow YouTube Channel](https://youtube.com/tensorflow)

