package a2ndrade.explore.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Integration implements Parcelable{

    public final String name;
    public final String description;
    public final String avatar_url;
    public final String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.avatar_url);
        dest.writeString(this.url);
    }

    protected Integration(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.avatar_url = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Integration> CREATOR = new Creator<Integration>() {
        @Override
        public Integration createFromParcel(Parcel source) {
            return new Integration(source);
        }

        @Override
        public Integration[] newArray(int size) {
            return new Integration[size];
        }
    };
}
