# pip install webdriver-manager selenium
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import urllib.request

#from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service

chrome_options = webdriver.ChromeOptions()
#driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()),options=chrome_options)
driver = webdriver.Chrome()
URL='https://www.google.co.kr/imghp'
driver.get(url=URL)
driver.implicitly_wait(time_to_wait=10)
keyElement=driver.find_element(By.XPATH,'//*[@id="APjFqb"]')
keyElement.send_keys('가디건')
keyElement.send_keys(Keys.RETURN)


bodyElement = driver.find_element(By.TAG_NAME,'body')
time.sleep(5)

for i in range(1):
    bodyElement.send_keys(Keys.PAGE_DOWN)
    time.sleep(0.2)


images = driver.find_elements(By.XPATH,'//*[@id="islrg"]/div[1]/div/a[1]')
#print(len(images))

imageSeq=1
#//*[@id="Sva75c"]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div/div/div[3]/div[1]/a/img[1]
#//*[@id="Sva75c"]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div/div/div[2]/div/a/img[1]
#//*[@id="Sva75c"]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div/div/div[3]/div[1]/a/img[1]

for image in images:
    image.click()
    time.sleep(0.5)
    highImages = driver.find_elements(By.XPATH,'//*[@id="Sva75c"]/div[2]/div[2]/div[2]/div[2]/c-wiz/div/div/div/div/div/div/a/img[1]')
    realImage=highImages[0].get_attribute('src')

    try:
        urllib.request.urlretrieve(realImage,'C:/Users/kangb/IdeaProjects/Image/가디건/'+str(imageSeq)+'.jpg')
        imageSeq +=1
    except:
        pass