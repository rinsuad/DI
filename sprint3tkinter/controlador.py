import time
from tkinter import Toplevel, Label, messagebox, simpledialog
from vista import GameView
from modelo import GameModel

class GameController:
    def __init__(self, root, main_menu):

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

    def start_game_callback(self):
        difficulty = simpledialog.askstring("Seleccionar Dificultad", "Elige la dificultad: facil, medio, dificil")

        if difficulty in ["facil", "medio", "dificil"]:
            self.player_name = self.main_menu.ask_player_name()

            if self.player_name:
                self.model = GameModel(difficulty, self)
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
            self.game_view = GameView(self.game_window, self.model, self)
            self.game_view.create_board()
        else:
            # Check again after 100 ms if the images are loaded
            self.root.after(100, self.check_images_loaded)

    def on_card_click(self, card_position):
        if not self.timer_running:
            self.start_timer()
            self.update_time()
            self.timer_running = True

        self.selected.append(card_position)
        self.game_view.update_board(card_position, show=True)  # Show the card image

        if len(self.selected) == 2:
            self.handle_card_selection()

    def start_timer(self):
        self.start_time = time.time()
        self.timer_running = True
        self.update_time()

    def update_time(self):
        if self.timer_running:
            elapsed_time = int(time.time() - self.start_time)
            self.game_view.update_time_label(elapsed_time)
            self.root.after(1000, self.update_time)

    def handle_card_selection(self):
        card1, card2 = self.selected
        if self.model.board[card1[0]][card1[1]] == self.model.board[card2[0]][card2[1]]:
            self.model.board[card1[0]][card1[1]] = f"matched_{self.model.board[card1[0]][card1[1]]}"
            self.model.board[card2[0]][card2[1]] = f"matched_{self.model.board[card2[0]][card2[1]]}"
            self.game_view.update_board(card1, permanent=True)
            self.game_view.update_board(card2, permanent=True)
        else:
            self.root.after(1000, lambda: self.game_view.reset_cards(card1, card2))

        self.selected = []
        self.update_move_count()

        if self.check_game_over():
            self.stop_timer()
            messagebox.showinfo("Game Over", "Congratulations! You've matched all pairs.")
            self.game_window.destroy()
            self.show_stats_callback()

    def check_game_over(self):
        for row in self.model.board:
            for card in row:
                if not card.startswith("matched"):
                    return False
        return True

    def stop_timer(self):
        self.timer_running = False

    def update_move_count(self):
        self.model.move_count += 1
        self.game_view.update_move_label(self.model.move_count)

    def show_stats_callback(self):
        print("Mostrar estadísticas")

    def quit_callback(self):
        self.root.quit()
