import tkinter as tk

class MainMenu:
    def __init__(self, root, controller):
        self.root = root
        self.controller = controller
        self.frame = tk.Frame(root)
        self.frame.pack()

        self.play_button = tk.Button(self.frame, text="Jugar", command=self.controller.start_game_callback)
        self.play_button.pack()

        self.stats_button = tk.Button(self.frame, text="Estadísticas", command=self.controller.show_stats_callback)
        self.stats_button.pack()

        self.quit_button = tk.Button(self.frame, text="Salir", command=self.controller.quit_callback)
        self.quit_button.pack()