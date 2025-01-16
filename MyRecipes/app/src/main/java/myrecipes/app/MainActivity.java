package myrecipes.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase Authentication y Database
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("users");

        // Configurar botones
        findViewById(R.id.registerButton).setOnClickListener(v -> registerUser());
        findViewById(R.id.loginButton).setOnClickListener(v -> loginUser());

        // Leer datos del usuario actual (si está autenticado)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            readUserData(currentUser.getUid());
        }
    }

    private void registerUser() {
        // Obtener datos de los campos de texto
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString().trim();

        // Validar campos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                // Agregar un listener para saber cuándo se completó el registro
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Crear objeto usuario y guardar en la base de datos
                            String uid = user.getUid();
                            User newUser = new User(uid, email);
                            databaseRef.child(uid).setValue(newUser)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(this, "Usuario registrado y guardado correctamente.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.e("Firebase", "Error al guardar usuario en la base de datos", dbTask.getException());
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginUser() {
        // Obtener datos de los campos de texto
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString().trim();

        // Validar campos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Iniciar sesión con Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                // Agregar un listener para saber cuándo se completó el inicio de sesión
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            readUserData(user.getUid());
                        }
                    } else {
                        Toast.makeText(this, "Error en autenticación: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void readUserData(String uid) {
        // Leer datos del usuario en la base de datos
        databaseRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Convertir datos a objeto User
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        Log.d("Firebase", "Usuario leído: " + user.getEmail());
                    }
                } else {
                    Log.w("Firebase", "No se encontró información para el usuario.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores
                Log.e("Firebase", "Error al leer datos: " + error.getMessage(), error.toException());
            }
        });
    }

    // Clase User para representar los datos de usuario
    public static class User {
        private String uid;
        private String email;

        public User() {
            // Constructor vacío requerido para Firebase
        }

        public User(String uid, String email) {
            this.uid = uid;
            this.email = email;
        }

        public String getUid() {
            return uid;
        }

        public String getEmail() {
            return email;
        }
    }
}