package it.gamesandapps.k_launcher;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import it.gamesandapps.k_launcher.adapters.AppAdapter;
import it.gamesandapps.k_launcher.adapters.PageAdapter;
import it.gamesandapps.k_launcher.objects.AppObj;
import it.gamesandapps.k_launcher.controllers.SlideController;
import it.gamesandapps.k_launcher.utils.Utils;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    ListView lvApps;

    ArrayList<PageFragment> pages;
    ArrayList<AppObj> apps;
    PageAdapter adapter;

    AppAdapter adapter_list;

    int view_type = Utils.VIEW_GRID;
    int view_grid = Utils.GRID_4x5;

    SlideController slideController;
    ViewPager.PageTransformer selectedEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getActionBar()!=null) getActionBar().setTitle("");
        super.onCreate(savedInstanceState);

        slideController = new SlideController();
        selectedEffect = slideController.getEffect(SlideController.DEFAULT);
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

            case R.id.action_grid_size:
                ShowGridSelector();
                return true;

            case R.id.action_slide_effects:
                ShowSlideSelector();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SwitchLayout(){

        switch (view_type){
            case Utils.VIEW_GRID:
                LoadLayoutGrid();
                view_type = Utils.VIEW_LIST;
                break;

            case Utils.VIEW_LIST:
                LoadLayoutList();
                view_type = Utils.VIEW_GRID;
                break;
        }
    }

    private void LoadLayoutGrid(){
        setContentView(R.layout.activity_main_grid);

        pages = new ArrayList<>();
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setPageTransformer(false, selectedEffect);
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
        LoadFragments(view_grid);
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

    private void LoadFragments(int grid_dimension){

        if(pages!=null)
            if(!pages.isEmpty())
                pages.clear();

        int count = 1;
        ArrayList<AppObj> pageApps = new ArrayList<>();
        for(AppObj a : apps){
            pageApps.add(a);

            if(pageApps.size() >= grid_dimension || count == apps.size()){

                ArrayList<AppObj> pApps = new ArrayList<>();
                pApps.addAll(pageApps);
                pages.add(new PageFragment().create(pApps, grid_dimension));
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

            case Utils.VIEW_GRID:
                adapter_list.notifyDataSetChanged();
                break;

            case Utils.VIEW_LIST:
                LoadFragments(view_grid);
                break;
        }
    }

    private void ShowGridSelector(){

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_single_choice);
        adapter.add("3x3");
        adapter.add("3x4");
        adapter.add("4x4");
        adapter.add("4x5");

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar)
                .setTitle("Select grid dimension")
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        switch (pos){
                            case 0:
                                view_grid = Utils.GRID_3x3;
                                break;
                            case 1:
                                view_grid = Utils.GRID_3x4;
                                break;
                            case 2:
                                view_grid = Utils.GRID_4x4;
                                break;
                            case 3:
                                view_grid = Utils.GRID_4x5;
                                break;
                        }

                        LoadLayoutGrid();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void ShowSlideSelector(){

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_single_choice);
        adapter.addAll(slideController.getEffectsNames());

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar)
                .setTitle("Select slide effect")
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {

                        /*if(pager!=null)
                            pager.setPageTransformer(false, slideController.getEffect(pos));
                        LoadLayoutGrid();*/

                        selectedEffect = slideController.getEffect(adapter.getItem(pos));
                        LoadLayoutGrid();

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
