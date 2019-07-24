package se.umu.chlu0125.inscriber.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description:
 */
public class Inscription implements Parcelable {


    private Date mDate;
    private String mMessage;

    protected Inscription(Parcel in) {
        mDate = (Date)in.readSerializable();
        mMessage = in.readString();
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
        dest.writeSerializable(mDate);
        dest.writeString(mMessage);
    }

}
