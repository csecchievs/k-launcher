package it.gamesandapps.k_launcher.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import it.gamesandapps.k_launcher.objects.AppObj;


public class Utils {

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
