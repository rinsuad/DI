# Crea una barra deslizante (Scale) que permita al usuario seleccionar un número entre 0 y
# 100. El valor seleccionado debe mostrarse en tiempo real en una etiqueta.

import tkinter as tk

# Function to update the label with the value of the scale
def update_value(value):
    label.config(text=f"Valor: {value}")

# We set up the main window
root = tk.Tk()
root.title("Ejercicio 11")
root.geometry("300x300")

# We create the scale
scale = tk.Scale(root, from_=0, to=100, orient="horizontal", command=update_value)
scale.pack(pady=25)

# We create the label that will show the value of the scale
label = tk.Label(root, text="Valor: 0")
label.pack(pady=15)

root.mainloop()