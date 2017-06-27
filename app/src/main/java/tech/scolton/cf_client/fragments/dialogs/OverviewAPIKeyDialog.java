package tech.scolton.cf_client.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tech.scolton.cf_client.R;
import tech.scolton.cf_client.storage.Storage;

public class OverviewAPIKeyDialog extends DialogFragment {

    private TextView APIKey;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogRoot = inflater.inflate(R.layout.dialog_api_key, null);
        builder.setView(dialogRoot);
        builder.setTitle("Your API Key");

        this.APIKey = (TextView) dialogRoot.findViewById(R.id.dialog_api_key_key);
        this.APIKey.setText(Storage.storage.getString("api-key"));
        this.APIKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyAPIKeyToClipboard();
            }
        });

        return builder.create();
    }

    private void copyAPIKeyToClipboard() {
        String APIKeyText = this.APIKey.getText().toString();

        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("CF API Key", APIKeyText));

        Toast.makeText(getContext(), R.string.api_key_copied, Toast.LENGTH_LONG).show();
    }

}
