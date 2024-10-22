import tkinter as tk

root = tk.Tk()
root.title("Centered Window")
# With the following line, we center the window on the screen
root.eval('tk::PlaceWindow . center')
# By not having a geometry, the window will use the space needed to display its widgets
#root.geometry("600x400")
root.resizable(True, True)

label_resizable = tk.Label(root, text="Prueba a redimensionar la pantalla", font=("Arial", 15))
label_resizable.pack()


def show_dimensions():
    screen_width = root.winfo_screenwidth()
    screen_height = root.winfo_screenheight()
    return label_dimensions.config(text=f"La resolución es {screen_width}x{screen_height}")

button_dimensions = tk.Button(root, text="Mostrar resolución de la pantalla", command=show_dimensions)
button_dimensions.pack()
label_dimensions = tk.Label(root, text="", font=("Arial", 15))
label_dimensions.pack()

def more_text():
    return label_button_text.config(text="Este botón tiene un texto muy largo, para demostrar que la pantalla aumenta de tamaño \n todo lo que le haga falta a la app", font=("Arial", 15))

button_size = tk.Button(root, text="Botón de muestra", font=("Arial", 15), command=more_text)
button_size.pack()
label_button_text = tk.Label(root, text="", font=("Arial", 15))
label_button_text.pack()

def show_button_size():
    req_width = button_size.winfo_reqwidth()
    req_height = button_size.winfo_reqheight()
    return label_button_dimensions.config(text=f"Tamaño requerido del botón: {req_width}x{req_height}")

button_size_show = tk.Button(root, text="Mostrar Tamaño Requerido", command=show_button_size)
button_size_show.pack()
label_button_dimensions = tk.Label(root, text="", font=("Arial", 15))
label_button_dimensions.pack()

root.mainloop()