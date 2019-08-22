package se.umu.chlu0125.inscriber.controllers;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import se.umu.chlu0125.inscriber.R;


/**
 * @author: Christoffer Lundstrom
 * @date: 20/08/2019
 * <p>
 * Description: Custom reset data confirmation dialog.
 */
public class ResetDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.reset_data).setTitle(R.string.reset_settings_question)
                .setPositiveButton(R.string.accept, (dialog, id) -> {
                    InscriptionService.clearUserData();
                    dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dismiss());
        return builder.create();
    }
}
