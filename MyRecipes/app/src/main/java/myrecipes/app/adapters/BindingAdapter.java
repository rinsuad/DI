package myrecipes.app.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }
}
