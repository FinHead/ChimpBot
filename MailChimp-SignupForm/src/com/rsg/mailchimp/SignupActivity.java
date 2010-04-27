package com.rsg.mailchimp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignupActivity extends Activity implements OnClickListener {

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
		SignupDialog dialog = new SignupDialog(this);
		dialog.setOwnerActivity(this);
		dialog.show();	
	}
    
	
}
