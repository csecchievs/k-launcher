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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.eftimoff.viewpagertransformers.DepthPageTransformer;
import com.eftimoff.viewpagertransformers.DrawFromBackTransformer;
import com.eftimoff.viewpagertransformers.FlipVerticalTransformer;
import com.eftimoff.viewpagertransformers.RotateDownTransformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import it.gamesandapps.k_launcher.objects.AppObj;

public class MainActivity extends FragmentActivity {

    ViewPager pager;
    ArrayList<PageFragment> pages;
    ArrayList<AppObj> apps;
    PageAdapter adapter;
    boolean order_ascend = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getActionBar()!=null)
            getActionBar().setTitle("");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pages = new ArrayList<>();

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setPageTransformer(false, new RotateDownTransformer());

        apps = new ArrayList<>();

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(mainIntent, 0);

        for (ResolveInfo info: list){

            String name = info.loadLabel(getPackageManager()).toString();
            String pkg = info.activityInfo.packageName;
            Drawable icon = info.loadIcon(getPackageManager());

            apps.add(new AppObj(name,pkg,icon));
        }

        OrdinaApp(apps, true);

        int count = 1;
        ArrayList<AppObj> pageApps = new ArrayList<>();
        for(AppObj a : apps){
            pageApps.add(a);

            if(pageApps.size() >= 20 || count == apps.size()){

                ArrayList<AppObj> pApps = new ArrayList<>();
                pApps.addAll(pageApps);
                pages.add(new PageFragment().create(pApps));
                pageApps.clear();

            }
            count++;
        }

        adapter = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem() > 0)
            pager.setCurrentItem(pager.getCurrentItem()-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_info:
                return true;

            case R.id.action_settings:

                if(apps!=null) {
                    order_ascend = !order_ascend;
                    OrdinaApp(apps, order_ascend);
                    if(adapter!=null)
                        adapter.notifyDataSetChanged();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void OrdinaApp(ArrayList<AppObj> apps, boolean ascendente){

        final boolean b = ascendente;
        Collections.sort(apps, new Comparator<AppObj>() {
            @Override
            public int compare(AppObj app1, AppObj app2) {
                if(b)
                    return app1.getName().compareTo(app2.getName());
                else
                    return app2.getName().compareTo(app1.getName());
            }
        });
    }
}
