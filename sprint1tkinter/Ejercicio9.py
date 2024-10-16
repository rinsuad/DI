# Crea una barra de menú en la ventana que tenga dos menús desplegables: “Archivo” con
# las opciones “Abrir” y “Salir”, y “Ayuda” con la opción “Acerca de”. La opción “Salir” debe
# cerrar la ventana, y “Acerca de” debe mostrar un mensaje informativo en una ventana
# emergente.

import tkinter as tk
from tkinter import messagebox


# We define the functions that will be called when the menu options are clicked
def new_window():
    messagebox.showinfo("Acerca de", "Abrir una nueva ventana")


def exit_app():
    root.quit()


# We set up the main window
root = tk.Tk()
root.title("Ejercicio 9")
root.geometry("300x300")

# We create the menu
main_menu = tk.Menu(root)
root.config(menu=main_menu)

# We create the file menu, a submenu of the main menu
file_menu = tk.Menu(main_menu, tearoff=0)
main_menu.add_cascade(label="Archivo", menu=file_menu)
file_menu.add_command(label="Abrir", command=new_window)
file_menu.add_separator()
file_menu.add_command(label="Salir", command=exit_app)


# We create the help menu, a submenu of the main menu
def show_help():
    messagebox.showinfo("Ayuda", "Esto es una ayuda")


help_menu = tk.Menu(main_menu, tearoff=0)
main_menu.add_cascade(label="Ayuda", menu=help_menu)
help_menu.add_command(label="Acerca de", command=show_help)

root.mainloop()
