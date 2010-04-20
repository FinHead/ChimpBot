package rsg.mailchimp.api.lists;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class MemberInfo implements RPCStructConverter {
	
	public String id;
	public String email;
	public Constants.EmailType emailType;
	public MergeFieldListUtil mergeFields;
	public Constants.SubscribeStatus status;
	public String ipOpt;
	public String ipSignup;
	public Integer memberRating;
	
	/** The specific campaignId the user unsubscribed from, if they unsusbscribed from a campaign email*/
	public String campaignId;
	
	public MailChimpListStatus[] lists;
	
	/** The time this email address was added to the list */
	public Date timestamp;
	

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true, "emailType", "status", "lists", "merges");
		
		// handle emailType enum
		Object rpcObj = struct.get("email_type");
		if (rpcObj != null) {
			emailType = Constants.EmailType.valueOf((String) rpcObj);
		}
		
		// handle subscribe status enum
		rpcObj = struct.get("status");
		if (rpcObj != null) {
			status = Constants.SubscribeStatus.valueOf((String) rpcObj);
		}
		
		// handle merge fields
		rpcObj = struct.get("merges");
		mergeFields = new MergeFieldListUtil();
		if (rpcObj instanceof Map) {			
			mergeFields.putAll((Map<Object, Object>) struct.get("merges"));
		}
		
		// handle lists
		rpcObj = struct.get("lists");
		if (rpcObj instanceof Map) {
			Set<Map.Entry<String, Object>> entries = ((Map) rpcObj).entrySet();
			lists = new MailChimpListStatus[entries.size()];
			int i = 0;
			for (Map.Entry<String, Object> entry : entries) {
				lists[i] = new MailChimpListStatus();
				lists[i].listId = entry.getKey();
				lists[i].status = Constants.SubscribeStatus.valueOf(entry.getValue().toString());
				i++;
			}
		}
	}

}
