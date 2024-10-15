# Crea un grupo de tres botones de opción (Radiobutton) para que el usuario elija su color
# favorito (rojo, verde o azul). Al seleccionar una opción, cambia el color de fondo de la
# ventana al color seleccionado.

import tkinter as tk

# We set up the main window
root = tk.Tk()
root.title("Ejercicio 5")
root.geometry("300x200")

# We create the variable that will store the color
var_color = tk.StringVar()

# Define the function that will change the color
def change_color():
    color = var_color.get()
    root.config(bg=color)

# We crete the canvas
canvas = tk.Canvas(root, width=300, height=200, bg="white")
canvas.pack()

# We create the radio buttons
buttonRed = tk.Radiobutton(canvas, text="Rojo", value="red", command=change_color, variable=var_color)
buttonRed.pack()
buttonGreen = tk.Radiobutton(canvas, text="Verde", value="green", command=change_color, variable=var_color)
buttonGreen.pack()
buttonBlue = tk.Radiobutton(canvas, text="Azul", value="blue", command=change_color, variable=var_color)
buttonBlue.pack()


root.mainloop()
