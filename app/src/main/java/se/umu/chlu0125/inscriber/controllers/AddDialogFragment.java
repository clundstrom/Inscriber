package se.umu.chlu0125.inscriber.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
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

/**
 * @author: Christoffer Lundstrom
 * @date: 04/08/2019
 * <p>
 * Description: Handles the Add inscription Dialog.
 */
public class AddDialogFragment extends DialogFragment {

    private static final String TAG = "AddDialogFragment";
    private static final int MIN_MSG_LENGTH = 5;
    private static final int MAX_MSG_LENGTH = 140;
    private TextView mMaxChars;
    private EditText mMessage;
    private Button mCancel;
    private Button mAdd;


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
        Log.d(TAG, "onCreateDialog: Createing dialog.");

        // Finding Views.
        mCancel = v.findViewById(R.id.add_ins_cancel);
        mAdd = v.findViewById(R.id.add_ins_add);
        mMaxChars = (TextView)v.findViewById(R.id.add_ins_max_char);
        mMessage = (EditText)v.findViewById(R.id.add_ins_msg);

        attachListeners();
        return new AlertDialog.Builder(getActivity()).setView(v).create();
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
                Inscription ins = new Inscription();
                ins.setMessage(mMessage.getText().toString());
                Toast successToast = Toast.makeText(getContext(), R.string.add_ins_success, Toast.LENGTH_SHORT);
                successToast.setGravity(Gravity.TOP, 0, 50);
                successToast.show();
                Log.d(TAG, "attachListeners: New Inscription created.");
                dismiss();
            }
        });



    }

}
