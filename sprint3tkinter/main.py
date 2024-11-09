import tkinter as tk
from vista import MainMenu
from controlador import GameController

if __name__ == "__main__":
    root = tk.Tk()  # Create the main window
    root.withdraw()  # Hide the main window

    main_menu = MainMenu(root, None)  # Initialize the main menu
    controller = GameController(root, main_menu)  # Initialize the game controller
    main_menu.controller = controller  # Set the controller in the main menu

    root.deiconify()  # Show the main window
    root.mainloop()  # Start the Tkinter event loop