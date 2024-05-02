SignIt – Team High Five

***
Project Overview
***
The "SignIt" mobile Android application can accurately translate American Sign Language in real time. The programming languages used in this system are Python, Java, and Kotlin. Android Studio and XML layouts are used on the front end, and a YOLOv5 Machine Learning Model, created and trained in Python notebooks, is used on the back end. The framework for Machine Learning Model integration came from Aarohi Singla’s Object Detection application. Firebase is used as the traditional log-in authentication and log-in data storage and Google is used for Google Account log-ins.


Key Features:

The key features of the SignIt mobile Android application include:

* Customizable Machine Learning Model: The app can allow users to take or upload a picture and analyze it using computer vision techniques to identify facial expressions that may indicate depression.
* Account Log-in: The app should be designed with robust security measures to protect user data.
* Live Sign Language and Hand Gesture Translation: By allowing the end-user to provide feedback on the services they have used, the program ensures quality control.


Project Design
***
Architecture Overview

The SignIt mobile application was built in two sections, the front-end application implemented in Android Studio, and the back-end Machine Learning Model created in Python notebooks. The trained model was converted from a YOLOv5 model into a Tensorflow Lite format for integration within Android Studio. The model was then inserted into the application.

Front-end Design
***
The front-end of the SignIt mobile application was designed using Java and Kotlin within an Android Studio environment. The front-end design includes the following:
***
* User Interface: The user interface is designed to be intuitive and user-friendly,
* Account Creation (Sign-up) Page: The application allows a user to create an individual account to access the application’s features.
* Traditional and Google-based Log-In Methods: The application provides the traditional email and password log-in as well as an alternative Google-based log-in capabilities.
* Home Page: The home page includes an “About” page, a “How-To” Guide, a button for device camera access, and a settings page.
***
Back-end Design
***
The back end of the SignIt web application is designed in Python notebooks for easy developer customization. The back-end design includes the following:

* YOLOv5 Machine Learning Model: The YOLOv5 model consists of individual Backbone, Neck, and Head networks that form a processing chain. The model was created using Keras, OpenCV, PyTorch, and Tensorflow frameworks to initialize the YOLOv5 stacked networks.

Developer Deployment:
***
Machine Learning Model:
Required Dependencies:
1. Python (3.9 recommended)
2. NumPy
3. Pandas
4. Matplotlib
5. OpenCV-python
6. Jupyter
7. LabelImg
8. YOLOv5 model
Adding Custom Datasets
The dependencies needed for data collecting and labeling are included in “ML2/requirement.txt”
Run “pip install requirement.txt” to install those dependencies.

Use the LabelImg library to label the data. After labeling, a corresponding XML file will be generated. That xml file includes important information needed for the next step.

The YOLOv5 model takes specific input data. As for the object detection, please refer to “01_extract_object_info_from_xml.ipynb” to see the data conversion process.
***
Recommendations for data image collecting:
1. Avoid blurred images
2. Use Hi-resolution images
3. Avoid background clutter
***
Recommendations for data labeling:
1. Label the gesture(object) at the appropriate box size, to include just the hands.
***
Testing Model with Webcam

Due to YOLOv5’s architecture, the actual model training process is relatively easy. Before the training, run “pip install -r requirement.txt” to install the dependencies. All of the dependencies are listed in “yolov5/requirement.txt” file.

Please refer to “yolo_train/yolo_train.ipynb” for training code. I used Google Colab for the model training because Google Colab provides a GPU connection that can be used as an alternative if the machine you are using has a weak or no GPU support. After the training, the model can be saved in different formats, please refer to Yolo official document for details.

Model Implementation and Testing:

The following steps are the team’s implementation of the model on a Windows machine, once the training was completed.

Save the model in onnx format. 

Xi wrote a helper class name “yolo_predictions.py”.
Please import this class in your implementation then call the predictions function to use the model. The model takes an image as input. In addition, you can use OpenCV to invoke the camera on your machine and feed the image captured to the model (video is just a stream of images). Now, the real-time predictions and labels should be displayed.

Model Training Expanding:
You can train the model to recognize more gestures by taking more pictures of different gestures. You can also improve the accuracy of the model by collecting more data with higher quality images or train the model with a longer duration.




Deployment in Android Studio:
***
Source Code Link: https://github.com/htmw/2024S-Async/tree/main/app

Deploying the Android Application:

•	Download Github Desktop
•	Clone the Repository (https://github.com/htmw/2024S-Async)
•	Install Android Studio (version used: Hedgehog 2023.1.1 Patch 2)
•	Open in Android Studio and Allow Gradle and Libraries to Sync and Update
•	Build and run application using an Android Mobile Device

Installing Customized Dataset Classifications and Model:
***
•	Navigate to the Asset’s folder: /app/src/main/assets
•	Delete the “customclasses.txt” file for the default classification and add-in the new customized class .txt file if you made a new file. If you just updated the existing “customclasses.txt” file, you can leave it.
•	Delete the default model
•	Add the updated model to the folder
•	Change the path in the first ‘if’ statement of the “DetectorFactory.java” class file: 
if (modelFilename.equals("yolov5s.tflite")) {
•	Build and run application using an Android Mobile Device (will not run properly on desktop)


