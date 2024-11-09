class GameController:
    def __init__(self, root):
        self.root = root

    def start_game_callback(self):
        print("Iniciar juego")

    def show_stats_callback(self):
        print("Mostrar estadísticas")

    def quit_callback(self):
        self.root.quit()