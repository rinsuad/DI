# Diseña una interfaz que esté dividida en dos Frame. El Frame superior debe contener dos
# etiquetas y un campo de entrada, mientras que el Frame inferior debe contener dos
# botones: uno para mostrar el contenido del Entry en una etiqueta, y otro para borrar el
# contenido del Entry.

import tkinter as tk

# We set up the main window
root = tk.Tk()
root.title("Ejercicio 8")
root.geometry("300x300")

# We create the first frame, with two labels and an entry
frame1 = tk.Frame(root, bg="lightgrey", bd=2)
frame1.pack(pady=5, padx=5, expand=True, fill='both')
label1 = tk.Label(frame1, text="Introduce un texto:")
label1.pack(pady=5, padx=5, expand=True, fill='x')
label2 = tk.Label(frame1, text="")
label2.pack(pady=5, padx=5, expand=True, fill='x')
entry = tk.Entry(frame1, width=150)
entry.pack(pady=5, padx=5)

# Function to show the text in the label
def show_text():
    label2.config(text=entry.get())

# Function to delete the text in the entry and the label
def delete_text():
    entry.delete(0, tk.END)
    label2.config(text="")

# We create the second frame, with two buttons
frame2 = tk.Frame(root, bg="lightgreen", bd=2)
frame2.pack(pady=5, padx=5, expand=True, fill='both')
button1 = tk.Button(frame2, text="Contenido del entry", command=show_text)
button1.pack(pady=5, padx=15)
button2 = tk.Button(frame2, text="Borrar contenido", command=delete_text)
button2.pack(pady=5, padx=15)

root.mainloop()
