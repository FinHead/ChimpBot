package rsg.mailchimp.api.campaigns;

import java.util.Map;

import rsg.mailchimp.api.RPCStructConverter;

/**
 * Structure for the TemplateDetails as returned by the API
 * <br/><a href="http://www.mailchimp.com/api/rtfm/campaigntemplates.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigntemplates.func.php</a>
 * @author ericmuntz
 */
public class TemplateDetails implements RPCStructConverter {

	public Integer id;
	public String name;
	public String layout;
	public String previewImageUrl;
	@SuppressWarnings("unchecked") public Map sections;
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) {
		this.id = ((Number) struct.get("id")).intValue();
		this.name = (String) struct.get("name");
		this.layout = (String) struct.get("layout");
		this.previewImageUrl = (String) struct.get("preview_image");
		
		// set sections, if they aren't empty
		Object sectionsVal = struct.get("sections");
		if (sectionsVal instanceof Map) {					
			this.sections = (Map) struct.get("sections");
		}
	}
	
}
