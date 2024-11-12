import requests
from PIL import Image
from io import BytesIO


def descargar_imagen(url):
    try:
        # Send a GET request to the URL
        response = requests.get(url, timeout=5)
        # Raise an error for bad status codes
        response.raise_for_status()
        if response.content:
            # Read the image data from the response content
            image_data = BytesIO(response.content)
            # Open the image using PIL
            image = Image.open(image_data)
            return image
        else:
            print(f"Error fetching image from {url}: No content found")
    except requests.RequestException as e:
        # Print an error message if there's a request exception
        print(f"Error fetching image from {url}: {e}")
    except Image.UnidentifiedImageError as e:
        # Print an error message if the image cannot be identified
        print(f"Error identifying image from {url}: {e}")
    return None
