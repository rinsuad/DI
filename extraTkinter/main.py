'''La correcta realización de esta tarea suma 0,3 puntos a la nota final.
Debéis implementar un juego de piedra, papel, tijera en TKINTER.
Habrá un menú que me permita seleccionar la partida de un jugador (contra la máquina), dos jugadores o salir del programa.
En cada partida se jugará hasta que uno de los dos participantes logre 3 puntos.
En cada jugada se recibirá información de lo que ha sacado el jugador 1, lo que ha sacado el jugador dos y el resultado.
Por ejemplo:
"El jugador uno saca piedra , el jugador dos saca papel. Gana el jugador dos, papel gana a piedra"
Los empates no cuentan.
Tú decides la interfaz, qué widgets usar, uso de messagebox o no...
Encapsula en funciones todo el codigo susceptible de agruparse, para facilitar la legibilidad
Puedes investigar el uso de TopLevel() por si quieres usar ventanas distintas a la principal.
Sólo puedes usar código que hemos visto en clase.'''

import tkinter as tk
from cProfile import label
from tkinter import Toplevel
import random

choices = ["piedra", "papel", "tijera"]


# Function to determine the winner
def determine_winner(player1_choice, player2_choice):
    player1_choice = player1_choice.lower()
    player2_choice = player2_choice.lower()
    if player1_choice == player2_choice:
        return "Empate"
    elif (player1_choice == "piedra" and player2_choice == "tijera") or \
            (player1_choice == "tijera" and player2_choice == "papel") or \
            (player1_choice == "papel" and player2_choice == "piedra"):
        return "Jugador 1"
    else:
        return "Jugador 2"


# Function to play a single round and show the choice of each player with the result
def play_round(player1_choice, player2_choice):
    winner = determine_winner(player1_choice, player2_choice)
    if winner == "Empate":
        return "Empate. Nadie gana esta ronda."
    else:
        print(f"Jugador 1 eligió {player1_choice} y Jugador 2 eligió {player2_choice}. {winner} gana esta ronda.")
        return winner


# Function to simulate a single player game
def single_player_game():
    w1 = tk.Toplevel(root)
    w1.title("Un jugador")
    w1.geometry("600x400")

    player1_score = 0
    player2_score = 0

    def play():
        nonlocal player1_score, player2_score
        player1_choice = player1_entry.get().lower()
        player2_choice = random.choice(choices)
        print(player1_choice)
        print(player2_choice)
        result = play_round(player1_choice, player2_choice)
        if "Empate" in result:
            result_label.config(text=f"Jugador 1: {player1_choice} vs Jugador 2: {player2_choice}\n {result}")
        else:
            result_label.config(
                text=f"Jugador 1: {player1_choice} vs Jugador 2: {player2_choice}\n {result} gana la ronda.")
        if "Jugador 1" in result:
            player1_score += 1
        elif "Jugador 2" in result:
            player2_score += 1
        score_label.config(text=f"Jugador 1: {player1_score} - Jugador 2: {player2_score}")
        if player1_score == 3 or player2_score == 3:
            winner = "Jugador 1" if player1_score == 3 else "Jugador 2"
            score_label.config(text=f"Jugador 1 eligió {player1_choice} y Jugador 2 eligió {player2_choice}.")
            result_label.config(text=f"{winner} gana el juego!")
            play_button.config(state=tk.DISABLED)

    player1_label = tk.Label(w1, text="Jugador 1", font=("Arial", 15))
    player1_label.pack(pady=10)
    player1_entry = tk.Entry(w1)
    player1_entry.pack(pady=10)
    player2_label = tk.Label(w1, text="Jugador 2, es la máquina.", font=("Arial", 15))
    player2_label.pack(pady=10)
    play_button = tk.Button(w1, text="Jugar", command=play)
    play_button.pack(pady=20)
    result_label = tk.Label(w1, text="", font=("Arial", 15))
    result_label.pack(pady=20)
    score_label = tk.Label(w1, text=f"Jugador 1: {player1_score} - Jugador 2: {player2_score}", font=("Arial", 15))
    score_label.pack(pady=20)


# Function to simulate a multiplayer game
def multi_player_game():
    w2 = tk.Toplevel(root)
    w2.title("Dos jugadores")
    w2.geometry("600x400")

    player1_score = 0
    player2_score = 0

    def play():
        nonlocal player1_score, player2_score
        player1_choice = player1_entry.get().lower()
        player2_choice = player2_entry.get().lower()
        print(player1_choice)
        print(player2_choice)
        result = play_round(player1_choice, player2_choice)
        if "Empate" in result:
            result_label.config(text=f"Jugador 1: {player1_choice} vs Jugador 2: {player2_choice}\n {result}")
        else:
            result_label.config(
                text=f"Jugador 1: {player1_choice} vs Jugador 2: {player2_choice}\n {result} gana la ronda.")
        if "Jugador 1" in result:
            player1_score += 1
        elif "Jugador 2" in result:
            player2_score += 1
        score_label.config(text=f"Jugador 1: {player1_score} - Jugador 2: {player2_score}")
        if player1_score == 3 or player2_score == 3:
            winner = "Jugador 1" if player1_score == 3 else "Jugador 2"
            score_label.config(text=f"Jugador 1 eligió {player1_choice} y Jugador 2 eligió {player2_choice}.")
            result_label.config(text=f"{winner} gana el juego!")
            play_button.config(state=tk.DISABLED)

    player1_label = tk.Label(w2, text="Jugador 1", font=("Arial", 15))
    player1_label.pack(pady=10)
    player1_entry = tk.Entry(w2)
    player1_entry.pack(pady=10)
    player2_label = tk.Label(w2, text="Jugador 2", font=("Arial", 15))
    player2_label.pack(pady=10)
    player2_entry = tk.Entry(w2)
    player2_entry.pack(pady=10)
    play_button = tk.Button(w2, text="Jugar", command=play)
    play_button.pack(pady=20)
    result_label = tk.Label(w2, text="", font=("Arial", 15))
    result_label.pack(pady=20)
    score_label = tk.Label(w2, text="Jugador 1: 0 - Jugador 2: 0", font=("Arial", 15))
    score_label.pack(pady=20)


# Function to exit the app
def exit():
    root.quit()


# We create the main window
root = tk.Tk()
root.title("Piedra, papel, tijera")
root.geometry("550x350")

# We create the menu
main_menu = tk.Menu(root)
root.config(menu=main_menu)

# We create the options menu
option_menu = tk.Menu(main_menu, tearoff=0)
main_menu.add_cascade(label="Opciones", menu=option_menu)
option_menu.add_command(label="Un jugador", command=single_player_game)
option_menu.add_separator()
option_menu.add_command(label="Dos jugadores", command=multi_player_game)
option_menu.add_separator()
option_menu.add_command(label="Salir", command=exit)

# We create the main frame with a widget for the title and a second frame for the rules
widget = tk.Label(root, text="Bienvenido al juego de piedra, papel, tijera", font=("Arial", 15))
widget.pack(pady=20)
main_frame = tk.Frame(root, bg="darkgrey", bd=2, relief="groove")
main_frame.place(anchor="c", relx=0.5, rely=0.5)

# We create the label with the rules for the game
game_rules = tk.Label(main_frame, font=("Arial", 10),
                      text="Reglas: \n" + "Piedra gana a tijera\n" + "Tijera gana a papel\n" + "Papel gana a piedra\n" + "El primero en llegar a 3 puntos gana\n" + "Los empates no cuentan")
game_rules.pack(pady=20)

root.mainloop()
