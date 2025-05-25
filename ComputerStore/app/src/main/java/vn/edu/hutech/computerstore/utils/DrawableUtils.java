package vn.edu.hutech.computerstore.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import lombok.experimental.UtilityClass;
import vn.edu.hutech.computerstore.R;

@UtilityClass
public final class DrawableUtils {

    public static Drawable getCircularProgress(Context context) {
        CircularProgressDrawable drawable = new CircularProgressDrawable(context);
        drawable.setColorSchemeColors(R.color.primary, R.color.primary_dark, R.color.primary_accent);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        drawable.start();
        return drawable;
    }

}
