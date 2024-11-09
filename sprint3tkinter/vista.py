import tkinter as tk
from tkinter import simpledialog

class MainMenu:
    def __init__(self, root, controller):
        self.root = root
        self.controller = controller
        self.frame = tk.Frame(root)  # Create a frame for the main menu
        self.frame.pack()  # Pack the frame

        # Button to start the game
        self.play_button = tk.Button(self.frame, text="Jugar", command=self.start_game)
        self.play_button.pack()

        # Button to show statistics
        self.stats_button = tk.Button(self.frame, text="Estadísticas", command=self.show_stats)
        self.stats_button.pack()

        # Button to quit the application
        self.quit_button = tk.Button(self.frame, text="Salir", command=self.quit_game)
        self.quit_button.pack()

    def start_game(self):
        if self.controller:
            self.controller.start_game_callback()  # Call the start game callback

    def show_stats(self):
        if self.controller:
            self.controller.show_stats_callback()  # Call the show stats callback

    def quit_game(self):
        if self.controller:
            self.controller.quit_callback()  # Call the quit callback

    def ask_player_name(self):
        # Ask the user to enter their name
        return simpledialog.askstring("Nombre del Jugador", "Introduce tu nombre:")


class GameView:
    def __init__(self, root, model):
        self.root = root
        self.model = model
        self.board_frame = tk.Frame(root)

    def create_board(self):
        self.board_frame.pack()
        for row in self.model.board:
            for card in row:
                # Creates a button for each card, hidden at first
                card_button = tk.Button(self.board_frame, image=self.model.hidden_image)
                card_button.pack(side="left")
