package rsg.mailchimp.api.data;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

/**
 * Object representing the chimp chatter data
 * @author ericmuntz
 *
 */
public class ChimpChatter implements RPCStructConverter {

	public String message;
	public Type type;
	public String listId;
	public String campaignId;
	public String updateTime;
	
	public enum Type {
		scheduled, sent, inspection, subscribes, unsubscribes, low_credits, absplit, best_opens, best_clicks, abuse
	}
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct)
			throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true, "type");
		
		String typeVal = (String) struct.get("type");
		type = Type.valueOf(typeVal);
	}
	
}
