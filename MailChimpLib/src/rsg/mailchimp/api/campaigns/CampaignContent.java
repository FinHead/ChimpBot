package rsg.mailchimp.api.campaigns;

import java.util.Hashtable;

import org.xmlrpc.android.XMLRPCSerializable;

/**
 * Simple class to hold Campaign content as a result of the campaignContent method
 * <br/>See: <a href="http://www.mailchimp.com/api/rtfm/campaignCreate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignCreate.func.php</a>
 * @author ericmuntz
 *
 */
public class CampaignContent implements XMLRPCSerializable {

	public String html;
	public String text;
	public String url;
	public String archiveBytes;//base64 encoded byte[] for an upload file
	public String archiveType = "zip";

	// for template vals
	public String templateHtmlHeader;
	public String templateHtmlMain;
	public String templateHtmlSidecolumn;
	public String templateHtmlFooter;
	
	/**
	 * Implements XMLRPSerializable
	 * @return a hashmap with the appropriate values set
	 */
	public Object getSerializable() {		
		Hashtable<String, Object> map = new Hashtable<String, Object>();
		
		if (url != null) {
			map.put("url", url);
		} else if (archiveBytes != null) {
			map.put("achive_bytes", archiveBytes);
			map.put("archive_type", archiveType);
		} else if (html != null || text != null ) {
			map.put("html", html);
			map.put("text", text);			
		} else {
			if (templateHtmlHeader != null) {
				map.put("html_HEADER", templateHtmlHeader);
			}
			
			if (templateHtmlMain != null) {
				map.put("html_MAIN", templateHtmlMain);
			}
			
			if (templateHtmlSidecolumn != null) {
				map.put("html_SIDECOLUMN", templateHtmlSidecolumn);
			}
			
			if (templateHtmlFooter != null) {
				map.put("html_FOOTER", templateHtmlFooter);
			}
		}
		
		return map;
	}
	
}
