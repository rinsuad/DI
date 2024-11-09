import requests
from PIL import Image, ImageTk
from io import BytesIO

def descargar_imagen(url):
    response = requests.get(url) # Send a GET request to the URL
    image_data = BytesIO(response.content) # Get the image data from the response content
    image = Image.open(image_data) # Open the image using PIL
    return ImageTk.PhotoImage(image) # Return the image as a PhotoImage object