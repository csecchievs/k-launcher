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
import android.widget.TextView;
import java.util.ArrayList;
import it.gamesandapps.k_launcher.objects.AppObj;
import it.gamesandapps.k_launcher.utils.Utils;

public class PageFragment extends Fragment {

    public static String APPS = "apps";
    private ArrayList<AppObj> apps;
    private GridLayout grid;

    public static PageFragment create(ArrayList<AppObj> apps){
        Bundle b = new Bundle();
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
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page, container, false);
        grid = (GridLayout)root.findViewById(R.id.appGrid);

        GridLayout.LayoutParams params = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            params = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),GridLayout.spec(GridLayout.UNDEFINED, 1f));
            params.setGravity(Gravity.CENTER);
            params.setMargins(10,10,10,10);
        }

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
