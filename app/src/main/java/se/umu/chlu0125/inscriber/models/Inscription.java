package se.umu.chlu0125.inscriber.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Model for a inscription.
 */
public class Inscription implements Parcelable {

    private Timestamp mDate;
    private String mMessage;
    private Location mLocation;

    public Timestamp getDate() {
        return mDate;
    }

    public void setDate(Timestamp date) {
        mDate = date;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    protected Inscription(Parcel in) {
        mDate = (Timestamp)in.readParcelable(getClass().getClassLoader());
        mMessage = in.readString();
    }

    public Inscription(Location location){
        mDate = Timestamp.now();
        mLocation = location;
    }
    public Inscription(){
        mDate = Timestamp.now();
        mMessage = "Test";
    }

    public static final Creator<Inscription> CREATOR = new Creator<Inscription>() {
        @Override
        public Inscription createFromParcel(Parcel in) {
            return new Inscription(in);
        }

        @Override
        public Inscription[] newArray(int size) {
            return new Inscription[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mDate, 0);
        dest.writeString(mMessage);
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }
}
