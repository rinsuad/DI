# Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
# desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
# contenido.

import tkinter as tk

# We define the function that will add some text to the text widget
def some_text():
    for i in range (1, 100):
        text_frame.insert("end", f"Linea {i}\n")

# We set up the main window
root = tk.Tk()
root.title("Ejercicio 10")
root.geometry("300x300")

# We create the frame and the text widget
frame = tk.Frame(root)
frame.pack(pady=5, padx=5, expand=True, fill='both')

text_frame = tk.Text(frame, wrap="none")
text_frame.grid(row=0, column=0, sticky="nsew")

# We create the scrollbar and link it to the text widget
scroll_y = tk.Scrollbar(frame, orient="vertical", command=text_frame.yview)
scroll_y.grid(row=0, column=1, sticky="ns")
text_frame.config(yscrollcommand=scroll_y.set)

# We configure the grid so that the text widget takes all the available space within the frame
frame.grid_rowconfigure(0, weight=1)
frame.grid_columnconfigure(0, weight=1)

# We insert some text
some_text()

root.mainloop()