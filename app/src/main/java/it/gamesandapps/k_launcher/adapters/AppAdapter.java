package it.gamesandapps.k_launcher.adapters;

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

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.app_item_layout_list, parent, false);

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_app_name_list);
        ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon_list);

        AppObj app = apps.get(position);

        tv_name.setText(app.getName());
        iv_icon.setImageDrawable(app.getIcon());

        return convertView;

    }
}
