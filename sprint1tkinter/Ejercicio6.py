# Crea una lista de elementos (Listbox) que contenga una lista de frutas (por ejemplo,
# “Manzana”, “Banana”, “Naranja”). Al seleccionar una fruta y hacer clic en un botón,
# muestra el nombre de la fruta seleccionada en una etiqueta.

import tkinter as tk

# We set up the main window
root = tk.Tk()
root.title("Ejercicio 6")
root.geometry("300x220")

# We create the list of fruits
fruits = ["Manzana", "Banana", "Naranja"]


def show_fruits():
    # We get the selected fruit
    selected_fruit = listBox.get(listBox.curselection())
    # We update the label
    label.config(text=f"Has seleccionado: {selected_fruit}")

# We create the listbox and make it single selection
listBox = tk.Listbox(root, selectmode=tk.SINGLE)
# We insert the fruits
for fruit in fruits:
    listBox.insert(tk.END, fruit)
listBox.pack()

# We create the button that will show the selected fruit
button = tk.Button(root, text="Haz click para mostrar la fruta seleccionada", command=show_fruits)
button.pack()

# We create the label that will show the selected fruit
label = tk.Label(root, text="")
label.pack()

root.mainloop()
