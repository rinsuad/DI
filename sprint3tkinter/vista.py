import tkinter as tk
from tkinter import simpledialog
from PIL import Image, ImageTk

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
        self.board_frame.pack()

    def create_board(self):
        for i, row in enumerate(self.model.board):
            for j, card in enumerate(row):
                # Resize the PIL image
                resized_image = self.model.hidden_image.resize((100, 100), Image.LANCZOS)
                photo_image = ImageTk.PhotoImage(resized_image)
                card_button = tk.Button(self.board_frame, image=photo_image)
                card_button.image = photo_image  # Keep a reference to avoid garbage collection
                card_button.grid(row=i, column=j)  # Place the button in the grid