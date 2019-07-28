package se.umu.chlu0125.inscriber.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import se.umu.chlu0125.inscriber.R;

/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description: Controller for a dialog which suggests a short walkthrough to the user.
 */
public class GuideDialogFragment extends DialogFragment {


    private static final String TAG = "GuideDialogFragment";
    private Button mHideButton;
    private Button mShowButton;


    public static GuideDialogFragment newInstance(){
        GuideDialogFragment fragment = new GuideDialogFragment();

        Bundle args = new Bundle();
        //args.putInt("someInt", someInt);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private void attachListeners() {
        mHideButton.setOnClickListener( (x) -> {
            Log.d("Inscriber", "Hide fragment.");
            closeFragment();
        });

        mShowButton.setOnClickListener( (x) -> {
            Log.d("Inscriber", "Show guide.");
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.modal_help, container, false);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        mHideButton = getView().findViewById(R.id.button_hide);
        mShowButton = getView().findViewById(R.id.button_show);

        attachListeners();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.modal_help, null);
        Log.d(TAG, "onCreateDialog: Starting dialog.");
        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }

    private void closeFragment() {
        Log.d(TAG, "closeFragment: Closing dialog.");
        getFragmentManager().beginTransaction().remove(this).commit();
    }

}
