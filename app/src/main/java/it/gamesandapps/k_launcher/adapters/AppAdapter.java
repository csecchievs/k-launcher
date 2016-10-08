package it.gamesandapps.k_launcher.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import it.gamesandapps.k_launcher.R;
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

        Holder holder;

        if(convertView==null) {

            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            convertView = inflater.inflate(R.layout.app_item_layout, parent, false);

            holder = new Holder();
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_app_name);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);

            if(apps!=null){

                AppObj app = apps.get(position);
                holder.iv_icon.setImageDrawable(app.getIcon());
                holder.tv_name.setText(app.getName());
            }

        }

        return convertView;
    }

    static class Holder {
        TextView tv_name;
        ImageView iv_icon;
    }
}
