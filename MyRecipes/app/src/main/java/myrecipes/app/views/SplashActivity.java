/**
 * This is the entry point of the application, showing a splash screen with animation
 * while performing initial authentication checks.
 * Key responsibilities:
 * 1. Display welcome animation
 * 2. Check authentication state
 * 3. Route to appropriate screen (Login or Main)
 */
package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import myrecipes.app.R;

public class SplashActivity extends AppCompatActivity {
    // Duration for showing the splash animation (5 seconds)
    private static final int SPLASH_DURATION = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Load and display the animated GIF using Glide
        // Glide handles efficient loading and caching of the animation
        ImageView splashGif = findViewById(R.id.splash_gif);
        Glide.with(this)
                .asGif()                      // Specify that we're loading a GIF
                .load(R.raw.splash_gif)       // Load from raw resources
                .into(splashGif);             // Target ImageView

        // Schedule authentication check after animation
        // Using Handler ensures we stay on the main thread
        new Handler(Looper.getMainLooper()).postDelayed(
                this::checkAuthAndNavigate,  // Method reference to auth check
                SPLASH_DURATION              // Delay duration
        );
    }

    /**
     * Checks if user is already authenticated and navigates accordingly.
     * - If user is logged in -> MainActivity
     * - If no user session -> LoginActivity
     * This method demonstrates the common pattern of checking authentication
     * state before allowing access to the main app features.
     */
    private void checkAuthAndNavigate() {
        // Get current Firebase user (null if not logged in)
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;

        if (currentUser != null) {
            // User is already authenticated, skip login flow
            intent = new Intent(this, MainActivity.class);
        } else {
            // No authenticated user, must log in
            intent = new Intent(this, LoginActivity.class);
        }

        // Start appropriate activity and remove splash from back stack
        startActivity(intent);
        finish();
    }
}