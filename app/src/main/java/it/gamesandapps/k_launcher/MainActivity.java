package it.gamesandapps.k_launcher;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import com.eftimoff.viewpagertransformers.RotateDownTransformer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import it.gamesandapps.k_launcher.adapters.AppAdapter;
import it.gamesandapps.k_launcher.adapters.PageAdapter;
import it.gamesandapps.k_launcher.objects.AppObj;
import it.gamesandapps.k_launcher.utils.Utils;

public class MainActivity extends FragmentActivity {

    ViewPager pager;
    ListView lvApps;

    ArrayList<PageFragment> pages;
    ArrayList<AppObj> apps;
    PageAdapter adapter;

    AppAdapter adapter_list;

    final static int VIEW_GRID = 1;
    final static int VIEW_LIST = 2;

    int view_type = VIEW_GRID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getActionBar()!=null) getActionBar().setTitle("");
        super.onCreate(savedInstanceState);

        SwitchLayout();
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

            case R.id.action_view_type:
                SwitchLayout();
                return true;

            case R.id.action_order:
                if(apps!=null)
                    ReverseOrder(apps);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SwitchLayout(){
        switch (view_type){
            case VIEW_GRID:
                LoadLayoutGrid();
                view_type = VIEW_LIST;
                break;

            case VIEW_LIST:
                LoadLayoutList();
                view_type = VIEW_GRID;
                break;
        }
    }

    private void LoadLayoutGrid(){
        setContentView(R.layout.activity_main_grid);
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

        AlphabOrder(apps);
        LoadFragments();
    }

    private void LoadLayoutList(){
        setContentView(R.layout.activity_main_list);

        lvApps = (ListView)findViewById(R.id.lvApps);
        adapter_list = new AppAdapter(this, apps);
        lvApps.setAdapter(adapter_list);
        lvApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Utils.OpenApp(MainActivity.this, apps.get(i));
            }
        });
        lvApps.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Utils.UnistallApp(MainActivity.this, apps.get(i).getPkg());
                return true;
            }
        });

    }

    private void LoadFragments(){

        if(pages!=null)
            if(!pages.isEmpty())
                pages.clear();

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

        adapter = new PageAdapter(getSupportFragmentManager(), pages);
        pager.setAdapter(adapter);

    }

    private void AlphabOrder(ArrayList<AppObj> apps){

        Collections.sort(apps, new Comparator<AppObj>() {
            @Override
            public int compare(AppObj app1, AppObj app2) {
                return app1.getName().compareTo(app2.getName());
            }
        });
    }

    private void ReverseOrder(ArrayList<AppObj> apps) {
        Collections.reverse(apps);
        switch (view_type){

            case VIEW_GRID:
                adapter_list.notifyDataSetChanged();
                break;

            case VIEW_LIST:
                LoadFragments();
                break;
        }
    }
}
