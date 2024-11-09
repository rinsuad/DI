from tkinter import simpledialog, messagebox
from tkinter import Toplevel, Label
import threading
from modelo import GameModel
from vista import GameView

class GameController:
    def __init__(self, root, main_menu):
        self.root = root  # Reference to the main Tkinter window
        self.main_menu = main_menu  # Reference to the MainMenu instance
        self.player_name = None  # Initialize player_name to None
        self.model = None  # Initialize model to None
        self.loading_window = None  # Initialize loading_window to None

    def start_game_callback(self):
        self.show_difficulty_selection()  # Call the method to show difficulty selection dialog

    def show_difficulty_selection(self):
        # Prompt the user to select a difficulty level
        difficulty = simpledialog.askstring("Seleccionar Dificultad", "Elige la dificultad: facil, medio, dificil")
        if difficulty in ["facil", "medio", "dificil"]:  # Check if the selected difficulty is valid
            self.player_name = self.main_menu.ask_player_name()  # Prompt for the player's name
            print(f"Dificultad seleccionada: {difficulty}")  # Print the selected difficulty
            print(f"Nombre del jugador: {self.player_name}")  # Print the player's name
        else:
            # Show an error message if the selected difficulty is not valid
            messagebox.showerror("Error", "Dificultad no válida. Inténtalo de nuevo.")

    def show_loading_window(self):
        self.loading_window = Toplevel(self.root) # Create a new Toplevel window
        Label(self.loading_window, text="Cargando imágenes...").pack() # Add a label to display loading message

    def check_images_loaded(self):
        if self.model.images_loaded.is_set(): # Check if images are loaded
            self.loading_window.destroy() # Close the loading window
            GameView(self.root, self.model).create_board() # Create the game board
        else:
            self.root.after(100, self.check_images_loaded) # Check again after 100ms

    def show_stats_callback(self):
        print("Mostrar estadísticas")  # Placeholder for showing statistics

    def quit_callback(self):
        self.root.quit()  # Quit the application