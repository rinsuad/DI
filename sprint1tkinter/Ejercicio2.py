# Diseña una ventana con dos botones. Uno debe mostrar un mensaje en una etiqueta al
# presionarlo, y el otro debe cerrar la ventana cuando se haga clic en él.

import tkinter as tk

#Define the function that will show the text
def show_text():
    label.config(text="Este es el mensaje que se muestra al presionar el botón")

#Define the function that will close the window
def close_window():
    root.quit()

#We set up the main window
root = tk.Tk()
root.title("Ejercicio 2")
root.geometry("300x200")

#We create the label
label = tk.Label(root, text=" ")
label.pack()

#We create the buttons
button1 = tk.Button(root, text="Haz click para mostrar el mensaje", command=show_text)
button1.pack()

button2 = tk.Button(root, text="Haz click para cerrar la ventana", command=close_window)
button2.pack()

#Main loop
root.mainloop()
