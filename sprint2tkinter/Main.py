import tkinter as tk
from NotasModel import NotasModel
from VistaNotas import Vista
from ControladorNotas import ControladorNotas

def main():
    # We create the root window, the model, the view, and the controller
    root = tk.Tk()
    model = NotasModel()
    view = Vista(root)
    controller = ControladorNotas(model, view)
    root.mainloop()

if __name__ == "__main__":
    main()