# Controlador: ControladorNotas
# Clase ControladorNotas:
#
# Esta clase gestionará la lógica de la interacción entre el usuario, la vista y el modelo.
# Métodos del controlador:
#
# agregar_nota():
# Obtiene el texto del widget Entry de la vista utilizando self.vista.entry_nota.get().
# Llama al método agregar_nota() del modelo para agregar la nueva nota.
# Actualiza el Listbox llamando a actualizar_listbox().
# eliminar_nota():
# Obtiene el índice seleccionado del Listbox usando self.vista.listbox.curselection().
# Llama al método eliminar_nota() del modelo para eliminar la nota en el índice seleccionado.
# Actualiza el Listbox llamando a actualizar_listbox().
# guardar_notas():
# Llama al método guardar_notas() del modelo para guardar la lista de notas en un archivo.
# Muestra un mensaje de confirmación con messagebox.showinfo().
# cargar_notas():
# Llama al método cargar_notas() del modelo para cargar las notas desde un archivo.
# Actualiza el Listbox llamando a actualizar_listbox().
# descargar_imagen():
# Llama a un método que descarga una imagen desde GitHub utilizando la biblioteca requests. La descarga se realiza en un hilo separado usando threading.Thread().
# Usa after() para actualizar la interfaz gráfica con la imagen descargada.
# actualizar_coordenadas(event):
# Captura las coordenadas del clic y actualiza el Label de coordenadas usando event.x y event.y.
import tkinter as tk
import requests
import threading
from tkinter import messagebox
from PIL import Image, ImageTk
from io import BytesIO
from urllib3 import request


import tkinter as tk
import requests
import threading
from tkinter import messagebox
from PIL import Image, ImageTk
from io import BytesIO

class ControladorNotas:
    def __init__(self, notasModel, vistaNotas):
        self.NotasModel = notasModel
        self.VistaNotas = vistaNotas

        # We associate the buttons with the corresponding functions/events
        self.VistaNotas.boton_agregar.config(command=self.agregar_nota)
        self.VistaNotas.boton_eliminar.config(command=self.eliminar_nota)
        self.VistaNotas.boton_guardar.config(command=self.guardar_notas)
        self.VistaNotas.boton_cargar.config(command=self.cargar_notas)
        self.VistaNotas.boton_imagen.config(command=self.iniciar_descarga)
        self.VistaNotas.root.bind("<Button-1>", lambda event: self.actualizar_coordenadas(event.x, event.y))

    # We define the functions that will be called when the buttons are pressed
    def actualizar_listbox(self):
        self.VistaNotas.listbox.delete(0, tk.END)
        for nota in self.NotasModel.obtener_notas():
            self.VistaNotas.listbox.insert(tk.END, nota)

    def agregar_nota(self):
        nueva_nota = self.VistaNotas.entry_nota.get()
        self.NotasModel.agregar_nota(nueva_nota)
        self.actualizar_listbox()

    def eliminar_nota(self):
        indice = self.VistaNotas.listbox.curselection()
        if indice:
            self.NotasModel.eliminar_nota(indice[0])
            self.actualizar_listbox()

    def guardar_notas(self):
        self.NotasModel.guardar_notas()
        messagebox.showinfo("Notas Guardadas", "Las notas se han guardado correctamente.")

    def cargar_notas(self):
        self.NotasModel.cargar_notas()
        self.actualizar_listbox()

    def descargar_imagen(self, url, callback):
        try:
            response = requests.get(url)
            response.raise_for_status()
            image = Image.open(BytesIO(response.content))
            image.thumbnail((200, 200), Image.Resampling.LANCZOS)
            image_tk = ImageTk.PhotoImage(image)
            self.VistaNotas.root.after(0, callback, image_tk)
        except requests.exceptions.RequestException as e:
            print(f"Error al descargar la imagen: {e}")
            self.VistaNotas.root.after(0, callback, None)

    def actualizar_label_imagen(self, image_tk):
        if image_tk:
            self.VistaNotas.label_imagen.config(image=image_tk)
            self.VistaNotas.label_imagen.image = image_tk
        else:
            self.VistaNotas.label_imagen.config(text="Error al descargar la imagen")

    def iniciar_descarga(self):
        url = 'https://raw.githubusercontent.com/rinsuad/DI/refs/heads/main/sprint2tkinter/gatomon.jpg?token=GHSAT0AAAAAACYGMCDRMR7P5SNWWE3OZUCAZY7VQTQ'
        hilo = threading.Thread(target=self.descargar_imagen, args=(url, self.actualizar_label_imagen))
        hilo.start()

    def actualizar_coordenadas(self, x, y):
        self.VistaNotas.label_coordenadas.config(text=f"Coordenadas: x={x}, y={y}")
