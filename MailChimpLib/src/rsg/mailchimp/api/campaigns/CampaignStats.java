package rsg.mailchimp.api.campaigns;

import java.util.Date;
import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

/**
 * Object representing the stats of a campaign as returned by the campaignStats function
 * @author ericmuntz
 *
 */
public class CampaignStats implements RPCStructConverter {

	public Integer syntaxErrors;
	public Integer hardBounces;
	public Integer softBounces;
	public Integer unsubscribes;
	public Integer abuseReports;
	public Integer forwards;
	public Integer forwardsOpens;
	public Integer opens;
	public Date lastOpen;
	public Integer uniqueOpens;
	public Integer clicks;
	public Integer uniqueClicks;
	public Date lastClick;
	public Integer usersWhoClicked;
	public Integer emailsSent;
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true);
	}

}
