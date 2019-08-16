package se.umu.chlu0125.inscriber.controllers;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import se.umu.chlu0125.inscriber.R;
import se.umu.chlu0125.inscriber.models.Inscription;
import se.umu.chlu0125.inscriber.models.Location;

/**
 * @author: Christoffer Lundstrom
 * @date: 04/08/2019
 * <p>
 * Description: Handles the Add inscription Dialog.
 */
public class AddDialogFragment extends DialogFragment {

    private static final String TAG = "AddDialogFragment";
    private static final int MIN_MSG_LENGTH = 1;
    private static final int MAX_MSG_LENGTH = 140;
    public static final String EXTRA_MARKER = "se.umu.chlu0125.inscriber.models.Inscription";
    private static Inscription mInscription;
    private TextView mMaxChars;
    private TextView mLocation;
    private EditText mMessage;
    private Button mCancel;
    private Button mAdd;
    private boolean mAddMarker;


    public static AddDialogFragment newInstance(@Nullable Location location){
        AddDialogFragment fragment = new AddDialogFragment();
        mInscription = new Inscription(location);
        Bundle args = new Bundle();
        //args.putInt("someInt", someInt);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_ins_dialog, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mLocation.setText("Lat: " + mInscription.getLocation().getLatitude()  + "\nLong: " + mInscription.getLocation().getLongitude());
    }

    /**
     * Creates the Add Inscription Dialog in which the User adds a message to the current location.
     * Also handles maximum amount of characters.
     * @param savedInstanceState
     * @return Dialog Popup.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.add_ins_dialog, null);
        Log.d(TAG, "onCreateDialog: Creating dialog.");

        // Finding Views.
        mCancel = v.findViewById(R.id.add_ins_cancel);
        mAdd = v.findViewById(R.id.add_ins_add);
        mMaxChars = (TextView)v.findViewById(R.id.add_ins_max_char);
        mMessage = (EditText)v.findViewById(R.id.add_ins_msg);
        mLocation = v.findViewById(R.id.add_ins_location);

        attachListeners();
        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }

    private void sendResult(int resultCode, Inscription mInscription){
        if (getTargetFragment() == null) return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_MARKER, mInscription);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }

    private void attachListeners() {
        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mMaxChars.setText("Characters left: " + (MAX_MSG_LENGTH -s.length()));
            }
        });

        mCancel.setOnClickListener((click) -> dismiss());

        mAdd.setOnClickListener((click) -> {
            if(mMessage.getText().toString().length() < MIN_MSG_LENGTH){
               Toast errToast =  Toast.makeText(getContext(), R.string.add_ins_err_message_empty, Toast.LENGTH_SHORT);
               errToast.setGravity(Gravity.TOP, 0, 50);
               errToast.show();
                Log.e(TAG, "attachListeners: Message empty.");
            }
            else {
                mInscription.setMessage(mMessage.getText().toString());
                Toast successToast = Toast.makeText(getContext(), R.string.add_ins_success, Toast.LENGTH_SHORT);
                successToast.setGravity(Gravity.TOP, 0, 50);
                successToast.show();
                Log.d(TAG, "attachListeners: New Inscription created.");
                sendResult(Activity.RESULT_OK, mInscription);
                dismiss();
            }
        });
    }

}
