package rsg.mailchimp.api.campaigns;

import java.util.Date;
import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

/**
 * See <a href="http://www.mailchimp.com/api/rtfm/campaignbouncemessages.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignbouncemessages.func.php</a>
 * <br/>
 * Representation of the BounceMessage data
 * @author ericmuntz
 */
public class BounceMessage implements RPCStructConverter {

	public Date date;
	public String email;
	public String message;
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true);
	}

}
