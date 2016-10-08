package it.gamesandapps.k_launcher.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import it.gamesandapps.k_launcher.PageFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<PageFragment> pages;

    public PageAdapter(FragmentManager fm, ArrayList<PageFragment> pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
