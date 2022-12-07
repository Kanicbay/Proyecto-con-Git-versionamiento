import os
import numpy as np
import random
from PIL import Image, ImageEnhance
from keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.utils import load_img
import cv2
from keras.models import load_model
import sys

def normalize_image(path):
    image_size = 128
    image = load_img(path, target_size=(image_size, image_size))
    image = Image.fromarray(np.uint8(image))
    image = ImageEnhance.Brightness(image).enhance(random.uniform(0.9,0.9))
    image = ImageEnhance.Contrast(image).enhance(random.uniform(1.5,1.5))
    image = np.array(image)
    return image

if __name__ == '__main__':

    #Path actual
    path_modelo = "src\\python\\BestModelFinal.h5"
    
    #Recibir foto
    path = [el for el in list(map(str, sys.argv[1:]))]  

    path_image = "src\\archivos\\imagen_a_predecir.jpg"

    #Normalizar foto
    img = normalize_image(path_image)
    input_array = np.array([img])
    
    #Cargar modelo
    modeloTumor = load_model(path_modelo)
    
    #Prediccion
    prediccion = modeloTumor.predict(input_array)
    resultado = "Cancer Tumor prediction " + str(prediccion[0][0]) + "%"
    print(resultado)
    
    
