package com.rsg.mailchimp;

public interface SignupResultCallback {

	public void signupSuccess();
	public void signupFailure(String message);
	
}
