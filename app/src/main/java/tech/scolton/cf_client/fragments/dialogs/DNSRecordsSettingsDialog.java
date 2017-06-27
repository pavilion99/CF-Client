package tech.scolton.cf_client.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import lombok.Setter;
import tech.scolton.cf_client.fragments.DNSFragment;

public class DNSRecordsSettingsDialog extends DialogFragment {

    @Setter
    private DNSFragment.DNSRecord record;

    public DNSRecordsSettingsDialog() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (this.record == null) {
            Log.e("CF_APP", "DNSRecord should have been set in Dialog before calling onCreateDialog!");
            return new Dialog(getContext());
        }


        return null;
    }
}
