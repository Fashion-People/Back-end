from django.shortcuts import render
# Create your views here.

from django.core.files.storage import FileSystemStorage

import requests

#model을 위한 준비과정
import io
import time
import json
import torch 
import torchvision
import numpy as np
import matplotlib.pyplot as plt

from torchvision import models
from torchvision import transforms

from django.conf import settings
#from PIL import image

import torch.nn as nn
import torch.optim as optim

from PIL import Image

device = torch.device('cpu')

img_height, img_width = 224,224

transforms_test = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
])

with open('./models/imagenet_classes.json','r') as f:
    labelInfo=f.read()
labelInfo=json.loads(labelInfo)

model_path='./models/fashion_model_6.pth'
loaded_model = torch.load(model_path)

model = loaded_model
num_features = model.fc.in_features
# 전이 학습(transfer learning): 모델의 출력 뉴런 수를 3개로 교체하여 마지막 레이어 다시 학습
model.fc = nn.Linear(num_features, 2)
model = model.to(device)

criterion = nn.CrossEntropyLoss()
optimizer = optim.SGD(model.parameters(), lr=0.001, momentum=0.9)


def index(request):
    context={'a':1}
    return render(request,'index.html',context)

def predictImage(request):
    print(request)
    print (request.POST.dict())
    #print (request.FILES['filePath'])
    #file obj => 파일 이름
    fileobj = (request.FILES['filePath'])
    fs = FileSystemStorage()
    filePathName = fs.save(fileobj.name,fileobj)
    filePathName = fs.url(filePathName)
    
    #image_url = 'https://fashionbucket.s3.ap-northeast-2.amazonaws.com/profile/image/20240128190555194.jpg'
    #image_url = 'http://127.0.0.1:8000/media/slxm_OLbTRZH.jpg'
    #image_url = 'https://media.bunjang.co.kr/product/224788073_1_1684729916_w360.jpg'
    
    #반팔
    image_url='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQe_OK_L4zyRWYfL7sQXNu0jGzaNlwgfxbvKQ&usqp=CAU' 
    
    #니트
    #image_url='https://web.joongna.com/cafe-article-data/live/2024/01/15/1035339901/1705305954360_000_DXO5P_main.jpg'
    response = requests.get(image_url)
    image_bytes = response.content

    # BytesIO를 사용하여 이미지 불러오기
    image = Image.open(io.BytesIO(image_bytes))
    image = transforms_test(image).unsqueeze(0).to(device)
    # 이미지 표시
    #image.show()


    
    #image_bytes = fileobj.read()

    #image = Image.open(io.BytesIO(image_bytes))
    #image = transforms_test(image).unsqueeze(0).to(device)

    #up_image = Image.open(io.BytesIO(image_bytes))
    #up_image.save("./static/img.jpg","jpeg")
    
    #print(filePathName)
    #testimage='.'+image_bytes
    #img = Image.open(testimage)
    #x=imshow(testimage)


    with torch.no_grad():
       model.eval()
       output=model(image)
    # 이미지 저장하는 것 (현재 파일에 올려둔 이미지)
    #context={'filePathName':filePathName}
       
    #predictImage =torch.argmax(output[0]).item()
    _, preds = torch.max(output, 1)

    if (preds[0].item()==0):
        predictImage='knit'
    elif (preds[0].item()==1):
        predictImage='sleeve'    


    context={'filePathName':filePathName,'predictImage':predictImage}
    return render(request,'index.html',context)

    




