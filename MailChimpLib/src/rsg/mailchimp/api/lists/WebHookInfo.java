package rsg.mailchimp.api.lists;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;

/**
 * Info about a webhook
 * <br/><a href="http://www.mailchimp.com/api/rtfm/listwebhookds.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listwebhookds.func.php</a>
 * @author ericmuntz
 */
public class WebHookInfo implements RPCStructConverter {

	public String url;
	public Map<String, Boolean> actions;
	public Map<String, Boolean> sources;
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		url = (String) struct.get("url");
		actions = (Map<String, Boolean>) struct.get("actions");
		sources = (Map<String, Boolean>) struct.get("sources");
	}

}
