import tkinter as tk

root = tk.Tk()
root.title("Centered Window")
# With the following line, we center the window on the screen
root.eval('tk::PlaceWindow . center')
# By not having a geometry, the window will use the space needed to display its widgets
#root.geometry("600x400")


def show_dimensions():
    screen_width = root.winfo_screenwidth()
    screen_height = root.winfo_screenheight()
    return label_dimensions.config(text=f"La resolución es {screen_width}x{screen_height}")

button_dimensions = tk.Button(root, text="Mostrar dimensiones de la pantalla", command=show_dimensions)
button_dimensions.pack()
label_dimensions = tk.Label(root, text="", font=("Arial", 15))
label_dimensions.pack()

root.mainloop()