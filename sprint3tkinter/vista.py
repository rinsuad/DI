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

        self.button = {}

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
    def __init__(self, root, model, controller):
        self.move_label = None
        self.time_label = None
        self.root = root
        self.controller = controller
        self.model = model
        self.board_frame = tk.Frame(self.root)  # Use the root window instead of creating a new Toplevel
        self.board_frame.pack()


        self.move_label = tk.Label(self.root, text="Movimientos: 0")
        self.move_label.pack()
        self.time_label = tk.Label(self.root, text="Tiempo: 0")
        self.time_label.pack()

    def create_board(self):
        if not self.model.board:
            print("Error: The board is not populated.")
            return

        for i, row in enumerate(self.model.board):
            for j, card in enumerate(row):
                # Resize the PIL image
                resized_image = self.model.hidden_image.resize((100, 100), Image.LANCZOS)
                photo_image = ImageTk.PhotoImage(resized_image)

                # Create a button with the image for each card
                card_button = tk.Button(
                    self.board_frame,
                    image=photo_image,
                    command=lambda i=i, j=j: self.controller.on_card_click((i, j))
                )
                card_button.image = photo_image  # Keep a reference to avoid garbage collection
                card_button.grid(row=i, column=j)  # Place the button in the grid

                # Ensure the button is enabled initially
                card_button.config(state="normal")

    def update_board(self, card_position, show=False, permanent=False):
        i, j = card_position
        card = self.model.board[i][j]

        card_number = int(''.join(filter(str.isdigit, card)))  # Extract only the digits

        if show or permanent:
            if 0 <= card_number - 1 < len(self.model.card_images):
                resized_image = self.model.card_images[card_number - 1].resize((100, 100), Image.Resampling.LANCZOS)
            else:
                print(f"Index out of range for card {card_number}")
                resized_image = self.model.hidden_image.resize((100, 100), Image.Resampling.LANCZOS)
        else:
            resized_image = self.model.hidden_image.resize((100, 100), Image.Resampling.LANCZOS)

        photo_image = ImageTk.PhotoImage(resized_image)
        button = self.board_frame.grid_slaves(row=i, column=j)[0]
        button.config(image=photo_image)
        button.image = photo_image

        if permanent:
            button.config(state="disabled")

    def reset_cards(self, card1, card2):
        for card in [card1, card2]:
            i, j = card
            resized_image = self.model.hidden_image.resize((100, 100), Image.Resampling.LANCZOS)
            photo_image = ImageTk.PhotoImage(resized_image)
            button = self.board_frame.grid_slaves(row=i, column=j)[0]
            button.config(image=photo_image)
            button.image = photo_image

    def update_move_label(self, move_count):
        self.move_label.config(text=f"Movimientos: {move_count}")

    def update_time_label(self, time_elapsed):
        self.time_label.config(text=f"Tiempo: {time_elapsed} segundos")
