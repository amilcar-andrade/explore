package a2ndrade.explore.data.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

public class Repo implements Parcelable {
    public static final int DEFAULT_COLOR = Color.DKGRAY;

    public final String name;
    public final String description;
    private boolean isStarred;
    // Avatar color
    private int color = DEFAULT_COLOR;

    public Repo(String name, String description, boolean isStarred) {
        this.name = name;
        this.description = description;
        this.isStarred = isStarred;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean isStarred) {
        this.isStarred = isStarred;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeByte(this.isStarred ? (byte) 1 : (byte) 0);
    }

    protected Repo(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.isStarred = in.readByte() != 0;
    }

    public static final Creator<Repo> CREATOR = new Creator<Repo>() {
        @Override
        public Repo createFromParcel(Parcel source) {
            return new Repo(source);
        }

        @Override
        public Repo[] newArray(int size) {
            return new Repo[size];
        }
    };
}


