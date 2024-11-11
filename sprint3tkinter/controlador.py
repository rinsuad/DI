import time
from tkinter import Toplevel, Label, messagebox, simpledialog
from vista import GameView
from modelo import GameModel

class GameController:
    def __init__(self, root, main_menu):
        # Initialize the game controller
        self.game_view = None
        self.root = root
        self.main_menu = main_menu
        self.model = None
        self.loading_window = None
        self.player_name = None
        self.game_window = None
        self.timer_running = False
        self.start_time = None
        self.selected = []

    # Start the game
    def start_game_callback(self):
        # Ask the user for the difficulty
        difficulty = simpledialog.askstring("Seleccionar Dificultad", "Elige la dificultad: facil, medio, dificil")

        # Check if the difficulty is valid
        if difficulty in ["facil", "medio", "dificil"]:
            self.player_name = self.main_menu.ask_player_name()

            # Check if the player entered a name, if so, start the game
            if self.player_name:
                self.model = GameModel(difficulty, self)
                self.show_loading_window()  # Show the loading window
                self.model.load_images_thread()  # Load images in a separate thread
                self.check_images_loaded()  # Check if images are loaded
            else:
                messagebox.showerror("Error", "Debes ingresar un nombre para jugar.")
        else:
            messagebox.showerror("Error", "Dificultad no válida. Inténtalo de nuevo.")

    # Show the loading window
    def show_loading_window(self):
        self.loading_window = Toplevel(self.root)
        self.loading_window.title("Cargando")
        Label(self.loading_window, text="Cargando imágenes...").pack()

    # Check if the images have been loaded
    def check_images_loaded(self):
        # Verify images were downloaded
        if self.model.images_loaded.is_set():
            if self.loading_window:
                self.loading_window.destroy()  # Destroy the loading window
                self.loading_window = None  # Ensure the loading window is properly destroyed

            # Destroy the previous game window if it exists, to avoid duplicates
            if self.game_window and self.game_window.winfo_exists():
                self.game_window.destroy()

            # Create a new game window
            self.game_window = Toplevel(self.root)
            self.game_window.title("Tablero de partida")
            self.game_view = GameView(self.game_window, self.model, self)
            self.game_view.create_board()  # Create the game board
        else:
            # Check again after 100 ms if the images are loaded
            self.root.after(100, self.check_images_loaded)

    # Handle card clicks on the board
    def on_card_click(self, card_position):
        # Start the timer if it's not running
        if not self.timer_running:
            self.start_timer()  # Start the timer
            self.update_time()  # Update the time label
            self.timer_running = True

        # Get the card position and show the card image
        self.selected.append(card_position)
        self.game_view.update_board(card_position, show=True)

        # Check if two cards are selected
        if len(self.selected) == 2:
            self.handle_card_selection()  # Handle the card selection

    # Start the timer
    def start_timer(self):
        self.start_time = time.time()  # Record the start time
        self.timer_running = True
        self.update_time()  # Update the time label

    # Update the elapsed time
    def update_time(self):
        if self.timer_running:
            elapsed_time = int(time.time() - self.start_time)  # Calculate elapsed time
            self.game_view.update_time_label(elapsed_time)  # Update the time label
            self.root.after(1000, self.update_time)  # Update the time every second

    def handle_card_selection(self):
        # Check if the selected cards match
        card1, card2 = self.selected
        # If they match, update the board and check if the game is over
        if self.model.board[card1[0]][card1[1]] == self.model.board[card2[0]][card2[1]]:
            self.model.board[card1[0]][card1[1]] = f"matched_{self.model.board[card1[0]][card1[1]]}"
            self.model.board[card2[0]][card2[1]] = f"matched_{self.model.board[card2[0]][card2[1]]}"
            self.game_view.update_board(card1, permanent=True)  # Update the board to show matched cards
            self.game_view.update_board(card2, permanent=True)
        else:
            self.root.after(1000, lambda: self.game_view.reset_cards(card1, card2))  # Reset cards if they don't match

        self.selected = []  # Clear the selected cards list
        self.update_move_count()  # Update the move count

        # Check if the game is over
        if self.check_game_over():
            self.stop_timer()  # Stop the timer
            messagebox.showinfo("Game Over", "Congratulations! You've matched all pairs.")
            self.game_window.destroy()  # Destroy the game window
            self.show_stats_callback()  # Show the game statistics

    def check_game_over(self):
        # Check if all cards have been matched
        for row in self.model.board:
            for card in row:
                if not card.startswith("matched"):
                    return False
        return True

    # Stop the timer
    def stop_timer(self):
        self.timer_running = False

    # Update the move count in the label
    def update_move_count(self):
        self.model.move_count += 1  # Increment the move count
        self.game_view.update_move_label(self.model.move_count)  # Update the move count label

    # Show the statistics
    def show_stats_callback(self):
        print("Mostrar estadísticas")

    # Quit the game
    def quit_callback(self):
        self.root.quit()