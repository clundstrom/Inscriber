package se.umu.chlu0125.inscriber.controllers;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * @author: Christoffer Lundstrom
 * @date: 03/08/2019
 * <p>
 * Description: A service which handles read, write operations on Cloud Firestore.
 */

public class InscriptionService {

    private static final String TAG = "InscriptionService";
    private FirebaseFirestore mFirebaseDbInstance;
    private FirebaseInstanceId mInstanceId;
    private String mIdToken;


    public InscriptionService() {
        getInstanceId();
    }


    public Task<DocumentSnapshot> getUserDataTask(){
        DocumentReference userDoc = mFirebaseDbInstance.collection("users").document(mIdToken);
        if(userDoc == null) Log.e(TAG, "getUserDataTask: Error fetching Inscriptions. User does not exist.");
        return userDoc.get();
    }

    public void setUserData(Object object){
        mFirebaseDbInstance.collection("users").document(mIdToken).set(object)
                .addOnSuccessListener((success) -> {
                    Log.d(TAG, "setUserData: Success.");
                })
                .addOnFailureListener((fail) -> {
                    Log.e(TAG, "setUserData: Failed.");
                });
    }


    /**
     *  Firebase Instance ID provides a unique identifier for each app instance and a mechanism to authenticate and authorize actions.
     *  Resets with app reinstalls, clearing of app-data, restores etc. More info @ firebase api.
     *  Used as unique id of user db-entry.
     */
    private void getInstanceId(){
        mFirebaseDbInstance = FirebaseFirestore.getInstance();
        if (mInstanceId == null){
            Log.d(TAG, "getInstanceId: Fetching local instanceID.");
            mInstanceId = FirebaseInstanceId.getInstance();
            mIdToken = mInstanceId.getId();
        }
    }
}