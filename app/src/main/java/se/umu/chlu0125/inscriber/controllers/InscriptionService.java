package se.umu.chlu0125.inscriber.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import se.umu.chlu0125.inscriber.models.User;

import static android.content.Context.MODE_PRIVATE;

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
    private static User mUser;
    private DocumentReference mUserDocRef;

    public InscriptionService() {
        getInstanceId();
        getUserDataTask().addOnSuccessListener((snapshot) -> {
            mUser = snapshot.toObject(User.class);
        });
    }

    public static InscriptionService getInstance() {
        if (instance == null) {
            instance = new InscriptionService();
        }
        return instance;
    }


    /**
     * Fetches User data from local and global database. Prioritizes Global data.
     *
     * @param context
     * @return
     */
    public User getUserData(Context context) {
        mUser = getLocalUserData(context);
        getUserDataTask().addOnSuccessListener((snapshot -> {

            mUser = snapshot.toObject(User.class);
            Log.d(TAG, "getUserData: Global user found.");
        }));

        if (mUser == null) {
            mUser = new User(); // no user found
        }
        return mUser;
    }

    /**
     * @return Returns an async task which provides a snapshot of the current User document. Must be awaited by the caller.
     */
    public Task<DocumentSnapshot> getUserDataTask() {
        mUserDocRef = mFirebaseDbInstance.collection("users").document(mIdToken);
        if (mUserDocRef == null) {
            Log.e(TAG, "getUserDataTask: Error fetching Inscriptions. User does not exist.");
        }
        return mUserDocRef.get();
    }

    /**
     * Saves the current instance of Local User to Cloud Firestore.
     */
    public void setDbUserData() {
        mFirebaseDbInstance.collection("users").document(mIdToken).set(mUser)
                .addOnSuccessListener((success) -> {
                    Log.d(TAG, "setDbUserData: Success.");
                })
                .addOnFailureListener((fail) -> {
                    Log.e(TAG, "setDbUserData: Failed.");
                });
    }


    /**
     * Saves the currently stored Local User.
     *
     * @param context
     */

    public void setLocalUserData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("se.umu.chlu0125.inscriber", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("User", json);
        prefsEditor.commit();
        Log.d(TAG, "setLocalUserData: Local User Saved.");
    }

    /**
     * @param context
     * @return Returns currently stored Local User.
     */
    public User getLocalUserData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("se.umu.chlu0125.inscriber", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = mPrefs.getString("User", "");
        User user = gson.fromJson(json, User.class);
        Log.d(TAG, "getLocalUserData: Local user found.");
        return user;
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
}