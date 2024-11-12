from datetime import datetime
import random
import threading
import time

from recursos import descargar_imagen


class GameModel:
    def __init__(self, difficulty, controller):
        # Initialize the game model
        self.difficulty = difficulty
        self.controller = controller
        self.board = self._generate_board()  # Generate the game board
        self.hidden_image = None
        self.card_images = []
        self.images_loaded = threading.Event()  # Event to indicate that the images have been downloaded
        self.time_elapsed = 0
        self.timer_running = False
        self.move_count = 0

    # Generate the board based on the difficulty
    def _generate_board(self):
        sizes = {  # Board sizes for each difficulty
            "facil": (2, 4),  # 2x4 board (8 cards)
            "medio": (4, 4),  # 4x4 board (16 cards)
            "dificil": (4, 6)  # 4x6 board (24 cards)
        }
        size = sizes[self.difficulty]  # Get the size based on the difficulty
        num_pairs = (size[0] * size[1]) // 2  # Number of pairs

        # Ensure we only use cards from card1 to card8
        available_cards = [f"card{i}" for i in range(1, 9)]

        # Repeat the available cards to ensure we have enough pairs
        cards = (available_cards * ((num_pairs // len(available_cards)) + 1))[:num_pairs] * 2
        random.shuffle(cards)  # Shuffle the cards

        # Build the board from the shuffled cards
        board = [cards[i * size[1]:(i + 1) * size[1]] for i in range(size[0])]
        print("Generated board:", board)  # Debug print
        return board

    # Load the images for the cards
    def _load_images(self):
        # Download the hidden card image
        self.hidden_image = descargar_imagen(
            "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/hidden_card.JPG")

        # Download the images for the cards
        self.card_images = [
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card1.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card2.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card3.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card4.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card5.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card6.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card7.JPG"),
            descargar_imagen(
                "https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint3tkinter/assets/card8.JPG"),
        ]

        # Verify that all images have been downloaded
        if self.hidden_image is None or any(img is None for img in self.card_images):
            print("Error al descargar una o más imágenes.")
            return

        # Indicate that the images have been downloaded
        self.images_loaded.set()

    # Load the images in a new thread to avoid blocking the main thread
    def load_images_thread(self):
        threading.Thread(target=self._load_images).start()

    # Function to start the timer
    def start_timer(self):
        self.timer_running = True
        self.timer_thread = threading.Thread(target=self._run_timer)
        self.timer_thread.start()

    def _run_timer(self):
        while self.timer_running:
            time.sleep(1)
            self.time_elapsed += 1

    # Function to update the time label with the elapsed time
    def update_time(self):
        self.controller.game_view.update_time_label(self.time_elapsed)

    # Save the player's score
    def save_score(self, player_name):
        score = {
            "name": player_name,
            "difficulty": self.difficulty,
            "moves": self.move_count,
            "date": datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        }
        scores = self.load_scores()
        scores[self.difficulty].append(score)
        scores[self.difficulty] = sorted(scores[self.difficulty], key=lambda x: x["moves"])[:3]
        with open("ranking.txt", "a") as file:
            file.write(f"{score['name']},{score['difficulty']},{score['moves']},{score['date']}\n")

    # Load the scores from the file
    def load_scores(self):
        scores = {"facil": [], "medio": [], "dificil": []}
        try:
            with open("ranking.txt", "r") as file:
                for line in file:
                    try:
                        name, difficulty, moves, date = line.strip().split(",")
                        scores[difficulty].append({
                            "name": name,
                            "difficulty": difficulty,
                            "moves": int(moves),
                            "date": date
                        })
                    except ValueError:
                        print(f"Error processing line: {line}")
        except FileNotFoundError:
            print("File does not exist. No scores loaded.")
        return scores
