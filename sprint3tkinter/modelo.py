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
        sizes = {  # Board sizes for each difficulty
            "facil": (2, 4),
            "medio": (4, 4),
            "dificil": (4, 6)
        }
        size = sizes[self.difficulty]
        num_pairs = (size[0] * size[1]) // 2
        cards = [f"card{i}" for i in range(1, num_pairs + 1)] * 2
        random.shuffle(cards)

        board = [cards[i * size[1]:(i + 1) * size[1]] for i in range(size[0])]
        print("Generated board:", board)  # Debug print
        return board

    def _load_images(self):
        # Donwload the hidden card image
        self.hidden_image = descargar_imagen(
            "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/hidden_card.JPG")

        # Download the images for the cards
        self.card_images = [
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
        ]

        # Indicar que las imágenes se han descargado
        self.images_loaded.set()

    def load_images_thread(self):
        # Execute the _load_images method in a new thread
        threading.Thread(target=self._load_images).start()
