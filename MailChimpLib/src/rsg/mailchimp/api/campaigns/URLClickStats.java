package rsg.mailchimp.api.campaigns;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

/**
 * Object representing the URL Click Stat from the API.
 * @author ericmuntz
 *
 */
public class URLClickStats implements RPCStructConverter {

	public String url;
	public Integer clicks;
	public Integer unique;
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		url = key;
		Utils.populateObjectFromRPCStruct(this, struct, true);
	}

}
