package rsg.mailchimp.api.campaigns;

import java.util.Date;

import rsg.mailchimp.api.data.GenericStructConverter;

/**
 * Typed class for the Abuse Report that comes back from the API
 * @author ericmuntz
 *
 */
public class AbuseReport extends GenericStructConverter {

	public Date date;
	public String email;
	public String type;
	public String campaignId;
	
}
