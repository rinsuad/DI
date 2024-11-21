package com.example.mycatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CatalogFragment extends Fragment {

    @Nullable
    @Override
    // Method to create the view of the fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        // Button to navigate to the DetailFragment
        Button buttonDetail = view.findViewById(R.id.buttonDetail);
        buttonDetail.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new DetailFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
