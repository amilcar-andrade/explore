package a2ndrade.explore.data.model;

import android.os.Parcel;

public class Developer extends AbstractUser {
    public final String description;
    public final String repoName;

    public Developer(String login, String name, String avatarUrl, String repoName, String description) {
        super(login, name, avatarUrl);
        this.description = description;
        this.repoName = repoName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.description);
        dest.writeString(this.repoName);
    }

    protected Developer(Parcel in) {
        super(in);
        this.description = in.readString();
        this.repoName = in.readString();
    }

    public static final Creator<Developer> CREATOR = new Creator<Developer>() {
        @Override
        public Developer createFromParcel(Parcel source) {
            return new Developer(source);
        }

        @Override
        public Developer[] newArray(int size) {
            return new Developer[size];
        }
    };
}