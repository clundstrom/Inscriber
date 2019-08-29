package se.umu.chlu0125.inscriber.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Christoffer Lundstrom
 * @date: 03/08/2019
 * <p>
 * Description: Model for a Collection of Inscription.
 */
@Keep
public class User implements Parcelable {

    private static final String TAG = "User";
    private List<Inscription> mCollection;

    public User(){
        mCollection = new ArrayList<Inscription>();
    }

    public List<Inscription> getCollection() {
        return mCollection;
    }
    public void setCollection(List<Inscription> collection) {
        mCollection = collection;
    }

    protected User(Parcel in) {
        mCollection = in.createTypedArrayList(Inscription.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mCollection);
    }
}