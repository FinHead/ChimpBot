package rsg.mailchimp.api;

public class MailChimpApiException extends Exception {

	private static final long serialVersionUID = -2808865565343231104L;

	public MailChimpApiException() {
		super();
		
	}

	public MailChimpApiException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		
	}

	public MailChimpApiException(String detailMessage) {
		super(detailMessage);
		
	}

	public MailChimpApiException(Throwable throwable) {
		super(throwable);
		
	}

	
	
}
