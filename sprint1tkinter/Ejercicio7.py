# Crea un Canvas y dibuja en él un círculo y un rectángulo. El tamaño y las posiciones
# deben ser definidas por el usuario a través de dos campos de entrada (Entry) para las
# coordenadas.

import tkinter as tk

from Ejercicio4 import label

# We set up the main window
root = tk.Tk()
root.title("Ejercicio 7")
root.geometry("300x300")

# We create the entries and the labels that explain each entry
labelX = tk.Label(root, text="Introduce la coordenada y:")
labelX.pack()
x_entry = tk.Entry(root, width=10)
x_entry.pack()
labelY = tk.Label(root, text="Introduce la coordenada y:")
labelY.pack()
y_entry = tk.Entry(root, width=10)
y_entry.pack()

# We create the canvas
canvas = tk.Canvas(root, width=200, height=200, bg="white")
canvas.pack()

# We define the function that will draw the shape in the canvas
def draw_shape():
    try:
        x = int(x_entry.get())
        y = int(y_entry.get())
        canvas.create_rectangle(x, y, x + 50, y + 50, outline="blue")
        canvas.create_oval(x, y, x + 50, y + 50, fill="red")
    except ValueError:
        print("Por favor introduce unos valores válidos.")

# We create the button
button = tk.Button(root, text="Dibujar", command=draw_shape)
button.pack()

root.mainloop()
