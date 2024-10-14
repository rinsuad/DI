# Crea una ventana que muestre tres etiquetas (Label). La primera debe mostrar un
# mensaje de bienvenida, la segunda debe mostrar tu nombre, y la tercera debe cambiar su
# texto al hacer clic en un botón.

import tkinter as tk
from cProfile import label

def changeText():
    label3.config(text="Este texto ha cambiado")

#Crear la ventana principal
root = tk.Tk()
root.title("Ejercicio 1")
root.geometry("300x200")

#Creamos los labels
label1 = tk.Label(root, text="Bienvenido a mi primera interfaz")
label1.pack()

label2 = tk.Label(root, text="Mi nombre es: Rebeca!")
label2.pack()

label3 = tk.Label(root, text="Este texto cambiará si le haces click al botón")
label3.pack()

#Creamos el botón
button = tk.Button(root, text="Haz click para cambiar el texto", command=changeText)
button.pack()

#Bucle principal
root.mainloop()