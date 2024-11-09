import random
import threading
from recursos import descargar_imagen

class GameModel:
    def __init__(self, difficulty):
        self.difficulty = difficulty
        self.board = self._generate_board()
        self.hidden_image = None
        self.card_images = []
        self.images_loaded = threading.Event()  # Event to indicate that the images have been downloaded

    def _generate_board(self):
        pairs = { # Number of pairs for each difficulty, modify as needed
            "facil": 6,
            "medio": 12,
            "dificil": 16
        }
        num_pairs = pairs[self.difficulty]
        cards = [f"card{i}" for i in range(1, num_pairs + 1)] * 2
        random.shuffle(cards)

        size = (2, num_pairs)
        return [cards[i * size[1]:(i + 1) * size[1]] for i in range(size[0])]

    def _load_images(self):
        # Download hidden card image
        #TODO: Cambiar la URL de la imagen de la carta oculta
        self.hidden_image = descargar_imagen("https://github.com/user/images/hidden_card.png")

        # Download card images
        for i in range(1, 5):
            #TODO: Cambiar la URL de las imágenes de las cartas
            image = descargar_imagen(f"https://github.com/user/images/card{i}.png")
            self.card_images.append(image)

        # Indicates that the images have been downloaded
        self.images_loaded.set()

    def load_images_thread(self):
        # Execute the _load_images method in a new thread
        threading.Thread(target=self._load_images).start()
