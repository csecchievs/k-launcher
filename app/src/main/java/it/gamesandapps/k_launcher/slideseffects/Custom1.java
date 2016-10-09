package it.gamesandapps.k_launcher.slideseffects;

import android.view.View;

import com.eftimoff.viewpagertransformers.BaseTransformer;


public class Custom1 extends BaseTransformer {

    public Custom1(){}

    @Override
    protected void onTransform(View view, float position) {

        //view.setPivotX(position > 0.0F ? 0.0F : (float)view.getWidth());
        view.setPivotX(0.0F);
        view.setPivotY(0.0F);
        view.setRotation(-90.0F * position);
    }

    public boolean isPagingEnabled() {
        return true;
    }
}
