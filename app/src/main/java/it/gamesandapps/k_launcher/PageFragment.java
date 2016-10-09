package it.gamesandapps.k_launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import it.gamesandapps.k_launcher.objects.AppObj;
import it.gamesandapps.k_launcher.utils.Utils;

public class PageFragment extends Fragment {

    public static String APPS = "apps";
    public static String GRID_DIMENSION = "grid_dimen";
    private ArrayList<AppObj> apps;
    private int grid_imension;
    private GridLayout grid;

    public static PageFragment create(ArrayList<AppObj> apps, int grid_imension){
        Bundle b = new Bundle();
        b.putInt(GRID_DIMENSION, grid_imension);
        b.putParcelableArrayList(APPS, apps);
        PageFragment f = new PageFragment();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Bundle b = getArguments();
        if(b!=null) {
            if(b.containsKey(APPS)) {
                this.apps = b.getParcelableArrayList(APPS);
            }
            if(b.containsKey(GRID_DIMENSION)) {
                this.grid_imension = b.getInt(GRID_DIMENSION);
            }
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page, container, false);
        grid = (GridLayout)root.findViewById(R.id.appGrid);

        switch(grid_imension){
            case Utils.GRID_3x3:
                grid.setColumnCount(3);
                break;

            case Utils.GRID_3x4:
                grid.setColumnCount(3);
                break;

            case Utils.GRID_4x4:
                grid.setColumnCount(4);
                break;

            case Utils.GRID_4x5:
                grid.setColumnCount(4);
                break;

            case Utils.GRID_5x5:
                grid.setColumnCount(5);
                break;
        }

        int height = getActivity().getWindow().getDecorView().getHeight();
        int width = getActivity().getWindow().getDecorView().getWidth();

        for(final AppObj app : apps){

            final View appview = getActivity().getLayoutInflater().inflate(R.layout.app_item_layout, container, false);

            appview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.OpenApp(getActivity(),app);
                }
            });

            appview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Utils.UnistallApp(getActivity(), app.getPkg());
                    return true;
                }
            });

            TextView tv_name = (TextView) appview.findViewById(R.id.tv_app_name);
            ImageView iv_icon = (ImageView) appview.findViewById(R.id.iv_app_icon_list);

            tv_name.setText(app.getName());
            iv_icon.setImageDrawable(app.getIcon());

            grid.addView(appview);
        }


        return root;
    }

}
