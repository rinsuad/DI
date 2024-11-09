import tkinter as tk
from vista import MainMenu
from controlador import GameController

if __name__ == "__main__":
    root = tk.Tk()
    root.withdraw()  # Oculta la ventana principal

    controller = GameController(root)
    main_menu = MainMenu(root, controller)

    root.deiconify()  # Muestra la ventana principal con el menú
    root.mainloop()