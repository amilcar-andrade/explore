package a2ndrade.explore.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class IntegrationCategory implements Parcelable {

    public final String id;
    public final String name;
    public final String description;
    public final List<Integration> integrations;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeTypedList(this.integrations);
    }

    protected IntegrationCategory(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.integrations = in.createTypedArrayList(Integration.CREATOR);
    }

    public static final Creator<IntegrationCategory> CREATOR = new Creator<IntegrationCategory>() {
        @Override
        public IntegrationCategory createFromParcel(Parcel source) {
            return new IntegrationCategory(source);
        }

        @Override
        public IntegrationCategory[] newArray(int size) {
            return new IntegrationCategory[size];
        }
    };
}
