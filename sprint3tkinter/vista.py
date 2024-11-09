import tkinter as tk
from tkinter import simpledialog

class MainMenu:
    def __init__(self, root, controller):
        self.root = root  # Reference to the main Tkinter window
        self.controller = controller  # Reference to the GameController instance
        self.frame = tk.Frame(root)  # Create a frame to hold the menu buttons
        self.frame.pack()  # Pack the frame into the main window

        # Create and pack the "Jugar" button, linking it to the start_game method
        self.play_button = tk.Button(self.frame, text="Jugar", command=self.start_game)
        self.play_button.pack()

        # Create and pack the "Estadísticas" button, linking it to the show_stats method
        self.stats_button = tk.Button(self.frame, text="Estadísticas", command=self.show_stats)
        self.stats_button.pack()

        # Create and pack the "Salir" button, linking it to the quit_game method
        self.quit_button = tk.Button(self.frame, text="Salir", command=self.quit_game)
        self.quit_button.pack()

    def start_game(self):
        if self.controller:  # Check if the controller is set
            self.controller.start_game_callback()  # Call the start_game_callback method of the controller

    def show_stats(self):
        if self.controller:  # Check if the controller is set
            self.controller.show_stats_callback()  # Call the show_stats_callback method of the controller

    def quit_game(self):
        if self.controller:  # Check if the controller is set
            self.controller.quit_callback()  # Call the quit_callback method of the controller

    def ask_player_name(self):
        # Prompt the user to enter their name and return the input
        return simpledialog.askstring("Nombre del Jugador", "Introduce tu nombre:")

class GameView:
    def __init__(self, root, model): # Add model parameter to the constructor
        self.root = root
        self.model = model

    def create_board(self): # Method to create the game board
        board_frame = tk.Frame(self.root) # Create a frame to hold the board
        board_frame.pack() # Pack the frame into the main window
        for row in self.model.board: # Iterate over each row in the board
            for card in row: # Iterate over each card in the row
                card_button = tk.Button(board_frame, image=self.model.hidden_image) # Create a button with the hidden image
                card_button.pack(side="left") # Pack the button to the left