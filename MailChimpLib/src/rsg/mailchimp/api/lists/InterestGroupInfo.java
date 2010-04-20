package rsg.mailchimp.api.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;

/**
 * Represents interest group information as returned by listInterestGroups
 * @author ericmuntz
 *
 */
public class InterestGroupInfo implements RPCStructConverter {

	public String name;
	public Constants.InterestGroupType type;
	public List<String> groups;
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		name = (String) struct.get("name");
		
		Object obj = struct.get("form_field");
		if (obj != null) type = Constants.InterestGroupType.valueOf((String) obj);
		
		obj = struct.get("groups");
		if (obj != null && obj instanceof Object[]) {
			Object[] values = (Object[]) obj;
			groups = new ArrayList<String> (values.length);
			for (Object o : values) {
				groups.add((String) o);
			}
		}
	}
	
	
}
