# Vista: VistaNotas
# Clase VistaNotas:
#
# Esta clase gestionará la interfaz gráfica de la aplicación, definiendo los widgets y su disposición.
# Widgets utilizados:
#
# Label:
# Se utiliza para mostrar el título de la aplicación.
# También se usa para mostrar las coordenadas del clic del ratón y la imagen descargada.
# Listbox:
# Muestra la lista de notas agregadas. Se usará el método insert() para añadir elementos y delete() para eliminarlos.
# Entry:
# Entrada de texto donde el usuario puede escribir una nueva nota.
# Button:
# Botones para agregar, eliminar, guardar, cargar notas, y descargar una imagen.
# Cada botón estará asociado a una función del controlador mediante la opción command.
# bind("<Button-1>", función):
# Enlaza el evento del clic izquierdo del ratón fuera de los botones para capturar las coordenadas del clic.
# Label (para la imagen):
# Este Label será donde se mostrará la imagen descargada desde GitHub.

import tkinter as tk

class Vista:
    def __init__(self, root):
        self.root = root
        self.root.title("Aplicación de Notas")

        self.label_titulo = tk.Label(self.root, text="Aplicación de Notas")
        self.label_titulo.pack()

        self.listbox = tk.Listbox(self.root)
        self.listbox.pack()

        self.entry_nota = tk.Entry(self.root)
        self.entry_nota.pack()

        self.boton_agregar = tk.Button(self.root, text="Agregar Nota")
        self.boton_agregar.pack()

        self.boton_eliminar = tk.Button(self.root, text="Eliminar Nota")
        self.boton_eliminar.pack()

        self.boton_guardar = tk.Button(self.root, text="Guardar Notas")
        self.boton_guardar.pack()

        self.boton_cargar = tk.Button(self.root, text="Cargar Notas")
        self.boton_cargar.pack()

        self.boton_imagen = tk.Button(self.root, text="Descargar Imagen")
        self.boton_imagen.pack()

        self.label_coordenadas = tk.Label(self.root, text="Coordenadas: ")
        self.label_coordenadas.pack()

        self.label_imagen = tk.Label(self.root)
        self.label_imagen.pack()