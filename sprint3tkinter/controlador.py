from tkinter import Toplevel, Label, messagebox, simpledialog
from vista import GameView
from modelo import GameModel

class GameController:
    def __init__(self, root, main_menu):
        self.root = root
        self.main_menu = main_menu
        self.model = None
        self.loading_window = None
        self.player_name = None
        self.game_window = None

    def start_game_callback(self):
        difficulty = simpledialog.askstring("Seleccionar Dificultad", "Elige la dificultad: facil, medio, dificil")

        if difficulty in ["facil", "medio", "dificil"]:
            self.player_name = self.main_menu.ask_player_name()

            if self.player_name:
                self.model = GameModel(difficulty)
                self.show_loading_window()
                self.model.load_images_thread()
                self.check_images_loaded()
            else:
                messagebox.showerror("Error", "Debes ingresar un nombre para jugar.")
        else:
            messagebox.showerror("Error", "Dificultad no válida. Inténtalo de nuevo.")

    def show_loading_window(self):
        self.loading_window = Toplevel(self.root)
        self.loading_window.title("Cargando")
        Label(self.loading_window, text="Cargando imágenes...").pack()

    def check_images_loaded(self):
        # Verify images were downloaded
        if self.model.images_loaded.is_set():
            if self.loading_window:
                self.loading_window.destroy()
                self.loading_window = None  # Ensure the loading window is properly destroyed

            # Destroy the previous game window if it exists
            if self.game_window and self.game_window.winfo_exists():
                self.game_window.destroy()

            # Create a new game window
            self.game_window = Toplevel(self.root)
            self.game_window.title("Tablero de partida")
            GameView(self.game_window, self.model).create_board()
        else:
            # Check again after 100 ms if the images are loaded
            self.root.after(100, self.check_images_loaded)

    def show_stats_callback(self):
        print("Mostrar estadísticas")

    def quit_callback(self):
        self.root.quit()
