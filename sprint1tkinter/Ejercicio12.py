# Desarrolla una aplicación que permita registrar usuarios con los siguientes datos: nombre,
# edad y género. La aplicación debe contar con las siguientes funcionalidades:
# 1. Un campo de entrada (Entry) para el nombre del usuario.
# 2. Una barra deslizante (Scale) para seleccionar la edad del usuario (entre 0 y 100
# años).
# 3. Tres botones de opción (Radiobutton) para seleccionar el género del usuario
# (masculino, femenino, otro).
# 4. Un botón (Button) para añadir el usuario a una lista.
# 5. Una lista de usuarios (Listbox) que muestre el nombre, la edad y el género de los
# usuarios registrados.
# 6. Una barra de desplazamiento vertical (Scrollbar) para la lista de usuarios.
# 7. Un botón para eliminar el usuario seleccionado de la lista.
# 8. Un botón de salir que cierre la aplicación.
# 9. Un menú desplegable con las opciones “Guardar Lista” y “Cargar Lista” esto nos
# mostrará un messagebox indicando que la lista ha sido guardada o cargada.

import tkinter as tk
from tkinter import messagebox

root = tk.Tk()
root.title("Ejercicio 12")
root.geometry("700x600")

# We create the list of users
users = ["Nombre: Juan, Edad: 25, Género: Masculino", "Nombre: María, Edad: 30, Género: Femenino",
         "Nombre: Pedro, Edad: 40, Género: Otro", "Nombre: Ana, Edad: 20, Género: Femenino"]


# Function to exit the app
def exit_app():
    messagebox.showinfo("Salir", "Saliendo de la aplicación")
    root.quit()


# Function to delete the selected user and show a message
def delete_user():
    selected_user = listBox.get(listBox.curselection())
    listBox.delete(listBox.curselection())
    messagebox.showinfo("Usuario eliminado", f"Usuario eliminado: {selected_user}")
    print(f"Usuario eliminado: {selected_user}")


# Function to update the label with the value of the scale
def update_value(value):
    label_age.config(text=f"Tu edad: {value}")


# Function to add the user to the listbox and show a message
def add_user():
    name = entry.get()
    age = scale.get()
    genre_user = genre.get()
    user = f"Nombre: {name}, Edad: {age}, Género: {genre_user}"
    listBox.insert(tk.END, user)
    messagebox.showinfo("Usuario añadido", f"Usuario añadido: {user}")
    print(f"Usuario añadido: {user}")


# Function to show a message when the list is saved using the menu
def save_list():
    messagebox.showinfo("Guardar Lista", "La lista ha sido guardada")


# Function to show a message when the list is loaded using the menu
def load_list():
    messagebox.showinfo("Cargar Lista", "La lista ha sido cargada")


# We create the menu
main_menu = tk.Menu(root)
root.config(menu=main_menu)

# We create the option menu
option_menu = tk.Menu(main_menu, tearoff=0)
main_menu.add_cascade(label="Opciones", menu=option_menu)
option_menu.add_command(label="Guardar Lista", command=save_list)
option_menu.add_separator()
option_menu.add_command(label="Cargar Lista", command=load_list)

label_data = tk.Label(root, text="INTRODUCE TUS DATOS", font=("Arial", 14))
label_data.pack(pady=1)

# We create the frame for the user data
frame_user_data = tk.Frame(root, bg="lightgrey", bd=2, relief="groove")
frame_user_data.pack(pady=0, padx=10, fill=tk.X, expand=True)

# We create labels for the user data
label_name = tk.Label(frame_user_data, text="Nombre:")
label_name.pack()

# We create the entry for the name of the user
entry = tk.Entry(frame_user_data, width=50)
entry.pack()

# We create the scale
label_enter_age = tk.Label(frame_user_data, text="Introduce tu edad:")
label_enter_age.pack(pady=5)
scale = tk.Scale(frame_user_data, from_=0, to=100, orient="horizontal", command=update_value)
scale.pack(pady=5)

# We create the label that will show the value of the scale
label_age = tk.Label(frame_user_data, text="Tu edad: 0")
label_age.pack(pady=15)

# We create the variable that will store the genre
genre = tk.StringVar()

# We create the radio buttons for the genre options
label_genre = tk.Label(frame_user_data, text="Selecciona tu género:")
label_genre.pack(pady=5)
button_male = tk.Radiobutton(frame_user_data, text="Masculino", value="Masculino", variable=genre)
button_male.pack()
button_female = tk.Radiobutton(frame_user_data, text="Femenino", value="Femenino", variable=genre)
button_female.pack()
button_other = tk.Radiobutton(frame_user_data, text="Otro", value="Otro", variable=genre)
button_other.pack()

# We create the frame for the buttons
frame_buttons = tk.Frame(root, bg="lightgrey", bd=2, relief="groove")
frame_buttons.pack(pady=10, padx=10, fill="both", expand=True)

# We create the button that will add the user
button_add_user = tk.Button(frame_buttons, text="Añadir usuario", command=add_user)
button_add_user.place(relx=0.4, rely=0.5, anchor="center")

# We create the button that will delete the selected user
button_delete_user = tk.Button(frame_buttons, text="Eliminar usuario", command=delete_user)
button_delete_user.place(relx=0.6, rely=0.5, anchor="center")

# We create the button that will exit the app
button_exit_app = tk.Button(frame_buttons, text="Salir", command=exit_app)
button_exit_app.place(relx=0.95, rely=0.5, anchor="center")

# We create the listbox and make it single selection
listBox = tk.Listbox(root, selectmode=tk.SINGLE)
# We insert the users into the listbox
for user in users:
    listBox.insert(tk.END, user)
listBox.pack(expand=True, fill="both", padx=10, pady=2)

# We create the scrollbar and link it to the listbox
scroll_y = tk.Scrollbar(listBox, orient="vertical", command=listBox.yview)
scroll_y.pack(side="right", fill="y")

root.mainloop()
