# Importamos las clases Heroe y Mazmorra de los ficheros heroe.py y mazmorra.py.
from heroe import Heroe
from mazmorra import Mazmorra

#
def main():
    # Se pide al usuario que introduzca el nombre del héroe que se va a utilizar para el programa y se guarda en la variable heroe.
    nombre_heroe = input("Introduce el nombre de tu héroe: ")
    heroe = Heroe(nombre_heroe)
    # Se invoca la clase mazmorra y se introduce el valor de la variable heroe a esa clase para trabajar con ella.
    mazmorra = Mazmorra(heroe)
    # Se inicia la función jugar de la clase mazmorra.
    mazmorra.jugar()

# Con este if se controla que lo que abarca solo se inicie cuando se ejecute desde el archivo main.
if __name__ == "__main__":
    # Se inicia el programa
    main()
