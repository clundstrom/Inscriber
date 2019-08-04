package se.umu.chlu0125.inscriber.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author: Christoffer Lundstrom
 * @date: 03/08/2019
 * <p>
 * Description:
 */

public class InscriptionCollection implements Parcelable {

    private static final String TAG = "InscriptionCollection";
    private List<Inscription> mCollection;



    protected InscriptionCollection(Parcel in) {
        mCollection = in.createTypedArrayList(Inscription.CREATOR);
    }

    public static final Creator<InscriptionCollection> CREATOR = new Creator<InscriptionCollection>() {
        @Override
        public InscriptionCollection createFromParcel(Parcel in) {
            return new InscriptionCollection(in);
        }

        @Override
        public InscriptionCollection[] newArray(int size) {
            return new InscriptionCollection[size];
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