package it.gamesandapps.k_launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        ListView lv = new ListView(this);
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

        Collections.sort(apps, new Comparator<AppObj>() {
            @Override
            public int compare(AppObj app1, AppObj app2) {
                return app1.getName().compareTo(app2.getName());
            }
        });

        final AppAdapter adapter = new AppAdapter(this, apps);
        lv.setAdapter(adapter);
        setContentView(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                Toast.makeText(MainActivity.this, adapter.getItem(pos).getPkg(), Toast.LENGTH_SHORT).show();
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(adapter.getItem(pos).getPkg());
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }

            }
        });

    }
}
