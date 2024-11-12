import tkinter as tk
from tkinter import ttk, simpledialog
from PIL import Image, ImageTk

from modelo import GameModel


class MainMenu:
    def __init__(self, root, controller):
        self.root = root
        self.controller = controller
        self.frame = ttk.Frame(root, padding=20)  # Create a frame for the main menu using ttk for a modern look
        self.frame.pack()  # Pack the frame to display it

        # Configure the styles
        self.style = ttk.Style()
        self.style.configure("TButton", font=("Helvetica", 14), padding=10)

        # Buttons of the main menu
        self.play_button = ttk.Button(self.frame, text="🎮 Jugar", command=self.start_game)
        self.play_button.grid(row=0, column=0, pady=10)

        self.stats_button = ttk.Button(self.frame, text="📊 Estadísticas", command=self.show_stats)
        self.stats_button.grid(row=1, column=0, pady=10)

        self.quit_button = ttk.Button(self.frame, text="🚪 Salir", command=self.quit_game)
        self.quit_button.grid(row=2, column=0, pady=10)

        self.stats_window = None

    def start_game(self):
        if self.controller:
            self.controller.start_game_callback()  # Call the start game callback

    def show_stats(self):
        if self.stats_window is not None and self.stats_window.winfo_exists():
            self.stats_window.lift()
            return

        self.stats_window = tk.Toplevel(self.root)
        self.stats_window.title("Estadísticas")
        self.stats_window.geometry("300x400")
        self.stats_window.config(bg="#f0f0f0")
        self.stats_window.protocol("WM_DELETE_WINDOW", self.on_stats_window_close)

        model = GameModel("facil", None)
        scores = model.load_scores()

        # Create a frame for the stats window
        stats_frame = ttk.Frame(self.stats_window, padding=10)
        stats_frame.pack(fill=tk.BOTH, expand=True)

        # Show the scores in the stats window
        for difficulty, scores_list in scores.items():
            ttk.Label(stats_frame, text=f"Dificultad: {difficulty.capitalize()}", font=("Helvetica", 12, "bold")).pack(
                pady=5)
            for score in scores_list:
                ttk.Label(
                    stats_frame,
                    text=f"{score['name']} - {score['moves']} movimientos - {score['date']}"
                ).pack()

    def on_stats_window_close(self):
        if self.stats_window is not None:
            self.stats_window.destroy()
            self.stats_window = None

    def quit_game(self):
        if self.controller:
            self.controller.quit_callback()

    def ask_player_name(self):
        return simpledialog.askstring("Nombre del Jugador", "Introduce tu nombre:")


class GameView:
    def __init__(self, root, model, controller):
        self.move_label = None
        self.time_label = None
        self.root = root
        self.controller = controller
        self.model = model

        # Create the board frame
        self.board_frame = ttk.Frame(self.root, padding=20)
        self.board_frame.pack()

        # Labels for moves and time
        self.move_label = ttk.Label(self.root, text="Movimientos: 0", font=("Helvetica", 14))
        self.move_label.pack(pady=10)
        self.time_label = ttk.Label(self.root, text="Tiempo: 0", font=("Helvetica", 14))
        self.time_label.pack(pady=10)

    def create_board(self):
        # Check if the board is populated
        if not self.model.board:
            print("Error: The board is not populated.")
            return
        # Create the board buttons
        for i, row in enumerate(self.model.board):
            for j, card in enumerate(row):
                if self.model.hidden_image is None:
                    print("Error: Couldn't find the hidden image.")
                    return
                else:
                    # Resize the image to 100x100 pixels
                    resized_image = self.model.hidden_image.resize((100, 100), Image.LANCZOS)

                if resized_image:
                    photo_image = ImageTk.PhotoImage(resized_image)
                    # Create a button for the card
                    card_button = ttk.Button(self.board_frame, image=photo_image,
                                             command=lambda i=i, j=j: self.controller.on_card_click((i, j)))
                    card_button.image = photo_image  # Keep a reference to the image
                    card_button.grid(row=i, column=j, padx=5, pady=5)  # Place the button in the grid
                else:
                    print(f"Couldn't load image for card {i}, {j}")

    def update_board(self, card_position, show=False, permanent=False):
        # Get the card number from the board
        i, j = card_position
        card = self.model.board[i][j]
        try:
            card_number = int(
                ''.join(filter(str.isdigit, card)))  # Extract the card number from the card string, only the digits
        except ValueError:
            print(f"Error: Couldn't extract card number from {card}")
            return

        if show or permanent:
            if 0 <= card_number - 1 < len(self.model.card_images):
                resized_image = self.model.card_images[card_number - 1].resize((100, 100), Image.Resampling.LANCZOS)
            else:
                resized_image = self.model.hidden_image.resize((100, 100), Image.Resampling.LANCZOS)
        else:
            resized_image = self.model.hidden_image.resize((100, 100), Image.Resampling.LANCZOS)

        photo_image = ImageTk.PhotoImage(resized_image)
        button = self.board_frame.grid_slaves(row=i, column=j)[0]
        button.config(image=photo_image)
        button.image = photo_image

        if permanent:
            button.config(state="disabled")  # Disable the button if the card is matched

    def reset_cards(self, card1, card2):
        for card in [card1, card2]:
            i, j = card
            resized_image = self.model.hidden_image.resize((100, 100), Image.Resampling.LANCZOS)
            photo_image = ImageTk.PhotoImage(resized_image)
            button = self.board_frame.grid_slaves(row=i, column=j)[0]
            button.config(image=photo_image)
            button.image = photo_image  # Reset the card image to hidden

    def update_move_label(self, move_count):
        self.move_label.config(text=f"Movimientos: {move_count}")  # Update the move label with the move count

    def update_time_label(self, time_elapsed):
        self.time_label.config(text=f"Tiempo: {time_elapsed} segundos")  # Update the time label with the elapsed time
