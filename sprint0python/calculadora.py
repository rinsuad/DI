while True:
    try:
        number = int(input("Escribe el primer número con el que quieres operar:\n"))
        number1 = int(input("Escribe el segundo número con el que quieres operar:\n"))
        break
    except ValueError:
        print("Por favor, introduce un número válido.")

# Importamos las operaciones del otro archivo y operamos con ellas en este para no volver a escribirlas.
import operaciones

while True:
    while True:
        try:
            opcion = int(input("Qué quieres hacer con los números?\n 1.Sumar\n 2.Restar\n 3.Multiplicar\n 4.Dividir\n"))
            if opcion in [1, 2, 3, 4]:
                break
            else:
                print("Por favor, elige una opción válida.")
        except ValueError:
            print("Por favor, introduce un número válido.")

    print(f"Has elegido los números: {number} y {number1}")

    if opcion == 1:
        print("La suma de ambos valores es:", operaciones.suma(number, number1))
    elif opcion == 2:
        print("La resta del primer valor menos el segundo valor es:", operaciones.resta(number, number1))
    elif opcion == 3:
        print("La multiplicación es:", operaciones.multiplication(number, number1))
    elif opcion == 4:
        print("La división es:", operaciones.division(number, number1))

    otra_operacion = input("¿Quieres hacer otra operación? (S/N):\n")
    if otra_operacion.upper() != 'S':
        break

    mismos_numeros = input("¿Quieres usar los mismos números? (S/N):\n")
    if mismos_numeros.upper() != 'S':
        while True:
            try:
                number = int(input("Escribe el primer número con el que quieres operar:\n"))
                number1 = int(input("Escribe el segundo número con el que quieres operar:\n"))
                break
            except ValueError:
                print("Por favor, introduce un número válido.")