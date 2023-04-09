import pytesseract
from pytesseract import Output
import cv2

img = cv2.imread('imagetext1.jpg')

pytesseract.pytesseract.tesseract_cmd = r"C:\Users\miker\AppData\Local\Programs\Tesseract-OCR\tesseract.exe"
d = pytesseract.image_to_data(img, output_type=Output.DICT)
for i in range(0, len(d["text"])):
      
    # We can then extract the bounding box coordinates
    # of the text region from  the current result
    x = d["left"][i]
    y = d["top"][i]
    w = d["width"][i]
    h = d["height"][i]
      
    # We will also extract the OCR text itself along
    # with the confidence of the text localization
    text = d["text"][i]
    conf = int(d["conf"][i])
      
    # filter out weak confidence text localizations
 
    text = "".join(text).strip()
    cv2.rectangle(img,
                      (x, y),
                      (x + w, y + h),
                      (0, 0, 255), 2)
    cv2.putText(img,
                    text,
                    (x, y - 10), 
                    cv2.FONT_HERSHEY_SIMPLEX,
                    1.2, (0, 255, 255), 3)
          
# After all, we will show the output image
cv2.imshow("Image", img)
cv2.waitKey(0)