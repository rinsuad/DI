# Crea una ventana con tres casillas de verificación (Checkbutton) que representen tres
# aficiones (por ejemplo, “Leer”, “Deporte”, “Música”). Cuando el usuario seleccione o
# deseleccione una casilla, el estado actual de las aficiones seleccionadas debe mostrarse
# en una etiqueta.

import tkinter as tk

# We set up the main window
root = tk.Tk()
root.title("Ejercicio 4")
root.geometry("300x200")

# We create the variables that will store the state of the checkbuttons
hobbies_var = [tk.BooleanVar() for _ in range(3)]
# We create the list of hobbies
hobbies_names = ["Leer", "Deporte", "Música"]

# Define the function that will show the hobbies
def show_hobbies():
    # We get the selected hobbies
    selected_hobbies = [hobbies_names[i] for i, var in enumerate(hobbies_var) if var.get()]
    # We update the label
    label2.config(text=f"Estos son tus hobbies: {', '.join(selected_hobbies)}")

# We create the label
label = tk.Label(root, text="Selecciona tus aficiones")
label.pack()

# We create the checkbuttons
check = tk.Checkbutton(root, text="Leer", variable=hobbies_var[0], command=show_hobbies)
check.pack()
check1 = tk.Checkbutton(root, text="Deporte", variable=hobbies_var[1], command=show_hobbies)
check1.pack()
check2 = tk.Checkbutton(root, text="Música", variable=hobbies_var[2], command=show_hobbies)
check2.pack()

# Second label that will show the hobbies once selected
label2 = tk.Label(root, text="")
label2.pack()

# Main loop
root.mainloop()