# Clase NotasModel:
#
# Esta clase gestionará la lista de notas y proporcionará métodos para agregar, eliminar y recuperar las notas.
# Atributo:
# self.notas: Lista que almacena las notas.
# Métodos del modelo:
#
# agregar_nota(nueva_nota): Añade una nueva nota a la lista de notas. Utiliza el método append() para agregar la nueva nota.
# eliminar_nota(indice): Elimina una nota en un índice específico. Usa del self.notas[indice] para eliminarla.
# obtener_notas(): Devuelve la lista completa de notas. Este método retorna el valor de self.notas.
# guardar_notas(): Abre el archivo notas.txt en modo escritura ('w'), y escribe cada nota en una nueva línea utilizando write().
# cargar_notas(): Abre el archivo notas.txt en modo lectura ('r') y lee cada línea, eliminando los saltos de línea usando strip().

class NotasModel:
    def __init__(self):
        self.notas = []

    def agregar_nota(self, nueva_nota):
        self.notas.append(nueva_nota)

    def eliminar_nota(self, indice):
        del self.notas[indice]

    def obtener_notas(self):
        return self.notas

    def guardar_notas(self):
        # We write the notes to the file line by line
        with open("notas.txt", "w") as archivo:
            for nota in self.notas:
                archivo.write(nota + "\n")

    def cargar_notas(self):
        # We read the notes from the file line by line
        with open("notas.txt", "r") as archivo:
            self.notas = [linea.strip() for linea in archivo]
