package tech.scolton.cf_client.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tech.scolton.cf_client.R;
import tech.scolton.cf_client.storage.Storage;

public class CopyrightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyright);
    }


    public void accept(View view) {
        respond(true);
    }

    public void decline(View view) {
        respond(false);
    }

    private void respond(boolean accept) {
        Storage.storage.saveBoolean("copyright-accepted", accept);

        if (accept) {
            finish();
        } else {
            moveTaskToBack(true);
        }
    }
}
