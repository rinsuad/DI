from monstruo import Monstruo  # Importa la clase Monstruo del archivo monstruo.py
from tesoro import Tesoro  # Importa la clase Tesoro del archivo tesoro.py

class Mazmorra:
    def __init__(self, heroe):
        self.heroe = heroe  # Asigna el héroe proporcionado a la mazmorra
        self.monstruos = [Monstruo("Goblin"), Monstruo("Orco"), Monstruo("Dragón")]  # Crea una lista de monstruos
        self.tesoro = Tesoro()  # Crea una instancia de la clase Tesoro

    def jugar(self):
        print("Héroe entra en la mazmorra.")  # Imprime un mensaje indicando que el héroe ha entrado en la mazmorra
        while self.heroe.esta_vivo() and self.monstruos:  # Mientras el héroe esté vivo y haya monstruos
            enemigo = self.monstruos.pop(0)  # Toma el primer monstruo de la lista
            print(f"Te has encontrado con un {enemigo.nombre}.")  # Imprime el nombre del monstruo encontrado
            self.enfrentar_enemigo(enemigo)  # Llama al método para enfrentar al héroe con el monstruo
            if self.heroe.esta_vivo():  # Si el héroe sigue vivo después del combate
                self.buscar_tesoro()  # Llama al método para buscar un tesoro

        if self.heroe.esta_vivo():  # Si el héroe ha derrotado a todos los monstruos
            print(f"¡{self.heroe.nombre} ha derrotado a todos los monstruos y ha conquistado la mazmorra!")  # Imprime un mensaje de victoria
        else:
            print("Héroe ha sido derrotado en la mazmorra.")  # Imprime un mensaje de derrota

    def enfrentar_enemigo(self, enemigo):
        while self.heroe.esta_vivo() and enemigo.esta_vivo():  # Mientras el héroe y el monstruo estén vivos
            print("¿Qué deseas hacer?")  # Pregunta al jugador qué acción desea realizar
            print("1. Atacar")  # Opción para atacar
            print("2. Defender")  # Opción para defenderse
            print("3. Curarse")  # Opción para curarse
            opcion = input("Elige una opción: ")  # Lee la opción elegida por el jugador
            if opcion == "1":
                self.heroe.atacar(enemigo)  # El héroe ataca al monstruo
            elif opcion == "2":
                self.heroe.defenderse()  # El héroe se defiende
            elif opcion == "3":
                self.heroe.curarse()  # El héroe se cura
            else:
                print("Opción no válida.")  # Imprime un mensaje si la opción no es válida
                continue  # Vuelve al inicio del bucle

            if enemigo.esta_vivo():  # Si el monstruo sigue vivo después del ataque del héroe
                enemigo.atacar(self.heroe)  # El monstruo ataca al héroe
            self.heroe.reset_defensa()  # Restaura la defensa del héroe a su valor original

    def buscar_tesoro(self):
        print("Buscando tesoro...")  # Imprime un mensaje indicando que se está buscando un tesoro
        self.tesoro.encontrar_tesoro(self.heroe)  # Llama al método para encontrar un tesoro y aplicar su beneficio al héroe