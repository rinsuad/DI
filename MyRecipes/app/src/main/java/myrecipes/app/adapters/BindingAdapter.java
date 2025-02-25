package myrecipes.app.adapters;

import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * This class provides custom data binding adapters for Android's Data Binding Library.
 * Data Binding adapters allow you to specify how custom properties in XML layouts
 * should be handled, extending the standard Android data binding capabilities.
 */

public class BindingAdapter {
    /**
     * Custom binding adapter for loading images using Glide.
     * This adapter can be used in XML layouts with the 'app:imageUrl' attribute.
     *
     * Usage in XML:
     * <ImageView
     *     android:layout_width="wrap_content"
     *     android:layout_height="wrap_content"
     *     app:imageUrl="@{recipe.imageUrl}" />
     *
     * @param imageView The ImageView target where the image will be loaded
     * @param url The URL of the image to load
     */
    @androidx.databinding.BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (url != null) {
            // Use Glide to handle image loading
            // Glide handles:
            // - Memory and disk caching
            // - Image downloading
            // - Bitmap pooling
            // - Image resizing and scaling
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }
}
