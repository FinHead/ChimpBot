package rsg.mailchimp.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import rsg.mailchimp.R;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.lists.ListMethods;
import rsg.mailchimp.api.lists.MergeFieldListUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Subscribe extends Activity implements OnClickListener {

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // register button callbacks
        Button subscribeButton = (Button) findViewById(R.id.SubscribeButton);
        subscribeButton.setOnClickListener(this);
        Button cancelButton = (Button) findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(this);
        
    }
    
    public void onClick(View clicked) {
//    	Log.d(this.getClass().getName(), "Clicked: " + clicked.toString());
    	
    	switch (clicked.getId()) {
    	case R.id.CancelButton:
    		// clear the email field
    		EditText emailField = (EditText) findViewById(R.id.EMailField);
    		emailField.setText("");
    		break;
    	case R.id.SubscribeButton:
        	// show progress dialog
    		final ProgressDialog dialog = ProgressDialog.show(this, 
    				getResources().getText(R.string.mc_dialog_uploading_title), 
    				getResources().getText(R.string.mc_dialog_uploading_desc), true, false);// not cancelable, TODO: this dialog needs to be accessible elsewhere
    		
    		// jump off to communicate with the MC API
    		// http://<dc>.api.mailchimp.com/1.2/
    		Runnable run = new Runnable() {
    			public void run() {
    	    		EditText text = (EditText) findViewById(R.id.EMailField);
    	    		if (text.getText() != null && text.getText().toString().trim().length() > 0) {
	    				addToList(text.getText().toString(), dialog);
    	    		}
    			}
    		};
    		(new Thread(run)).start();
    		
    		break;
    		default:
//    			Log.e("MailChimp", "Unable to handle onClick for view " + clicked.toString());
    	}
	}
    
    private void addToList(String emailAddy, final ProgressDialog dialog) {
    	
		MergeFieldListUtil mergeFields = new MergeFieldListUtil();
		mergeFields.addEmail(emailAddy);
		try {
			mergeFields.addDateField("BIRFDAY", (new SimpleDateFormat("MM/dd/yyyy")).parse("07/30/2007"));
		} catch (ParseException e1) {
//			Log.e("MailChimp", "Couldn't parse date, boo: " + e1.getMessage());
		}
		mergeFields.addField("FNAME", "Ona");
		mergeFields.addField("LNAME", "StoutMuntz");
		
		ListMethods listMethods = new ListMethods(getResources().getText(R.string.mc_api_key));
		try {
			listMethods.listSubscribe(getText(R.string.mc_list_id).toString(), emailAddy, mergeFields);
		} catch (MailChimpApiException e) {
//			Log.e("MailChimp", "Exception subscribing person: " + e.getMessage());
		}
		dialog.cancel();    	
    }
	
}