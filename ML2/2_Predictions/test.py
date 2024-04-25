import cv2
import numpy as np
import os
import yaml
from yaml.loader import SafeLoader
from tflite_runtime.interpreter import Interpreter

modelPath = "Model2/weights/best-fp16.tflite"
interpreter = Interpreter(model_path = modelPath)
interpreter.allocate_tensors()

input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()

image_path = 'iloveyoutest.jpg'
image = cv2.imread(image_path)