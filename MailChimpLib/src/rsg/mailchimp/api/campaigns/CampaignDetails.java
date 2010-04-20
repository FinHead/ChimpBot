package rsg.mailchimp.api.campaigns;

import java.util.Date;
import java.util.Map;

import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;
import rsg.mailchimp.api.Constants.CampaignStatus;
import rsg.mailchimp.api.Constants.CampaignType;

/**
 * See <a href="http://www.mailchimp.com/api/rtfm/campaigns.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigns.func.php</a>
 * <br/>
 * Typesafe values for the campaign as returned from the campaigns API call
 * @author ericmuntz
 */
public class CampaignDetails implements RPCStructConverter {
	
	public String id;
	public Integer folderId;
	public String listId;
	public Integer webId;
	public String title;
	public Constants.CampaignType type;
	public Date createTime;
	public Date sendTime;
	public Integer emailsSent;
	public Constants.CampaignStatus status;
	public String fromName;
	public String fromEmail;
	public String subject;
	public String toEmail;
	public String archiveUrl;
	public Boolean inlineCss;
	public String analytics;
	public String analyticsTag;
	public Boolean trackClicksText;
	public Boolean trackClicksHtml;
	public Boolean trackOpens;
	public String segmentText;
	
	// TODO handle segment_opts

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map vals) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, vals, true, "status", "type");
		
		String statusVal = (String) vals.get("status");
		if (statusVal != null) status = CampaignStatus.valueOf(statusVal);
		String typeVal = (String) vals.get("type");
		if (typeVal != null) type = CampaignType.valueOf(typeVal);
	}

}
