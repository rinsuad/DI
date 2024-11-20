package com.example.mycatalog;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView(R.layout.detail_activity);
        }
        else {
            setContentView(R.layout.activity_detail);
        }
        ImageView imageView = findViewById(R.id.avatar);
        Picasso.get().load(R.drawable.image)
                .transform(new CircleTransform())
                .into(imageView);


    }
}