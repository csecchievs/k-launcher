package it.gamesandapps.k_launcher.objects;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class AppObj implements Parcelable {

    private String name;
    private String pkg;
    private Drawable icon;

    public AppObj(String name, String pkg, Drawable icon) {
        this.name = name;
        this.pkg = pkg;
        this.icon = icon;
    }

    protected AppObj(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName());
        parcel.writeString(getPkg());
    }

    public static final Creator<AppObj> CREATOR = new Creator<AppObj>() {
        @Override
        public AppObj createFromParcel(Parcel in) {
            return new AppObj(in);
        }

        @Override
        public AppObj[] newArray(int size) {
            return new AppObj[size];
        }
    };
}
