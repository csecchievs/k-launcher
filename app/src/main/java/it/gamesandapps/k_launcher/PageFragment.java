package it.gamesandapps.k_launcher;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import it.gamesandapps.k_launcher.objects.AppObj;

public class PageFragment extends Fragment {

    public static String APPS = "apps";
    private ArrayList<AppObj> apps;

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

                String s = "";

                if(apps!=null)
                    for (AppObj a : apps) {
                        s += a.getName() + "\n";
                    }

                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }

        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_page, container, false);
    }

}
