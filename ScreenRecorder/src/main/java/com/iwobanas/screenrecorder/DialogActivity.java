package com.iwobanas.screenrecorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

public class DialogActivity extends Activity {
    public static final String MESSAGE_EXTRA = "MESSAGE_EXTRA";
    public static final String TITLE_EXTRA = "TITLE_EXTRA";
    public static final String RESTART_EXTRA = "RESTART_EXTRA";
    public static final String POSITIVE_EXTRA = "POSITIVE_EXTRA";
    public static final String RESTART_EXTRA_EXTRA = "RESTART_EXTRA_EXTRA";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.show(getFragmentManager(), "errorDialog");
    }

    static class DialogFragment extends android.app.DialogFragment {

        private boolean restart;

        private String extra;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Activity activity = getActivity();
            final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo);
            AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
            Intent intent = activity.getIntent();
            builder.setMessage(intent.getStringExtra(MESSAGE_EXTRA));
            builder.setTitle(intent.getStringExtra(TITLE_EXTRA));
            builder.setIcon(R.drawable.ic_launcher);
            String positiveLabel = intent.getStringExtra(POSITIVE_EXTRA);
            if (positiveLabel != null) {
                builder.setPositiveButton(positiveLabel, null);
            }
            restart = intent.getBooleanExtra(RESTART_EXTRA, false);
            extra = intent.getStringExtra(RESTART_EXTRA_EXTRA);
            return builder.create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            Activity activity = getActivity();
            if (restart) {
                Intent intent = new Intent(activity, RecorderService.class);
                if (extra != null) {
                    intent.putExtra(extra, true);
                }
                activity.startService(intent);
            }
            activity.finish();
        }
    }
}

