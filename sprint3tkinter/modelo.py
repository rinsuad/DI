import random

class GameModel:
    def __init__(self, difficulty):
        self.difficulty = difficulty
        self.board = self._generate_board()  # Generate the game board

    def _generate_board(self):
        pairs = { # Number of pairs based on difficulty, modify as needed
            "facil": 6,
            "medio": 12,
            "dificil": 18
        }
        num_pairs = pairs[self.difficulty]  # Get the number of pairs based on difficulty
        cards = [f"card{i}" for i in range(1, num_pairs + 1)] * 2  # Create card pairs
        random.shuffle(cards)  # Shuffle the cards

        size = (2, num_pairs)
        return [cards[i * size[1]:(i + 1) * size[1]] for i in range(size[0])]  # Create the board