import random  # Importa el módulo random para seleccionar beneficios aleatorios

class Tesoro:
    def __init__(self):
        self.beneficios = ["aumento de ataque", "aumento de defensa", "restauración de salud"]  # Lista de posibles beneficios

    def encontrar_tesoro(self, heroe):
        beneficio = random.choice(self.beneficios)  # Selecciona un beneficio aleatorio
        print(f"Héroe ha encontrado un tesoro: {beneficio}.")  # Imprime el beneficio encontrado

        if beneficio == "aumento de ataque":
            heroe.ataque += 5  # Aumenta el ataque del héroe
            print(f"El ataque de {heroe.nombre} aumenta a {heroe.ataque}.")  # Imprime el nuevo valor de ataque
        elif beneficio == "aumento de defensa":
            heroe.defensa += 5  # Aumenta la defensa del héroe
            print(f"La defensa de {heroe.nombre} aumenta a {heroe.defensa}.")  # Imprime el nuevo valor de defensa
        elif beneficio == "restauración de salud":
            heroe.salud = heroe.salud_maxima  # Restaura la salud del héroe a su valor máximo
            print(f"La salud de {heroe.nombre} ha sido restaurada a {heroe.salud}.")  # Imprime el nuevo valor de salud