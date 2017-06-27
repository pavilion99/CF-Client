package tech.scolton.cf_client.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import tech.scolton.cf_client.R;
import tech.scolton.cf_client.fragments.OverviewFragment;

public class OverviewQuickActionsDialogFragment extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.overview_quick_actions).setItems(R.array.overview_quick_actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((OverviewFragment)getTargetFragment()).onClickQuickAction(which);
            }
        });
        return builder.create();
    }

}
