package rsg.mailchimp.api.campaigns;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;

public class CampaignAdvice implements RPCStructConverter {
	
	public String message;
	public String type;

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		this.message = (String) struct.get("msg");
		this.type = (String) struct.get("type");
	}

}
