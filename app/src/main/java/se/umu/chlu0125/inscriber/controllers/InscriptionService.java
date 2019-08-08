package se.umu.chlu0125.inscriber.controllers;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import se.umu.chlu0125.inscriber.models.User;

/**
 * @author: Christoffer Lundstrom
 * @date: 03/08/2019
 * <p>
 * Description: A service which handles read, write operations on Cloud Firestore.
 */

public class InscriptionService {

    private static final String TAG = "InscriptionService";
    private static InscriptionService instance = null;
    private static FirebaseFirestore mFirebaseDbInstance;
    private static FirebaseInstanceId mInstanceId;
    private static String mIdToken;
    private static User mLocalUser;
    private DocumentReference mUserDocRef;

    public InscriptionService() {
        getInstanceId();
        getUserDataTask().addOnSuccessListener( (snapshot) -> {
            mLocalUser = snapshot.toObject(User.class);
        });
    }

    public static InscriptionService getInstance(){
        if(instance == null){
            instance = new InscriptionService();
        }
        return instance;
    }

    public Task<DocumentSnapshot> getUserDataTask() {
        mUserDocRef = mFirebaseDbInstance.collection("users").document(mIdToken);
        if (mUserDocRef == null){
            Log.e(TAG, "getUserDataTask: Error fetching Inscriptions. User does not exist.");
        }
        return mUserDocRef.get();
    }

    public void setUserData() {
        mFirebaseDbInstance.collection("users").document(mIdToken).set(mLocalUser)
                .addOnSuccessListener((success) -> {
                    Log.d(TAG, "setUserData: Success.");
                })
                .addOnFailureListener((fail) -> {
                    Log.e(TAG, "setUserData: Failed.");
                });
    }

    /**
     * Firebase Instance ID provides a unique identifier for each app instance and a mechanism to authenticate and authorize actions.
     * Resets with app reinstalls, clearing of app-data, restores etc. More info @ firebase api.
     * Used as unique id of user db-entry.
     */
    private void getInstanceId() {
        mFirebaseDbInstance = FirebaseFirestore.getInstance();
        if (mInstanceId == null) {
            Log.d(TAG, "getInstanceId: Fetching local instanceID.");
            mInstanceId = FirebaseInstanceId.getInstance();
            mIdToken = mInstanceId.getId();
        }
    }

    public User getLocalUser(){
        return mLocalUser;
    }
}