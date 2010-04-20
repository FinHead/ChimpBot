package rsg.mailchimp.api.campaigns;

import java.util.Date;
import java.util.Hashtable;

import rsg.mailchimp.api.Constants;

/**
 * Helper class to build the options up for the campaigns API call.  All methods return an instance of this class, so 
 * filters can be chained together.  
 * <br/>See: <a href="http://www.mailchimp.com/api/rtfm/campaigns.func.php" target="_new">http://www.mailchimp.com/api/rtfm/campaigns.func.php</a>
 * <br/>
 * Example usage:
 * <p>Get 10 results:
 * <code>CampaignListFilters filters = new CampaignListFilters().setLimit(10);</code>
 * </p>
 * <p>Get only sent results:
 * <code>CampaignListFilters filters = new CampaignListFilters().setStatus(MailChimpCampaignApi.CampaignStatus.sent);</code>
 * </p>
 * <p>Get only sent RSS campaigns:
 * <code>CampaignListFilters filters = new CampaignListFilters().setStatus(MailChimpCampaignApi.CampaignStatus.sent).setType(MailChimpCampaignApi.CampaignType.rss);</code>
 * </p>
 * 
 * @author ericmuntz
 */
public class CampaignListFilters {

	private static final int LIMIT_MAX = 1000;
	
	private Hashtable<String, Object> searchOptions = new Hashtable<String, Object>();
	private int start = 0;
	private int limit = 25;
		
	/**
	 * Default constructor
	 */
	public CampaignListFilters() { }
	
	/**
	 * Returns the Hashtable with all the options for this filter
	 * @return
	 */
	/*package-public scope intentional*/ Hashtable<String, Object> getSearchOptions() {
		return searchOptions;
	}

	/**
	 * Returns the start parameter for this filter
	 * @return
	 */
	/*package-public scope intentional*/ int getStart() {
		return start;
	}

	/**
	 * Returns the limit parameter for this filter
	 * @return
	 */
	/*package-public scope intentional*/ int getLimit() {
		return limit;
	}

	/**
	 * Set the start of this search
	 * @param startVal
	 * @return
	 */
	public CampaignListFilters setStart(int startVal) {
		this.start = startVal;
		return this;
	}

	/**
	 * Set the limit for this search
	 * @param limit
	 * @return
	 */
	public CampaignListFilters setLimit(int limit) {
		if (limit > LIMIT_MAX) throw new IllegalArgumentException("limit (" + limit + ") exceeds maximum value (" + LIMIT_MAX + ")");
		if (limit < 1) throw new IllegalArgumentException("limit called with a value <= 0");
		this.limit = limit;
		return this;
	}
	
	/**
	 * Set the campaignId to limit the results on, only this single campaign will be returned
	 * @param campaignId
	 * @return
	 */
	public CampaignListFilters setCampaignId(String campaignId) {
		searchOptions.put("campaign_id", campaignId);
		return this;
	}
	
	/**
	 * Set the list id to limit the results on, only campains assigned to the list id in question will be returned
	 * @param listId
	 * @return
	 */
	public CampaignListFilters setListId(String listId) {
		searchOptions.put("list_id", listId);
		return this;
	}
	
	/**
	 * Set the folder id to limit the results on
	 * @param folderId
	 * @return
	 * @see CampaignMethods.listFolders
	 */
	public CampaignListFilters setFolderId(String folderId) {
		searchOptions.put("folder_id", folderId);
		return this;
	}
	
	/**
	 * Set the status to limit the results on
	 * @param status
	 * @return
	 */
	public CampaignListFilters setStatus(Constants.CampaignStatus status) {
		searchOptions.put("status", status.toString());
		return this;
	}
	
	/**
	 * Set the type to limit the results on
	 * @param type
	 * @return
	 */
	public CampaignListFilters setType(Constants.CampaignType type) {
		searchOptions.put("type", type.toString());
		return this;
	}
	
	/**
	 * Set the from email to limit the results on
	 * @param email
	 * @return
	 */
	public CampaignListFilters setFromEmail(String email) {
		searchOptions.put("from_email", email);
		return this;
	}
	
	/**
	 * Set the title to limit the results on
	 * @param title
	 * @return
	 */
	public CampaignListFilters setTitle(String title) {
		searchOptions.put("title", title);
		return this;
	}
	
	/**
	 * Set the subject to limit the results on
	 * @param subject
	 * @return
	 */
	public CampaignListFilters setSubject(String subject) {
		searchOptions.put("subject", subject);
		return this;
	}
	
	/**
	 * Set the start time (IN GMT) to limit the results on, will only 
	 * return cammpaigns that have been sent since this datetime
	 * @param time
	 * @return
	 */
	public CampaignListFilters setSendtimeStart(Date time) {
		searchOptions.put("sendtime_start", Constants.TIME_FMT.format(time));
		return this;
	}
	
	/**
	 * Send the end time (IN GMT) to limit the results on, will only 
	 * return campaigns that have been sent before this datetime
	 * @param time
	 * @return
	 */
	public CampaignListFilters setSendtimeEnd(Date time) {
		searchOptions.put("sendtime_end", Constants.TIME_FMT.format(time));
		return this;
	}
	
	/**
	 * By default, the searching done with the options set in this class are exact, 
	 * using this method you can disable that so the API will search within content
	 * for the filtered values
	 * @param isExactSearch
	 * @return
	 */
	public CampaignListFilters setExact(boolean isExactSearch) {
		searchOptions.put("exact", new Boolean(isExactSearch));
		return this;
	}
}
