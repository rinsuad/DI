import tkinter as tk
from tkinter import simpledialog, messagebox

from modelo import GameModel

class GameController:
    def __init__(self, root, main_menu):
        self.root = root
        self.main_menu = main_menu
        self.model = None

    def start_game_callback(self):
        # Show dialog to select difficulty
        difficulty = simpledialog.askstring("Seleccionar Dificultad", "Elige la dificultad: facil, medio, dificil")

        if difficulty in ["facil", "medio", "dificil"]:
            # Ask for the player's name
            self.player_name = self.main_menu.ask_player_name()

            if self.player_name:
                # Create the game model with the selected difficulty
                self.model = GameModel(difficulty)
                print(f"Dificultad seleccionada: {difficulty}")
                print(f"Nombre del jugador: {self.player_name}")
                print(f"Tablero generado: {self.model.board}")
            else:
                messagebox.showerror("Error", "Debes ingresar un nombre para jugar.")
        else:
            # Show error message if the difficulty is not valid
            messagebox.showerror("Error", "Dificultad no válida. Inténtalo de nuevo.")

    # Callback to show statistics
    def show_stats_callback(self):
        print("Mostrar estadísticas")

    # Callback to quit the application
    def quit_callback(self):
        self.root.quit()  # Quit the application