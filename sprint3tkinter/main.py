import tkinter as tk
from vista import MainMenu
from controlador import GameController

if __name__ == "__main__":
    root = tk.Tk()  # Create the main Tkinter window
    root.withdraw()  # Hide the main window initially

    main_menu = MainMenu(root, None)  # Create an instance of MainMenu without a controller
    controller = GameController(root, main_menu)  # Create an instance of GameController with the main window and main menu
    main_menu.controller = controller  # Set the controller attribute of main_menu to the created controller

    root.deiconify()  # Show the main window with the menu
    root.mainloop()  # Start the Tkinter event loop