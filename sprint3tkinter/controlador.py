from tkinter import simpledialog, messagebox

class GameController:
    def __init__(self, root, main_menu):
        self.root = root  # Reference to the main Tkinter window
        self.main_menu = main_menu  # Reference to the MainMenu instance
        self.player_name = None  # Initialize player_name to None

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

    def show_stats_callback(self):
        print("Mostrar estadísticas")  # Placeholder for showing statistics

    def quit_callback(self):
        self.root.quit()  # Quit the application