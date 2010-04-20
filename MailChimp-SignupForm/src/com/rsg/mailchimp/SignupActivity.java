package com.rsg.mailchimp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignupActivity extends Activity implements OnClickListener, SignupResultCallback {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // attach handler to the subscribe button
        Button button = (Button) this.findViewById(R.id.ShowDialogButton);
        button.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		SignupDialog dialog = new SignupDialog(this, this);
		dialog.setOwnerActivity(this);
		dialog.show();	
	}

	@Override
	public void signupFailure(final String message) {
		final Context ctx = this;
		Runnable run = new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
				builder.setMessage("Signup failed: " + message).setPositiveButton("OK", new Dialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		};
		this.runOnUiThread(run);
	}

	@Override
	public void signupSuccess() {
		final Context ctx = this;
		Runnable run = new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
				builder.setMessage("Signup successful!").setPositiveButton("OK", new Dialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		};
		this.runOnUiThread(run);
	}
    
	
}
