package it.gamesandapps.k_launcher.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import it.gamesandapps.k_launcher.objects.AppObj;

public class AppAdapter extends ArrayAdapter<AppObj> {

    Context ctx;
    ArrayList<AppObj> apps;

    public AppAdapter( Context ctx, ArrayList<AppObj> apps) {
        super(ctx, -1, apps);
        this.ctx = ctx;
        this.apps = apps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AppObj app = apps.get(position);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        LinearLayout root = new LinearLayout(ctx);

        root.setLayoutParams(params);
        root.setPadding(10,10,10,10);
        TextView tv = new TextView(ctx);
        tv.setGravity(Gravity.CENTER);
        tv.setCompoundDrawablesRelativeWithIntrinsicBounds(app.getIcon(),null,null,null);
        tv.setCompoundDrawablePadding(12);
        tv.setTextSize(20);
        tv.setText(app.getName());

        root.addView(tv);

        return root;

    }
}
