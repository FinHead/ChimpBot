package rsg.mailchimp.api.campaigns;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

import org.xmlrpc.android.XMLRPCSerializable;


public class CampaignOptions implements XMLRPCSerializable {

	public String listId;
	public String subject;
	public String fromEmail;
	public String fromName;
	public String toEmail;
	public Integer templateId;
	public Integer folderId;
	public TrackingOptions[] tracking;
	public String title;
	public Boolean authenticate;
	public Map<String, String> analytics;
	public Boolean autoFooter;
	public Boolean inlineCss;
	public Boolean generateText;
	public Boolean autoTweet;
	
	public enum TrackingOptions {
		opens, html_clicks, text_clicks
	}
	
	public Object getSerializable() {
		Hashtable<String, Object> map = new Hashtable<String, Object>();
		
		// validate
		if (listId == null) throw new IllegalArgumentException("listId required for campaign options");
		if (subject == null) throw new IllegalArgumentException("subject required for campaign options");
		if (fromEmail == null) throw new IllegalArgumentException("fromEmail required for campaign options");
		if (fromName == null) throw new IllegalArgumentException("fromName required for campaign options");
		if (toEmail == null) throw new IllegalArgumentException("toEmail required for campaign options");
		
		// use reflection to put all the fields into the map for the struct representing the options
		try {
			Field[] fields = this.getClass().getFields();
			for (Field f : fields) {
				if (f.get(this) != null) {					
					map.put(convertVarNameToApiName(f.getName()), f.get(this));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error occurred serializing the CampaignOptions: " + e.getMessage());
		}
		
		return map;
	}

	/**
	 * Yes, I'm insane, but I like to follow java coding standards and I can't put an underscore in a Java var.  insert donkey sound here.
	 * @param name
	 * @return the lowercased underscore version of the string, since all vars are named just like their api counterparts, but in camel-case
	 */
	private String convertVarNameToApiName(String name) {
		StringBuffer buf = new StringBuffer();
		char c;
		for (int i = 0; i < name.length(); i++) {
			c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				buf.append('_');
				buf.append(Character.toLowerCase(c));
			} else {				
				buf.append(c);
			}
		}
		return buf.toString();
	}
	
}
