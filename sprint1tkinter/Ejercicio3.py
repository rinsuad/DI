# Crea una interfaz que tenga un campo de entrada (Entry) donde el usuario pueda escribir
# su nombre. Al hacer clic en un botón, debe mostrarse un saludo personalizado en una
# etiqueta.

import tkinter as tk


#Define the function that will show the greeting
def show_greeting():
    name = entry.get()
    label.config(text=f"Hola, {name}!")

#We set up the main window
root = tk.Tk()
root.title("Ejercicio 3")
root.geometry("300x200")

#We create the entry field
entry = tk.Entry(root)
entry.pack()

button = tk.Button(root, text="Haz click para mostrar el saludo", command=show_greeting)
button.pack()

label = tk.Label(root, text="Aquí se mostrará el saludo")
label.pack()

root.mainloop()
