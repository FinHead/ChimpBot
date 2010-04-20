package rsg.mailchimp.api.data;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;

/**
 * Simple utility class for representing a Folder as understood by the API
 * @author ericmuntz
 *
 */
public class FolderInfo implements RPCStructConverter {

	public Integer folderId;
	public String name;

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		this.folderId = ((Number) struct.get("folder_id")).intValue();
		this.name = (String) struct.get("name");
	}
}
