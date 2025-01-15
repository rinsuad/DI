package myrecipes.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView, caloriesTextView, categoryTextView, ingredientsTextView, stepsTextView;
    private ImageView imageView;
    private Button logoutButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        caloriesTextView = findViewById(R.id.caloriesTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        stepsTextView = findViewById(R.id.stepsTextView);
        imageView = findViewById(R.id.imageView);
        logoutButton = findViewById(R.id.logoutButton);

        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("recipes");

        loadRecipeData();

        logoutButton.setOnClickListener(v -> logout());
    }

    private void loadRecipeData() {
        // Cargar un único elemento desde Firebase
        databaseRef.child("avena_kinder_bueno").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        // Obtener los valores de Firebase
                        String title = (String) dataSnapshot.child("title").getValue();
                        String description = (String) dataSnapshot.child("description").getValue();
                        String imageUrl = (String) dataSnapshot.child("imageUrl").getValue();
                        String category = (String) dataSnapshot.child("category").getValue();

                        // Verificar si los valores son numéricos y convertirlos a String
                        Object caloriesObj = dataSnapshot.child("calorias_totales").getValue();
                        String calories = (caloriesObj instanceof Long) ? String.valueOf(caloriesObj) : (String) caloriesObj;

                        // Verificar los ingredientes
                        Object ingredientsObj = dataSnapshot.child("ingredients").getValue();
                        String ingredients = formatIngredientsList(ingredientsObj);

                        // Verificar los pasos
                        Object stepsObj = dataSnapshot.child("steps").getValue();
                        String steps = formatStepsList(stepsObj);

                        // Asignar los valores a los TextViews y la ImageView
                        titleTextView.setText(title != null ? title : "Título no disponible");
                        descriptionTextView.setText(description != null ? description : "Descripción no disponible");
                        Glide.with(DashboardActivity.this).load(imageUrl).into(imageView);

                        // Mostrar las calorías con el texto adicional
                        caloriesTextView.setText("Calorías totales: " + (calories != null ? calories : "0"));

                        // Mostrar la categoría con el texto adicional
                        categoryTextView.setText("Categoría: " + (category != null ? category : "Categoría no disponible"));

                        // Mostrar los ingredientes y los pasos con formato
                        ingredientsTextView.setText(ingredients != null ? ingredients : "Ingredientes no disponibles");
                        stepsTextView.setText(steps != null ? steps : "Pasos no disponibles");

                    } catch (Exception e) {
                        // Manejar cualquier error durante la obtención de los datos
                        Toast.makeText(DashboardActivity.this, "Error al cargar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("DashboardActivity", "Error al obtener los datos", e);
                    }

                } else {
                    Toast.makeText(DashboardActivity.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DashboardActivity.this, "Error al cargar datos: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("DashboardActivity", "Database error", databaseError.toException());
            }
        });
    }

    // Método para formatear los ingredientes con calorías y cantidad
    private String formatIngredientsList(Object ingredientsObj) {
        StringBuilder formattedIngredients = new StringBuilder();
        if (ingredientsObj instanceof List) {
            List<?> list = (List<?>) ingredientsObj;
            for (Object item : list) {
                if (item instanceof List) {
                    List<?> ingredientDetails = (List<?>) item;
                    if (ingredientDetails.size() == 3) {
                        String ingredientName = ingredientDetails.get(0).toString();
                        String calories = ingredientDetails.get(1).toString();
                        String quantity = ingredientDetails.get(2).toString();
                        formattedIngredients.append("- ").append(ingredientName).append(", ").append(calories).append(" calorías, ").append(quantity).append("\n");
                    }
                }
            }
        }
        return formattedIngredients.toString();
    }

    // Método para formatear los pasos sin comillas
    private String formatStepsList(Object stepsObj) {
        StringBuilder formattedSteps = new StringBuilder();
        if (stepsObj instanceof List) {
            List<?> list = (List<?>) stepsObj;
            for (Object step : list) {
                if (step != null) {
                    formattedSteps.append("- ").append(step.toString()).append("\n");
                }
            }
        } else if (stepsObj instanceof String) {
            formattedSteps.append(stepsObj.toString());
        }
        return formattedSteps.toString();
    }

    // Método para formatear pasos si es necesario (lo mismo que antes)
    private String formatList(Object listObj) {
        StringBuilder formattedList = new StringBuilder();
        if (listObj instanceof List) {
            List<?> list = (List<?>) listObj;
            for (Object item : list) {
                if (item != null) {
                    formattedList.append(item.toString()).append("\n");
                }
            }
        } else if (listObj instanceof String) {
            formattedList.append(listObj.toString());
        }
        return formattedList.toString();
    }

    private void logout() {
        mAuth.signOut();
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
