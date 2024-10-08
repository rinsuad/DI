class Heroe:
    def __init__(self, nombre):
        self.nombre = nombre  # Asigna el nombre del héroe
        self.ataque = 10  # Valor de ataque del héroe
        self.defensa = 5  # Valor de defensa del héroe
        self.salud = 100  # Salud inicial del héroe
        self.salud_maxima = 100  # Salud máxima del héroe
        self.defensa_temporal = 0  # Defensa temporal del héroe

    def atacar(self, enemigo):
        print(f"Héroe ataca a {enemigo.nombre}.")  # Mensaje de ataque
        daño = self.ataque - enemigo.defensa  # Calcula el daño
        if daño > 0:
            enemigo.salud -= daño  # Reduce la salud del enemigo
            print(f"El enemigo {enemigo.nombre} ha recibido {daño} puntos de daño.")  # Mensaje de daño
        else:
            print("El enemigo ha bloqueado el ataque.")  # Mensaje de bloqueo

    def curarse(self):
        cantidad_cura = 20  # Cantidad de curación
        self.salud = min(self.salud + cantidad_cura, self.salud_maxima)  # Cura al héroe
        print(f"Héroe se ha curado. Salud actual: {self.salud}")  # Mensaje de curación

    def defenderse(self):
        self.defensa_temporal = 5  # Aumenta la defensa temporalmente
        self.defensa += self.defensa_temporal  # Aplica la defensa temporal
        print(f"Héroe se defiende. Defensa aumentada temporalmente a {self.defensa}.")  # Mensaje de defensa

    def reset_defensa(self):
        self.defensa -= self.defensa_temporal  # Restaura la defensa original
        self.defensa_temporal = 0  # Resetea la defensa temporal
        print(f"La defensa de {self.nombre} vuelve a la normalidad.")  # Mensaje de restauración de defensa

    def esta_vivo(self):
        return self.salud > 0  # Verifica si el héroe está vivo