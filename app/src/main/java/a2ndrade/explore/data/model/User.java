package a2ndrade.explore.data.model;


import android.os.Parcel;

/**
 * Model class representing data returned from GitHub
 */
public class User extends AbstractUser {
    public final String followers_url;
    public final String organizations_url;
    public final String type;
    public final String company;
    public final String blog;
    public final String location;
    public final String email;
    public final String bio;
    public final int followers;
    public final int following;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.followers_url);
        dest.writeString(this.organizations_url);
        dest.writeString(this.type);
        dest.writeString(this.company);
        dest.writeString(this.blog);
        dest.writeString(this.location);
        dest.writeString(this.email);
        dest.writeString(this.bio);
        dest.writeInt(this.followers);
        dest.writeInt(this.following);
    }

    protected User(Parcel in) {
        super(in);
        this.followers_url = in.readString();
        this.organizations_url = in.readString();
        this.type = in.readString();
        this.company = in.readString();
        this.blog = in.readString();
        this.location = in.readString();
        this.email = in.readString();
        this.bio = in.readString();
        this.followers = in.readInt();
        this.following = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
