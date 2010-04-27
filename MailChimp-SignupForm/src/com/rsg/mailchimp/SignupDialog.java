package com.rsg.mailchimp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.lists.ListMethods;
import rsg.mailchimp.api.lists.MergeFieldListUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignupDialog extends Dialog implements OnClickListener {


	public SignupDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog);
		this.setTitle(getContext().getText(R.string.mc_dialog_title));
		
		// register button callbacks
        Button subscribeButton = (Button) findViewById(R.id.SubscribeButton);
        subscribeButton.setOnClickListener(this);
        Button cancelButton = (Button) findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(this);
	}

	public void onClick(View clicked) {
    	Log.d(this.getClass().getName(), "Clicked: " + clicked.toString());
    	
    	switch (clicked.getId()) {
    	case R.id.CancelButton:
    		// clear the email field
    		EditText emailField = (EditText) findViewById(R.id.EMailField);
    		emailField.setText("");
    		this.cancel();
    		break;
    	case R.id.SubscribeButton:
        	// show progress dialog
    		final ProgressDialog progressDialog = ProgressDialog.show(getContext(), 
    				getContext().getResources().getText(R.string.mc_dialog_uploading_title), 
    				getContext().getResources().getText(R.string.mc_dialog_uploading_desc), true, false);// not cancelable, TODO: this dialog needs to be accessible elsewhere
    		
    		Runnable run = new Runnable() {
    			public void run() {
    	    		EditText text = (EditText) findViewById(R.id.EMailField);
    	    		if (text.getText() != null && text.getText().toString().trim().length() > 0) {
	    				addToList(text.getText().toString(), progressDialog);
    	    		}
    			}
    		};
    		(new Thread(run)).start();
    		
    		break;
    		default:
    			Log.e("MailChimp", "Unable to handle onClick for view " + clicked.toString());
    	}
	}
    
private void addToList(String emailAddy, final ProgressDialog progressDialog) {
    	
		MergeFieldListUtil mergeFields = new MergeFieldListUtil();
		mergeFields.addEmail(emailAddy);
		try {
			mergeFields.addDateField("BIRFDAY", (new SimpleDateFormat("MM/dd/yyyy")).parse("07/30/2007"));
		} catch (ParseException e1) {
			Log.e("MailChimp", "Couldn't parse date, boo: " + e1.getMessage());
		}
		mergeFields.addField("FNAME", "Ona");
		mergeFields.addField("LNAME", "StoutMuntz");
		
		ListMethods listMethods = new ListMethods(getContext().getResources().getText(com.rsg.mailchimp.R.string.mc_api_key));
		String message = "Signup successful!";
		try {
			listMethods.listSubscribe(getContext().getText(R.string.mc_list_id).toString(), emailAddy, mergeFields, null, true, true, false, true);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception subscribing person: " + e.getMessage());
			message = "Signup failed: " + e.getMessage();
		} finally {
			progressDialog.dismiss();
			this.dismiss();			
			showResult(message);
		}
		
    }

	private void showResult(final String message) {
		Runnable run = new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setMessage(message).setPositiveButton("OK", new Dialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		};
		getOwnerActivity().runOnUiThread(run);
	}
}
