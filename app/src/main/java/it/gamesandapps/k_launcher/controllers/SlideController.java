package it.gamesandapps.k_launcher.controllers;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.BackgroundToForegroundTransformer;
import com.eftimoff.viewpagertransformers.CubeInTransformer;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.eftimoff.viewpagertransformers.DefaultTransformer;
import com.eftimoff.viewpagertransformers.DrawFromBackTransformer;
import com.eftimoff.viewpagertransformers.FlipHorizontalTransformer;
import com.eftimoff.viewpagertransformers.FlipVerticalTransformer;
import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.eftimoff.viewpagertransformers.RotateDownTransformer;
import com.eftimoff.viewpagertransformers.RotateUpTransformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SlideController {

    HashMap<String,ViewPager.PageTransformer> slideEffects = new HashMap<>();

    public static String DEFAULT = "Default";
    public static String CUBE_IN = "Cube in";
    public static String CUBE_OUT = "Cube out";
    public static String ROTATE_DOWN = "Rotate down";
    public static String ROTATE_UP = "Rotate up";
    public static String FLIP_VERT = "Flip vertical";
    public static String FLIP_HORIZ = "Flip horizontal";
    public static String FROM_BACK = "Draw from back";
    public static String ACCORDION = "Accordion";
    public static String BG_TO_FG = "Back to front";
    public static String FG_TO_BG = "Front to back";


    public SlideController() {

        this.slideEffects = new HashMap<>();

        slideEffects.put(DEFAULT,   new DefaultTransformer());
        slideEffects.put(CUBE_IN,   new CubeInTransformer());
        slideEffects.put(CUBE_OUT,  new CubeOutTransformer());
        slideEffects.put(CUBE_OUT,  new CubeOutTransformer());
        slideEffects.put(ROTATE_DOWN,  new RotateDownTransformer());
        slideEffects.put(ROTATE_UP,  new RotateUpTransformer());
        slideEffects.put(FLIP_VERT,  new FlipVerticalTransformer());
        slideEffects.put(FLIP_HORIZ,  new FlipHorizontalTransformer());
        slideEffects.put(FROM_BACK,  new DrawFromBackTransformer());
        slideEffects.put(ACCORDION,  new AccordionTransformer());
        slideEffects.put(BG_TO_FG,  new BackgroundToForegroundTransformer());
        slideEffects.put(FG_TO_BG,  new ForegroundToBackgroundTransformer());

    }


    public HashMap<String,ViewPager.PageTransformer> getEffects() {
        return this.slideEffects;
    }

    public ViewPager.PageTransformer getEffect(String effect){
        return this.slideEffects.get(effect);
    }

    public ViewPager.PageTransformer getEffect(int pos){
        ArrayList<ViewPager.PageTransformer> effects = new ArrayList<>();
        for (String s : slideEffects.keySet()) {
            effects.add(slideEffects.get(s));
        }
        return effects.get(pos);
    }

    public ArrayList<String> getEffectsNames() {

        ArrayList<String> names = new ArrayList<>();
        for (String s : slideEffects.keySet()) {
            names.add(s);
        }

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });

        return names;
    }

    public int getCount() {
        return this.slideEffects.size();
    }

}
