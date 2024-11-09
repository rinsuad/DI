import threading # Import the threading module to work with threads so that the GUI remains responsive
from recursos import descargar_imagen

class GameModel:
    def __init__(self, difficulty):
        self.difficulty = difficulty # Set the difficulty level of the game
        self.board = self._generate_board() # Generate the game board based on the selected difficulty
        self.images_loaded = threading.Event() # Event to indicate when images are loaded

    def _generate_board(self):
        # Generate the game board based on the selected difficulty
        if self.difficulty == "facil":
            return [["card1", "card2"], ["card1", "card2"]] # Return a 2x2 board with card1 and card2
        elif self.difficulty == "medio":
            return [["card1", "card2", "card3"], ["card1", "card2", "card3"]] # Return a 2x3 board with card1, card2, and card3
        elif self.difficulty == "dificil":
            return [["card1", "card2", "card3", "card4"], ["card1", "card2", "card3", "card4"]] # Return a 2x4 board with card1, card2, card3, and card4

    def _load_images(self):
        # Download images asynchronously
        self.hidden_image = descargar_imagen("hidden_card_url")
        self.card_images = [descargar_imagen(f"card{i}_url") for i in range(1, 5)] # Download card images 1-4
        self.images_loaded.set() # Set the event to indicate that images are loaded

    def load_images_thread(self):
        threading.Thread(target=self._load_images).start() # Start a new thread to load images