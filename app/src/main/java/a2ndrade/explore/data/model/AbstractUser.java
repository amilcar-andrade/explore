package a2ndrade.explore.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class that represents an abstract user.
 * The possible types are Organizations, User or Developer.
 */
public abstract class AbstractUser implements Parcelable {
    public final String login;
    public final String name;
    public final String avatar_url;

    @Override
    public int describeContents() {
        return 0;
    }

    /*package*/ AbstractUser(String login, String name, String avatar_url) {
        this.login = login;
        this.name = name;
        this.avatar_url = avatar_url;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.name);
        dest.writeString(this.avatar_url);
    }

    protected AbstractUser(Parcel in) {
        this.login = in.readString();
        this.name = in.readString();
        this.avatar_url = in.readString();
    }

}