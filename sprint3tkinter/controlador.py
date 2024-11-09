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
        Label(self.loading_window, text="Cargando imágenes...").pack()

    def check_images_loaded(self):
        # Verificar si las imágenes han sido cargadas
        if self.model.images_loaded.is_set():
            self.loading_window.destroy()
            GameView(self.root, self.model).create_board()
        else:
            # Volver a comprobar después de 100 ms
            self.root.after(100, self.check_images_loaded)

    def show_stats_callback(self):
        print("Mostrar estadísticas")

    def quit_callback(self):
        self.root.quit()