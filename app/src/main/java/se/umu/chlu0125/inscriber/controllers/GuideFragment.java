package se.umu.chlu0125.inscriber.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.umu.chlu0125.inscriber.R;

/**
 * @author: Christoffer Lundstrom
 * @date: 22/07/2019
 * <p>
 * Description:
 */
public class GuideFragment extends DialogFragment {


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.modal_help, container, false);


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.modal_help, null);

        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }

}
