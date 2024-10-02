class Heroe:
    def __init__(self, nombre):
        self.nombre = nombre
        self.ataque = 10
        self.defensa = 5
        self.salud = 100
        self.salud_maxima = 100
        self.defensa_temporal = 0

    def atacar(self, enemigo):
        print(f"Héroe ataca a {enemigo.nombre}.")
        daño = self.ataque - enemigo.defensa
        if daño > 0:
            enemigo.salud -= daño
            print(f"El enemigo {enemigo.nombre} ha recibido {daño} puntos de daño.")
        else:
            print("El enemigo ha bloqueado el ataque.")

    def curarse(self):
        cantidad_cura = 20
        self.salud = min(self.salud + cantidad_cura, self.salud_maxima)
        print(f"Héroe se ha curado. Salud actual: {self.salud}")

    def defenderse(self):
        self.defensa_temporal = 5
        self.defensa += self.defensa_temporal
        print(f"Héroe se defiende. Defensa aumentada temporalmente a {self.defensa}.")

    def reset_defensa(self):
        self.defensa -= self.defensa_temporal
        self.defensa_temporal = 0
        print(f"La defensa de {self.nombre} vuelve a la normalidad.")

    def esta_vivo(self):
        return self.salud > 0