package rsg.mailchimp.api.campaigns;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

/**
 * Object representing the geo opens call result
 * @author ericmuntz
 *
 */
public class GeoOpens implements RPCStructConverter {
	
	public String code;
	public String name;
	public Integer opens;
	public Boolean regionDetail;

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true);
	}

}
