package it.gamesandapps.k_launcher;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import it.gamesandapps.k_launcher.adapters.AppAdapter;
import it.gamesandapps.k_launcher.objects.AppObj;

public class MainActivity extends FragmentActivity {

    ViewPager pager;
    ArrayList<PageFragment> pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pages = new ArrayList<>();

        pager = (ViewPager)findViewById(R.id.pager);


        ArrayList<AppObj> apps = new ArrayList<>();

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(mainIntent, 0);

        for (ResolveInfo info: list){

            String name = info.loadLabel(getPackageManager()).toString();
            String pkg = info.activityInfo.packageName;
            Drawable icon = info.loadIcon(getPackageManager());

            apps.add(new AppObj(name,pkg,icon));
        }

        /*
        Collections.sort(apps, new Comparator<AppObj>() {
            @Override
            public int compare(AppObj app1, AppObj app2) {
                return app1.getName().compareTo(app2.getName());
            }
        });
        */

        int count = 0;
        ArrayList<AppObj> pageApps = new ArrayList<>();

        for(AppObj a : apps){
            pageApps.add(a);

            if(pageApps.size() >= 10 || count == apps.size()){

                ArrayList<AppObj> pApps = new ArrayList<>();
                pApps.addAll(pageApps);
                pages.add(new PageFragment().create(pApps));
                pageApps.clear();

            }
            count++;
        }

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem() > 0)
            pager.setCurrentItem(pager.getCurrentItem()-1);
    }

    private class PageAdapter extends FragmentStatePagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
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
}
