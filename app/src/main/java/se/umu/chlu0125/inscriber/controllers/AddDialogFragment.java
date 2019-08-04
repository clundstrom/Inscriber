package se.umu.chlu0125.inscriber.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import se.umu.chlu0125.inscriber.R;

public class AddDialogFragment extends DialogFragment {

    private static final String TAG = "AddDialogFragment";
    private final int MAX_CHARS = 140;
    private TextView mMaxChars;
    private EditText mMessage;


    public static AddDialogFragment newInstance(){
        AddDialogFragment fragment = new AddDialogFragment();

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


    /**
     * Creates the Add Inscription Dialog in which the User adds a message to the current location.
     * Also handles maximum amount of characters.
     * @param savedInstanceState
     * @return Dialog Popup.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.add_ins_dialog, null);
        Log.d(TAG, "onCreateDialog: Starting dialog.");

        mMaxChars = (TextView)v.findViewById(R.id.add_ins_max_char);
        mMessage = (EditText)v.findViewById(R.id.add_ins_msg);
        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mMaxChars.setText("Characters left: " + (MAX_CHARS-s.length()));
            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }
}
