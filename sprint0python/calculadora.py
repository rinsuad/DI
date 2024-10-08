# Utilizo un bucle while para mantener al usuario en el mismo menú hasta que introduzca dos valores válidos.
while True:
    try:
        # Aquí se leen los valores introducidos por teclado y se almacenan en dos varibles sobre las que trabajará el programa.
        number = int(input("Escribe el primer número con el que quieres operar:\n"))
        number1 = int(input("Escribe el segundo número con el que quieres operar:\n"))
        break
    except ValueError:
        print("Por favor, introduce un número válido.")
# Se importan las funciones del archivo operaciones.py
import operaciones

# Utilizo un bucle para manejar el resto del programa
while True:
    # Con este bucle manejo que la opción introducida por el usuario sea una opción válida, sino se le pide que introduzca un valor válido.
    while True:
        try:
            # Se lee la opción elegida por el usuario.
            opcion = int(input("Qué quieres hacer con los números?\n 1.Sumar\n 2.Restar\n 3.Multiplicar\n 4.Dividir\n"))
            if opcion in [1, 2, 3, 4]:
                # Si es una opción válida sale del bucle y continúa el programa.
                break
            else:
                print("Por favor, elige una opción válida.")
        except ValueError:
            print("Por favor, introduce un número válido.")
    # Se imprime por pantalla los valores elegidos y según la opción elegida por el usuario se realiza la operación y se imprime el resultado por pantalla.
    print(f"Has elegido los números: {number} y {number1}")

    if opcion == 1:
        print("La suma de ambos valores es:", operaciones.suma(number, number1))
    elif opcion == 2:
        print("La resta del primer valor menos el segundo valor es:", operaciones.resta(number, number1))
    elif opcion == 3:
        print("La multiplicación es:", operaciones.multiplication(number, number1))
    elif opcion == 4:
        print("La división es:", operaciones.division(number, number1))

    # Se le pregunta al usuario si quiere realizar más operaciones, en cuyo caso vuelve a preguntar cuál quiere realizar.
    otra_operacion = input("¿Quieres hacer otra operación? (S/N):\n")
    # Si la respuesta es que no, sale del bucle y finaliza el programa.
    if otra_operacion.upper() != 'S':
        break
    # Se le pregunta al usuario si quiere utilizar los mismos números.
    mismos_numeros = input("¿Quieres usar los mismos números? (S/N):\n")
    # Si la respuesta es que no, se le pregunta los números que quiere utilizar y vuelve a pedir que se elija las operaciones que se quieren realizar.
    if mismos_numeros.upper() != 'S':
        while True:
            try:
                number = int(input("Escribe el primer número con el que quieres operar:\n"))
                number1 = int(input("Escribe el segundo número con el que quieres operar:\n"))
                break
            except ValueError:
                print("Por favor, introduce un número válido.")
