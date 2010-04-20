package rsg.mailchimp.api.campaigns;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.MailChimpApi;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.Utils;
import rsg.mailchimp.api.data.FolderInfo;
import android.content.Context;

/**
 * Subclass of MailChimpApi for dealing with all campaign-related API functions.
 */
@SuppressWarnings("unchecked")
public class CampaignMethods extends MailChimpApi {

	/**
	 * Constructor to use the preferences from the manifest file
	 * @param ctx
	 */
	public CampaignMethods(Context ctx) {
		super(ctx);
	}
	
	/**
	 * Constructor to use when you want to specifically set the API key
	 * @param apiKey
	 */
	public CampaignMethods(CharSequence apiKey) {
		super(apiKey);
	}
	
	/**
	 * 
	 * @param filters
	 * @return
	 * @throws MailChimpApiException
	 * @see CampaignListFilters
	 */
	public List<CampaignDetails> listCampaigns(CampaignListFilters filters) throws MailChimpApiException {
		Object filtersParam = filters.getSearchOptions().size() > 0 ? filters.getSearchOptions() : "";
		return callListMethod(CampaignDetails.class, "campaigns", filtersParam, filters.getStart(), filters.getLimit());
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigncontent.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigncontent.func.php</a>
	 * <br/>
	 * Convenience method for calling campaignContent with the default archiveVersion parameter (true).
	 * @param campaignId
	 * @return an instance of CampaignContent with html and plainText set for the campaign
	 * @throws MailChimpApiException
	 * @see getCampaignContent(String, boolean)
	 */
	public CampaignContent campaignContent(String campaignId) throws MailChimpApiException {
		return campaignContent(campaignId, true);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigncontent.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigncontent.func.php</a>
	 * <br/>
	 * Get the content of a campaign.
	 * @param campaignId
	 * @param archiveVersion
	 * @return This will return a CampaignContent instance with just html and plainText values set
	 * @throws MailChimpApiException
	 */
	public CampaignContent campaignContent(String campaignId, boolean archiveVersion) throws MailChimpApiException {
		Map result = (Map) callMethod("campaignContent", campaignId, archiveVersion);
		CampaignContent content = new CampaignContent();
		content.html = (String) result.get("html");
		content.text = (String) result.get("text");
		return content;
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigncreate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigncreate.func.php</a>
	 * <br/>
	 * Convenience method to create a campaign without segmentation or type options
	 * @param type the type of campaign to create of type MailChimpCampaignApi.CampaignType
	 * @param options the options to use in creating this campaign
	 * @param content the content to create this campaign from
	 * @return the id of the created campaign
	 * @throws MailChimpApiException
	 */
	public String campaignCreate(Constants.CampaignType type, CampaignOptions options, CampaignContent content) throws MailChimpApiException {
		return campaignCreate(type, options, content, null, null);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigncreate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigncreate.func.php</a>
	 * <br/>
	 * Method to create a campaign
	 * @param type the type of campaign to create of type MailChimpCampaignApi.CampaignType
	 * @param options the options to use in creating this campaign
	 * @param content the content to create this campaign from
	 * @param segmentOptions optional (can be null) - a Map of values according to the specification at the API site.  See examples or online docs for the values
	 * @param typeOptions optional (can be null) - a Map of values according to the specification at the API site.  See examples or online docs for the values.
	 * @return the id of the created campaign
	 * @throws MailChimpApiException
	 */
	public String campaignCreate(Constants.CampaignType type, CampaignOptions options, CampaignContent content, Map<String,String> segmentOptions, Map<String, String> typeOptions) throws MailChimpApiException {
		return (String) callMethod("campaignCreate", type.toString(), options, content, segmentOptions == null ? "" : segmentOptions, typeOptions == null ? "" : typeOptions);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigndelete.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigndelete.func.php</a>
	 * <br/>
	 * Method to delete a campaign
	 * @param campaignId
	 * @return true if it was able to delete the campaign, false otherwise
	 * @throws MailChimpApiException 
	 */
	public boolean campaignDelete(String campaignId) throws MailChimpApiException {
		return (Boolean) callMethod("campaignDelete", campaignId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignfolders.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignfolders.func.php</a>
	 * <br/>
	 * Get a list of Folders in the account
	 * @return array of FolderInfo, if there were no folders, this returns a non-null, 0-length array.
	 * @throws MailChimpApiException
	 */
	public List<FolderInfo> campaignFolders() throws MailChimpApiException {
		return callListMethod(FolderInfo.class, "campaignFolders");
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignpause.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignpause.func.php</a>
	 * <br/>
	 * Pause an autoresponder or RSS campaign
	 * @param campaignId
	 * @return true if it was able to succesfully pause
	 * @throws MailChimpApiException
	 */
	public boolean campaignPause(String campaignId) throws MailChimpApiException {
		return (Boolean) callMethod("campaignPause", campaignId);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignresume.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignresume.func.php</a>
	 * <br/>
	 * Resume an autoresponder or RSS campaign
	 * @param campaignId
	 * @return true if it was able to successfully resume
	 * @throws MailChimpApiException
	 */
	public boolean campaignResume(String campaignId) throws MailChimpApiException {
		return (Boolean) callMethod("campaignResume", campaignId);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignsendnow.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignsendnow.func.php</a>
	 * <br/>
	 * Send/Start a campaign immediately.
	 * @param campaignId
	 * @return true if it was successful, false otherwise
	 * @throws MailChimpApiException
	 */
	public boolean campaignSendNow(String campaignId) throws MailChimpApiException {
		return (Boolean) callMethod("campaignSendNow", campaignId);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/createfolder.func.php" target="_new">http://www.mailchimp.com/api/rtfm/createfolder.func.php</a>
	 * Create a folder with the given name
	 * @param name
	 * @return the id returned by the server
	 * @throws MailChimpApiException
	 */
	public Integer createFolder(String name) throws MailChimpApiException {
		return (Integer) callMethod("createFolder", name);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignsendtest.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignsendtest.func.php</a>
	 * <br/>
	 * Convenience method to send test without specifying send type
	 * @param campaignId
	 * @param emails
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean campaignSendTest(String campaignId, String[] emails) throws MailChimpApiException {
		return campiagnSendTest(campaignId, emails, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignsendtest.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignsendtest.func.php</a>
	 * <br/>
	 * Convenience method to send test without specifying send type
	 * @param campaignId
	 * @param emails
	 * @param sendType one of "html" or "text", if null will send both types
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean campiagnSendTest(String campaignId, String[] emails, String sendType) throws MailChimpApiException {
		return (Boolean) callMethod("campaignSendTest", campaignId, emails, sendType == null ? "" : sendType);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignsharereport.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignsharereport.func.php</a>
	 * <br/>
	 * Convenience method to simply share the report for a campaign.  This will return the details of the shared report, which
	 * include the URL, title, secure url and password if pertinent
	 * @param campaignId
	 * @param secured if true, will create a secured report with either the password specified in password parameter, or one created by MailChimp
	 * @param password if non-null and secured is true, this will be used as the secure password for the report.
	 * @return the details of the report
	 * @throws MailChimpApiException 
	 */
	public SharedReportDetails campiagnShareReport(String campaignId, boolean secured, String password) throws MailChimpApiException {
		return campaignShareReport(campaignId, null, secured, password);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignsharereport.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignsharereport.func.php</a>
	 * <br/>
	 * Convenience method to simply share the report for a campaign.  This will return the details of the shared report, which
	 * include the URL, title, secure url and password if pertinent
	 * @param campaignId
	 * @param emailAddress the email address to share the report with
	 * @param secured if true, will create a secured report with either the password specified in password parameter, or one created by MailChimp
	 * @param password if non-null and secured is true, this will be used as the secure password for the report.
	 * @return the details of the report
	 * @throws MailChimpApiException 
	 */	
	public SharedReportDetails campaignShareReport(String campaignId, String emailAddress, boolean secured, String password) throws MailChimpApiException {
		// build the options table
		Hashtable<String, Object> opts = new Hashtable<String, Object>();
		opts.put("secure", secured);
		if (emailAddress != null) {
			opts.put("to_email", emailAddress);
		}
		if (password != null) {
			opts.put("password", password);
		}
		
		// send the call
		Map results = (Map) callMethod("campaignShareReport", campaignId, opts);
		
		// build the shared report from the call and return
		SharedReportDetails details = new SharedReportDetails();
		details.title = results.get("title") == null ? null : results.get("title").toString();
		details.url = results.get("url") == null ? null : results.get("url").toString();
		details.secureUrl = results.get("secure_url") == null ? null : results.get("secure_url").toString();
		details.password = results.get("password") == null ? null : results.get("password").toString();
		return details;
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigntemplates.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigntemplates.func.php</a>
	 * <br/>
	 * Gets all templates in the user's account
	 * @return
	 * @throws MailChimpApiException 
	 */
	public List<TemplateDetails> campiagnTemplates() throws MailChimpApiException {
		return callListMethod(TemplateDetails.class, "campaignTemplates");
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignschedule.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignschedule.func.php</a>
	 * <br/>
	 * Schedules a campaign
	 * @param campaignId
	 * @param time NOTE THIS IS ASSUMED TO BE IN GMT, it's up to the calling developer to make sure of the timezone of their date
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean campiagnSchedule(String campaignId, Date time) throws MailChimpApiException {
		return campaignSchedule(campaignId, time, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignschedule.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignschedule.func.php</a>
	 * <br/>
	 * Schedules a campaign with an AB-Split "B" time.  The time parameter is assumed to be the "A" time
	 * @param campaignId
	 * @param time NOTE THIS IS ASSUMED TO BE IN GMT, it's up to the calling developer to make sure of the timezone of their date
	 * @param splitTimeB NOTE THIS IS ASSUMED TO BE IN GMT, it's up to the calling developer to make sure of the timezone of their date
	 * @return true if successful
	 * @throws MailChimpApiException
	 */	
	public boolean campaignSchedule(String campaignId, Date time, Date splitTimeB) throws MailChimpApiException {
		return (Boolean) callMethod("campaignSchedule", campaignId, Constants.TIME_FMT.format(time), splitTimeB == null ? "" : Constants.TIME_FMT.format(splitTimeB));
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignunschedule.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignunschedule.func.php</a>
	 * <br/>
	 * Unschedule a campaign
	 * @param campaignId
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean campaignUnschedule(String campaignId) throws MailChimpApiException {
		return (Boolean) callMethod("campaignUnschedule", campaignId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignupdate.func.php</a>
	 * <br/>
	 * Convenience to change the title (the title is the internal name of the campaign, different from 'subject')
	 * @param campaignId
	 * @param newTitle
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean campaignUpdateTitle(String campaignId, String newTitle) throws MailChimpApiException {
		return campaignUpdate(campaignId, "title", newTitle);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignupdate.func.php</a>
	 * <br/>
	 * Convenience to change the subject (the subject is what the recipient sees in their inbox)
	 * @param campaignId
	 * @param newSubject
	 * @return
	 * @throws MailChimpApiException
	 */	
	public boolean campaignUpdateSubject(String campaignId, String newSubject) throws MailChimpApiException {
		return campaignUpdate(campaignId, "subject", newSubject);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignupdate.func.php</a>
	 * <br/>
	 * Convenience to change the from email address
	 * @param campaignId
	 * @param newFromEmail
	 * @return
	 * @throws MailChimpApiException
	 */	
	public boolean campaignUpdateFromEmail(String campaignId, String newFromEmail) throws MailChimpApiException {
		return campaignUpdate(campaignId, "from_email", newFromEmail);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignupdate.func.php</a>
	 * <br/>
	 * Convenience to change the from name (just the name, i.e. "Freddie the Chimp", not the email address)
	 * @param campaignId
	 * @param newFromName
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean campaignUpdateFromName(String campaignId, String newFromName) throws MailChimpApiException {
		return campaignUpdate(campaignId, "from_name", newFromName);
	}
	
	/**
	 * Convenience to change the to name (the name in the to: field)
	 * @param campaignId
	 * @param newName
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean campaignUpdateToName(String campaignId, String newName) throws MailChimpApiException {
		return campaignUpdate(campaignId, "to_name", newName);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignupdate.func.php</a>
	 * <br/>
	 * Convenience to change the auto tweet setting
	 * @param campaignId
	 * @param turnOn
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean campaignUpdateAutoTweet(String campaignId, boolean turnOn) throws MailChimpApiException {
		return campaignUpdate(campaignId, "auto_tweet", new Boolean(turnOn));
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignupdate.func.php</a>
	 * <br/>
	 * Convenience to change the List to send the campaign to.  NOTE: this will undo any previous segmentation
	 * @param campaignId
	 * @param newListId
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean campaignUpdateListToSendTo(String campaignId, String newListId)  throws MailChimpApiException {
		return campaignUpdate(campaignId, "list_id", newListId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignupdate.func.php</a>
	 * <br/>
	 * Generic handler for updating any value in a campaign.  See the documentation link for much further detail on what the 
	 * name/value parameters should contain, or use updateXXX() convenience methods for the standard things to update.
	 * @param campaignId
	 * @param name
	 * @param value
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean campaignUpdate(String campaignId, String name, Object value) throws MailChimpApiException {
		return (Boolean) callMethod("campaignUpdate", campaignId, name, value);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignabusereports.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignabusereports.func.php</a>
	 * <br/>
	 * Get abuse reports for the given campaign
	 * @param campaignId
	 * @param start if null, will default to 0
	 * @param limit if null, will default to API's default (currently 500)
	 * @param since if null, will not be sent to the API to limit the results
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<AbuseReport> campaignAbuseReports(String campaignId, Integer start, Integer limit, Date since) throws MailChimpApiException {
		int startVal = start == null ? 0 : start;
		Object limitVal = limit == null ? "" : limit;
		String sinceVal = since == null ? "" : Constants.TIME_FMT.format(since); 
				
		List<AbuseReport> list = callListMethod(AbuseReport.class, "campaignAbuseReports", campaignId, sinceVal, startVal, limitVal);
		for (AbuseReport report : list) {
			report.campaignId = campaignId;
		}
		
		return list;
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignadvice.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignadvice.func.php</a>
	 * <br/>
	 * Returns the advice for the given campaign
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<CampaignAdvice> campaignAdvice(String campaignId) throws MailChimpApiException {
		return callListMethod(CampaignAdvice.class, "campaignAdvice", campaignId);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignanalytics.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignanalytics.func.php</a>
	 * <br/>
	 * Returns analytics for a given campaign
	 * @param campaignId
	 * @return the analytics for the campaign or null if nothing found
	 * @throws MailChimpApiException
	 */
	public CampaignAnalytics campaignAnalytics(String campaignId) throws MailChimpApiException {
		Map map = (Map) callMethod("campaignAnalytics", campaignId);
		CampaignAnalytics analytics = null;
		if (map != null) {			
			analytics = new CampaignAnalytics();
			analytics.populateFromRPCStruct(null, map);
		}
		return analytics;
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignbouncemessages.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignbouncemessages.func.php</a>
	 * <br/>
	 * Retuns the list of bounce messages for this campaign.  Convenience method to just get the default list,
	 * same as getCampaignBounceMessages(campaignId, null, null, null);
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException 
	 */
	public List<BounceMessage> campaignBounceMessages(String campaignId) throws MailChimpApiException {
		return campaignBounceMessages(campaignId, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignbouncemessages.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignbouncemessages.func.php</a>
	 * <br/>
	 * Returns the list of bounce messages for this campaign.
	 * @param campaignId
	 * @param start if null, the server's default will be used
	 * @param limit if null, the server's default will be used
	 * @param since date to limit the messages by, in GMT, if null will return them all 
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<BounceMessage> campaignBounceMessages(String campaignId, Integer start, Integer limit, Date since) throws MailChimpApiException {
		return callListMethod(BounceMessage.class, "campaignBounceMessages", campaignId, start == null ? 0 : start, limit == null ? 25 : limit, since == null ? "" : Constants.TIME_FMT.format(since));
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignclickstats.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignclickstats.func.php</a>
	 * <br/>
	 * Returns the list of URL Click stats for the given campaign
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<URLClickStats> campaignClickStats(String campaignId) throws MailChimpApiException {
		return callListMethod(URLClickStats.class, "campaignClickStats", campaignId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignecommorders.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignecommorders.func.php</a>
	 * <br/>
	 * Convenience method to get all ECommerce orders.  Same as calling
	 * getECommOrders(campaignId, null, null, null)
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<ECommOrders> campaignECommOrders(String campaignId) throws MailChimpApiException {
		return campaignECommOrders(campaignId, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignecommorders.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignecommorders.func.php</a>
	 * <br/>
	 * Returns the list of ECommerce orders for this campaign.
	 * @param campaignId
	 * @param start if null, the server's default will be used
	 * @param limit if null, the server's default will be used
	 * @param since date to limit the messages by, in GMT, if null will return them all 
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException 
	 */
	public List<ECommOrders> campaignECommOrders(String campaignId, Integer start, Integer limit, Date since) throws MailChimpApiException {
		return callListMethod(ECommOrders.class, "campaignEcommOrders", campaignId, start == null ? 0 : start, limit == null ? 100 : limit, since == null ? "" : Constants.TIME_FMT.format(since));
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigneepurlstats.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigneepurlstats.func.php</a>
	 * <br/>
	 * Returns the list of EepUrlStats, currently it'll only be a list of size one with Twitter data in it
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<EepUrlStats> campaignEepUrlStats(String campaignId) throws MailChimpApiException {
		return callListMethod(EepUrlStats.class, "campaignEepUrlStats", campaignId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignemaildomainperformance.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignemaildomainperformance.func.php</a>
	 * <br/>
	 * Get the Email Domain Performance of the campaign
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<EmailDomainPerformance> campaignEmailDomainPerformance(String campaignId) throws MailChimpApiException {
		return callListMethod(EmailDomainPerformance.class, "campaignEmailDomainPerformance", campaignId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigngeoopens.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigngeoopens.func.php</a>
	 * <br/>
	 * Returns a list of GeoOpens for a given campaign
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<GeoOpens> campaignGeoOpens(String campaignId) throws MailChimpApiException {
		return callListMethod(GeoOpens.class, "campaignGeoOpens", campaignId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaigngeoopensforcountry.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigngeoopensforcountry.func.php</a>
	 * <br/>
	 * For ISO3166 2-digit country codes, see <a href="http://en.wikipedia.org/wiki/ISO_3166" target="_new">http://en.wikipedia.org/wiki/ISO_3166</a><br/>
	 * Returns a list of GeoOpens for a given campaign and country code
	 * @param campaignId
	 * @param countryCode ISO3166 2-digit country code
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException 
	 */
	public List<GeoOpens> campaignGeoOpens(String campaignId, String countryCode) throws MailChimpApiException {
		return callListMethod(GeoOpens.class, "campaignGeoOpensForCountry", campaignId, countryCode);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignhardbounces.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignhardbounces.func.php</a>
	 * <br/>
	 * Gets email addresses that hard bounced, same as calling getHardBounces(campaignId, null, null)
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<String> campaignHardBounces(String campaignId) throws MailChimpApiException {
		return campaignHardBounces(campaignId, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignhardbounces.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignhardbounces.func.php</a>
	 * <br/>
	 * Gets email addresses that hard bounced
	 * @param campaignId
	 * @param start 0 if null
	 * @param limit defaults to API default if null
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<String> campaignHardBounces(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
		Object obj = callMethod("campaignHardBounces", campaignId, start == null ? 0 : start, limit == null ? 1000 : limit);
		if (obj instanceof Object[]) {
			return Utils.convertObjectArrayToString((Object[]) obj);
		} else {
			return new ArrayList<String>(0);
		}
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignsoftbounces.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignsoftbounces.func.php</a>
	 * <br/>
	 * Gets email addresses that soft bounced, same as calling getSoftBounces(campaignId, null, null)
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<String> campaignSoftBounces(String campaignId) throws MailChimpApiException {
		return campaignSoftBounces(campaignId, null, null);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignsoftbounces.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignsoftbounces.func.php</a>
	 * <br/>
	 * Gets email addresses that soft bounced
	 * @param campaignId
	 * @param start 0 if null
	 * @param limit defaults to API default if null
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	private List<String> campaignSoftBounces(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
		Object obj = callMethod("campaignSoftBounces", campaignId, start == null ? 0 : start, limit == null ? 1000 : limit);
		if (obj instanceof Object[]) {
			return Utils.convertObjectArrayToString((Object[]) obj);
		} else {
			return new ArrayList<String>(0);
		}
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignunsubscribes.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignunsubscribes.func.php</a>
	 * <br/>
	 * Gets email addresses that soft bounced, same as calling getUnsubscribes(campaignId, null, null)
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<String> campaignUnsubscribes(String campaignId) throws MailChimpApiException {
		return campaignUnsubscribes(campaignId, null, null);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignunsubscribes.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignunsubscribes.func.php</a>
	 * <br/>
	 * Gets email addresses that soft bounced
	 * @param campaignId
	 * @param start 0 if null
	 * @param limit defaults to API default if null
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	private List<String> campaignUnsubscribes(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
		Object obj = callMethod("campaignUnsubscribes", campaignId, start == null ? 0 : start, limit == null ? 1000 : limit);
		if (obj instanceof Object[]) {
			return Utils.convertObjectArrayToString((Object[]) obj);
		} else {
			return new ArrayList<String>(0);
		}
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignstats.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignstats.func.php</a>
	 * <br/>
	 * Gets the stats for the given campaign
	 * @param campaignId
	 * @return NOTE: unlike most methods in this class, this returns null when there are no stats
	 * @throws MailChimpApiException
	 */
	public CampaignStats campaignStats(String campaignId) throws MailChimpApiException {
		Object obj = callMethod("campaignStats", campaignId);
		if (obj instanceof Map) {
			CampaignStats stats = new CampaignStats();
			stats.populateFromRPCStruct(null, (Map) obj);
			return stats;
		} else {
			return null;
		}
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignclickdetailaim.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignclickdetailaim.func.php</a>
	 * <br/>
	 * Convenience to get click count details
	 * Same as calling getClickDetailAIM(campaignId, url, null, null);
	 * @param campaignId
	 * @param url
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<EmailClickCounts> campaignClickDetailAIM(String campaignId, String url) throws MailChimpApiException {
		return campaignClickDetailAIM(campaignId, url, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignclickdetailaim.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignclickdetailaim.func.php</a>
	 * <br/>
	 * Get the AIM report click count details for each email address
	 * @param campaignId
	 * @param url
	 * @param start
	 * @param limit
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<EmailClickCounts> campaignClickDetailAIM(String campaignId, String url, Integer start, Integer limit) throws MailChimpApiException {
		return callListMethod(EmailClickCounts.class, "campaignClickDetailAIM", campaignId, url, start == null ? 0 : start, limit == null ? 1000 : limit);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignemailstatsaimall.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignemailstatsaimall.func.php</a>
	 * <br/>
	 * Method to get all the AIM stats for all emails set to.
	 * Convenience for getEmailStatsAIM(campaignId, null);
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<EmailAIMStats> campaignEmailStatsAIMAll(String campaignId) throws MailChimpApiException {
		return campaignEmailStatsAIMAll(campaignId, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignemailstatsaimall.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignemailstatsaimall.func.php</a> and method <a href="http://www.mailchimp.com/api/rtfm/campaignemailstatsaim.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignemailstatsaim.func.php</a>
	 * <br/>
	 * Method to get all the AIM stats for a specific EMail address or all for all emails sent to if the email address field is null
	 * @param campaignId
	 * @param emailAddress if null, will return ALL of them
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<EmailAIMStats> campaignEmailStatsAIMAll(String campaignId, String emailAddress) throws MailChimpApiException {
		Object result = emailAddress != null ?
			callMethod("campaignEmailStatsAIM", campaignId, emailAddress) :
			callMethod("campaignEmailStatsAIMAll", campaignId);
			
		// this one is a little different, the resulting object is a Map<String, Map[]> where the nested struct is the list of stats
		ArrayList<EmailAIMStats> retVal;
		if (result instanceof Map) {			
			Map<String, Object[]> results = (Map<String, Object[]>) result;
			Set<Map.Entry<String, Object[]>> entries = results.entrySet();
			retVal = new ArrayList<EmailAIMStats>(entries.size());
			for (Map.Entry<String, Object[]> entry : entries) {
				EmailAIMStats stat = new EmailAIMStats();
				stat.build(entry);
				retVal.add(stat);
			}
		} else {
			Object[] entries = (Object[]) result;
			retVal = new ArrayList<EmailAIMStats>(1);
			EmailAIMStats stat = new EmailAIMStats();
			stat.email = emailAddress;
			stat.buildStatsList(entries);
			retVal.add(stat);
		}
		return retVal;
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignnotopenedaim.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignnotopenedaim.func.php</a>
	 * <br/>
	 * Get a list of all emails that didn't open a campaign, this is a convenience for campaignNotOpenedAIM(campaignId, null, null)
	 * @param campaignId
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<String> campaignNotOpenedAIM(String campaignId) throws MailChimpApiException {
		return campaignNotOpenedAIM(campaignId, null, null);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignnotopenedaim.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignnotopenedaim.func.php</a>
	 * <br/>
	 * @param campaignId
	 * @param start
	 * @param limit
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<String> campaignNotOpenedAIM(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
		Object obj = callMethod("campaignNotOpenedAIM", campaignId, start == null ? 0 : start, limit == null ? 1000 : limit);
		if (obj instanceof Object[]) {
			return Utils.convertObjectArrayToString((Object[]) obj);
		} else {
			return new ArrayList<String>(0);
		}
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignopenedaim.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignopenedaim.func.php</a>
	 * <br/>
	 * Get campaign opened AIM reports for a campaign (this is the only free AIM report).
	 * This method is a convience method for getCampaignOpenedAIM(campaignId, null, null)
	 * @param campaignId
	 * @param start
	 * @param limit
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<EmailOpenCounts> campaignOpenedAIM(String campaignId) throws MailChimpApiException {
		return campaignOpenedAIM(campaignId, null, null);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/campaignopenedaim.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaignopenedaim.func.php</a>
	 * <br/>
	 * Get campaign opened AIM reports for a campaign (this is the only free AIM report)
	 * @param campaignId
	 * @param start
	 * @param limit
	 * @return the list, or non-null 0-length list if nothing found
	 * @throws MailChimpApiException
	 */
	public List<EmailOpenCounts> campaignOpenedAIM(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
		return callListMethod(EmailOpenCounts.class, "campaignOpenedAIM", campaignId, start == null ? 0 : start, limit == null ? 1000 : limit);
	}
}
