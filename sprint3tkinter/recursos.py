import requests
from PIL import Image
from io import BytesIO

def descargar_imagen(url):
    try:
        response = requests.get(url)
        response.raise_for_status()  # Raise an error for bad status codes
        image_data = BytesIO(response.content)
        image = Image.open(image_data)
        return image
    except requests.RequestException as e:
        print(f"Error fetching image from {url}: {e}")
    except Image.UnidentifiedImageError as e:
        print(f"Error identifying image from {url}: {e}")
    return None