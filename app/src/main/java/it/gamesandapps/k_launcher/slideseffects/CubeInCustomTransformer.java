package it.gamesandapps.k_launcher.slideseffects;

import android.view.View;

import com.eftimoff.viewpagertransformers.BaseTransformer;


public class CubeInCustomTransformer extends BaseTransformer {

    public CubeInCustomTransformer(){}

    @Override
    protected void onTransform(View view, float position) {

        int height = view.getHeight();

        view.setPivotX(position > 0.0F ? 0.0F : (float)view.getWidth());
        view.setPivotY(height/2);
        view.setRotationY(-30.0F * position);
    }

    public boolean isPagingEnabled() {
        return true;
    }
}
