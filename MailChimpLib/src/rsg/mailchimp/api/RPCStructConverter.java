package rsg.mailchimp.api;

import java.util.Map;

public interface RPCStructConverter {

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException;
	
}
