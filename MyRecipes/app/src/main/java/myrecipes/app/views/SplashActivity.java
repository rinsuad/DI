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
    private static final int SPLASH_DURATION = 5000; // Animation time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Load and play the GIF
        ImageView splashGif = findViewById(R.id.splash_gif);
        Glide.with(this)
                .asGif()
                .load(R.raw.splash_gif)
                .into(splashGif);

        // Delay and then check auth state
        new Handler(Looper.getMainLooper()).postDelayed(this::checkAuthAndNavigate, SPLASH_DURATION);
    }

    private void checkAuthAndNavigate() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;

        if (currentUser != null) {
            // User is signed in, go to main activity
            intent = new Intent(this, MainActivity.class);
        } else {
            // No user signed in, go to login
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}