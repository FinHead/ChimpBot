package rsg.mailchimp.api.campaigns;

import java.util.Date;
import java.util.List;
import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

/**
 * Object representing the EepUrlStats from the API
 * @author ericmuntz
 *
 */
public class EepUrlStats implements RPCStructConverter {

	/** This is always "Twitter" right now, not sure why it's in the API this way, but it is, so here it is*/
	public String service;
	
	public Integer tweets;
	public Date firstTweet;
	public Date lastTweet;
	public Integer retweets;
	public Date firstRetweet;
	public Date lastRetweet;
	public List<Status> statuses;
	
	public static class Status implements RPCStructConverter {

		public Status() {}
		
		public String status;
		public String screenName;
		public String statusId;
		public Boolean isRetweet;
		public Date datetime;
		
		@SuppressWarnings("unchecked")
		public void populateFromRPCStruct(String key, Map struct)
				throws MailChimpApiException {
			Utils.populateObjectFromRPCStruct(this, struct, true);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		service = key;
		Utils.populateObjectFromRPCStruct(this, struct, true, "statuses");
		Object obj = struct.get("statuses");
		if (obj instanceof Object[]) {
			statuses = Utils.extractObjectsFromList(EepUrlStats.Status.class, (Object[])obj);
		}
	}

}
