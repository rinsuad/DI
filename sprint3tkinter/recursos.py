import requests
from PIL import Image, ImageTk
from io import BytesIO

# Download an image from a URL and return it as a PhotoImage object
# TODO: Cambiar la URL de las imagenes
def descargar_imagen(url):
    response = requests.get(url)
    image_data = BytesIO(response.content)
    image = Image.open(image_data)
    return ImageTk.PhotoImage(image)
