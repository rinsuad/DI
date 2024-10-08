class Monstruo:
    def __init__(self, nombre):
        self.nombre = nombre  # Asigna el nombre del monstruo
        self.ataque = 8  # Valor de ataque del monstruo
        self.defensa = 3  # Valor de defensa del monstruo
        self.salud = 50  # Salud inicial del monstruo

    def atacar(self, heroe):
        print(f"El monstruo {self.nombre} ataca a {heroe.nombre}.")  # Mensaje de ataque
        daño = self.ataque - heroe.defensa  # Calcula el daño
        if daño > 0:
            heroe.salud -= daño  # Reduce la salud del héroe
            print(f"El héroe {heroe.nombre} ha recibido {daño} puntos de daño.")  # Mensaje de daño
        else:
            print("El héroe ha bloqueado el ataque.")  # Mensaje de bloqueo

    def esta_vivo(self):
        return self.salud > 0  # Verifica si el monstruo está vivo
