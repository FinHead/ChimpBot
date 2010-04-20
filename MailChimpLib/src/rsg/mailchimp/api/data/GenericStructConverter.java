package rsg.mailchimp.api.data;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

/**
 * Adapter class implementing RPCStructConverter by default reflection, allowing classes to subclass this and 
 * ride for free.
 * @author ericmuntz
 *
 */
public abstract class GenericStructConverter implements RPCStructConverter {

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true);
	}

}
