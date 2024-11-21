package com.example.mycatalog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load initial fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new CatalogFragment())
                    .commit();
        }

        // Configuration of BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_catalog && !(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) instanceof CatalogFragment)) {
                selectedFragment = new CatalogFragment();
                // Animation configuration for the transition between fragments
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            } else if (item.getItemId() == R.id.nav_about && !(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) instanceof AboutFragment)) {
                selectedFragment = new AboutFragment();
                // Animation configuration for the transition between fragments
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
}
