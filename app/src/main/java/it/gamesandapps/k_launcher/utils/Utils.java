package it.gamesandapps.k_launcher.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import it.gamesandapps.k_launcher.objects.AppObj;


public class Utils {

    public final static int VIEW_GRID = 1;
    public final static int VIEW_LIST = 2;
    public final static int GRID_3x3 = 9;
    public final static int GRID_3x4 = 12;
    public final static int GRID_4x4 = 16;
    public final static int GRID_4x5 = 20;
    public final static int GRID_5x5 = 25;

    public static void OpenApp(Context ctx, AppObj app) {
        Intent i = ctx.getPackageManager().getLaunchIntentForPackage(app.getPkg());
        ctx.startActivity(i);
    }

    public static void UnistallApp(Context ctx, String pkg){
        Uri packageURI = Uri.parse("package:" + pkg);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        ctx.startActivity(uninstallIntent);
    }
}
