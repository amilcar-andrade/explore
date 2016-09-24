package a2ndrade.explore.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Developer implements Parcelable {
    public final String id;
    public final String developerUrl;
    public final String description;
    public final String name;
    public final String repoName;

    public Developer(String id, String name, String developerUrl, String repoName, String description) {
        this.id = id;
        this.developerUrl = developerUrl;
        this.description = description;
        this.name = name;
        this.repoName = repoName;
    }

    protected Developer(Parcel in) {
        id = in.readString();
        developerUrl = in.readString();
        description = in.readString();
        name = in.readString();
        repoName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(developerUrl);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(repoName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Developer> CREATOR = new Creator<Developer>() {
        @Override
        public Developer createFromParcel(Parcel in) {
            return new Developer(in);
        }

        @Override
        public Developer[] newArray(int size) {
            return new Developer[size];
        }
    };
}
