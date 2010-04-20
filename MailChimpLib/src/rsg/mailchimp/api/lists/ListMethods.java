package rsg.mailchimp.api.lists;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.MailChimpApi;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.Utils;
import rsg.mailchimp.api.campaigns.AbuseReport;
import android.content.Context;

/**
 * Provides the list related API functions
 * @author ericmuntz
 *
 */
public class ListMethods extends MailChimpApi {

	public ListMethods(CharSequence apiKey) {
		super(apiKey);
	}

	public ListMethods(Context ctx) {
		super(ctx);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listsubscribe.func.php</a>
	 * <br/>
	 * Convience for subscribing a user, same as calling listSubscribe(listId, emailAddress, null, null, null, null, null, null);
	 * @param listId
	 * @param emailAddress
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listSubscribe(String listId, String emailAddress) throws MailChimpApiException {
		return listSubscribe(listId, emailAddress, null, null, null, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listsubscribe.func.php</a>
	 * <br/>
	 * Convience for subscribing a user, same as calling listSubscribe(listId, emailAddress, mergeFields, null, null, null, null, null);
	 * @param listId
	 * @param emailAddress
	 * @param mergeFields
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listSubscribe(String listId, String emailAddress, MergeFieldListUtil mergeFields) throws MailChimpApiException {
		return listSubscribe(listId, emailAddress, mergeFields, null, null, null, null, null);	
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listsubscribe.func.php</a>
	 * <br/>
	 * Convience for subscribing a user, same as calling listSubscribe(listId, emailAddress, mergeFields, emailType, null, null, null, null);
	 * @param listId
	 * @param emailAddress
	 * @param mergeFields
	 * @param emailType
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listSubscribe(String listId, String emailAddress, MergeFieldListUtil mergeFields, Constants.EmailType emailType) throws MailChimpApiException {
		return listSubscribe(listId, emailAddress, mergeFields, emailType, null, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listsubscribe.func.php</a>
	 * <br/>
	 * Subscribe a single user to a list
	 * @param listId the id of the list, see getLists()
	 * @param emailAddress the email address to subscribe
	 * @param mergeFields the merge fields and values for the person to subscribe
	 * @param emailType one of the constant enums from Constants.EmailType, if null will default to the server's default (currently 'html')
	 * @param doubleOptIn if false, will subscribe without the double opt-in.  please use this carefully, if abused, this can result in account suspension.  if null, defaults to true
	 * @param updateExisting if true, will update the existing user.  if false or null and the user exists, this call will throw an exception
	 * @param replaceInterests if true, will replace the interests for the user, otherwise it will not.  if null, defaults to false
	 * @param sendWelcome if false, will not send the welcome message.  if null, defaults to true
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listSubscribe(String listId, String emailAddress, MergeFieldListUtil mergeFields, Constants.EmailType emailType, Boolean doubleOptIn, Boolean updateExisting,
			Boolean replaceInterests, Boolean sendWelcome) throws MailChimpApiException {
		
		return (Boolean) callMethod("listSubscribe", listId, 
				emailAddress, 
				mergeFields == null ? new String[] { "" } : mergeFields, // API Note: must send at least an empty array for the merge fields 
				emailType == null ? "" : emailType.toString(),
				doubleOptIn == null ? "" : doubleOptIn, 
				updateExisting == null ? "" : updateExisting, 
				replaceInterests == null ? "" : replaceInterests,
				sendWelcome == null ? "" : sendWelcome);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listunsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listunsubscribe.func.php</a>
	 * <br/>
	 * Convenience method for unsubscribing a user, same as calling listUnsubscribe(listId, emailAddress, null, null, null); 
	 * @param listId
	 * @param emailAddress
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listUnsubscribe(String listId, String emailAddress) throws MailChimpApiException {
		return listUnsubscribe(listId, emailAddress, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listunsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listunsubscribe.func.php</a>
	 * <br/>
	 * Unsubscribe a user from a list
	 * @param listId the list id to unsubscribe from, see getLists()
	 * @param emailAddress the email address of the user to unsubscribe
	 * @param deleteMember if true, will delete the member instead of just marking them as unsubscribed.  if null, defaults to false
	 * @param sendGoodbye if true, will send the goodbye email.  if null, defaults to true
	 * @param sendNotify if true, will send the unsubscribe email to the list owner notifying them a user has unsubscribed.  if null, defaults to true  
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listUnsubscribe(String listId, String emailAddress, Boolean deleteMember, Boolean sendGoodbye, Boolean sendNotify) throws MailChimpApiException {
		return (Boolean) callMethod("listUnsubscribe", listId, emailAddress, deleteMember == null ? "" : deleteMember, sendGoodbye == null ? "" : sendGoodbye, sendNotify == null ? "" : sendNotify);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listsforemail.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listsforemail.func.php</a>
	 * <br/>
	 * Returns a list of Strings representing the ids of the lists a specific email address is assigned to.
	 * Call 
	 * @param email
	 * @return
	 * @throws MailChimpApiException
	 */
	public List<String> listsForEmail(String email) throws MailChimpApiException {
		Object obj = callMethod("listsForEmail", email);
		if (obj instanceof Object[]) {
			return Utils.convertObjectArrayToString((Object[]) obj);
		} else {
			return new ArrayList<String>(0);
		}
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listmemberinfo.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmemberinfo.func.php</a>
	 * <br/>
	 * Gets member info of a member in a particular list
	 * @param listId
	 * @param emailAddress
	 * @return null if not found, otherwise the MemberInfo populated
	 * @throws MailChimpApiException
	 */
	@SuppressWarnings("unchecked")
	public MemberInfo listMemberInfo(String listId, String emailAddress) throws MailChimpApiException {
		Object obj = callMethod("listMemberInfo", listId, emailAddress);
		if (obj != null && obj instanceof Map) {
			MemberInfo info = new MemberInfo();
			info.populateFromRPCStruct(null, (Map) obj);
			return info;
		}
		return null;
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listmembers.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmembers.func.php</a>
	 * <br/>
	 * Same as calling listMembers(listId, null);
	 * @param listId
	 * @return
	 * @throws MailChimpApiException
	 */
	public List<MemberInfo> listMembers(String listId) throws MailChimpApiException {
		return listMembers(listId, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listmembers.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmembers.func.php</a>
	 * <br/>
	 * Same as calling listMembers(listId, null, null, null);
	 * @param listId
	 * @param status
	 * @return
	 * @throws MailChimpApiException
	 */
	public List<MemberInfo> listMembers(String listId, Constants.SubscribeStatus status) throws MailChimpApiException {
		return listMembers(listId, status, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listmembers.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmembers.func.php</a>
	 * <br/>
	 * Gets a list of MemberInfo values for the given list.  <B>NOTE:</B> The MemberInfo instances returned will only contain email and timestamp, as that is 
	 * all that is returned by this method in the API.  To get more information call getMemberInfo() with the email address.
	 * @param listId
	 * @param status
	 * @param start
	 * @param limit
	 * @param since
	 * @return
	 * @throws MailChimpApiException
	 */
	public List<MemberInfo> listMembers(String listId, Constants.SubscribeStatus status, Integer start, Integer limit, Date since) throws MailChimpApiException {
		// this function on the server doesn't like blank pagination values for some reason, so I'm gonna hack it up this way...
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(listId);
		params.add(status == null ? Constants.SubscribeStatus.subscribed.toString() : status.toString());
		if (since != null) params.add(Constants.TIME_FMT.format(since));
		if (start != null) params.add(start);
		if (limit != null) params.add(limit);
		return callListMethod(MemberInfo.class, "listMembers", params.toArray());
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listabusereports.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listabusereports.func.php</a>
	 * <br/>
	 * Gets abuse list reports, same as calling listAbuseReports(listId, null, null, null);
	 * @param listId
	 * @return
	 * @throws MailChimpApiException
	 */
	public List<AbuseReport> listAbuseReports(String listId) throws MailChimpApiException {
		return listAbuseReports(listId, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listabusereports.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listabusereports.func.php</a>
	 * <br/>
	 * Gets abuse list reports, with pagination opportunities
	 * @param listId
	 * @param start
	 * @param limit
	 * @param since
	 * @return
	 * @throws MailChimpApiException
	 */
	public List<AbuseReport> listAbuseReports(String listId, Integer start, Integer limit, Date since) throws MailChimpApiException {
		int startVal = start == null ? 0 : start;
		Object limitVal = limit == null ? "" : limit;
		String sinceVal = since == null ? "" : Constants.TIME_FMT.format(since);
		return callListMethod(AbuseReport.class, "listAbuseReports", listId, startVal, limitVal, sinceVal);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listbatchsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listbatchsubscribe.func.php</a>
	 * <br/>
	 * Same as calling listBatchSubscribe(listId, toSubscribe, null, null, null);
	 * @param listId
	 * @param toSubscribe
	 * @return
	 * @throws MailChimpApiException
	 */
	public BatchResults listBatchSubscribe(String listId, List<MergeFieldListUtil> toSubscribe) throws MailChimpApiException {
		return listBatchSubscribe(listId, toSubscribe, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listbatchsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listbatchsubscribe.func.php</a>
	 * <br/>
	 * Method to batch subscribe a list of emails.  The list of emails to add are passed as a list of MergeField instances.  Make sure the addEmail() method
	 * has been called on each MergeField instance
	 * @param listId
	 * @param toSubscribe
	 * @param doubleOptIn
	 * @param updateExisting
	 * @param replaceInterests
	 * @return
	 * @throws MailChimpApiException
	 */
	@SuppressWarnings("unchecked")
	public BatchResults listBatchSubscribe(String listId, List<MergeFieldListUtil> toSubscribe, Boolean doubleOptIn, Boolean updateExisting,
			Boolean replaceInterests) throws MailChimpApiException {
		Object obj = callMethod("listBatchSubscribe", listId, toSubscribe, doubleOptIn == null ? "" : doubleOptIn, updateExisting == null ? "" : updateExisting, replaceInterests == null ? "" : replaceInterests);
		if (obj != null && obj instanceof Map) {
			BatchResults results = new BatchResults();
			results.populateFromRPCStruct(null, (Map) obj);
			return results;
		} else {			
			return null;
		}
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listbatchunsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listbatchunsubscribe.func.php</a>
	 * <br/>
	 * Same as calling listBatchUnsubscribe(listId, toUnsubscribe, null, null, null);
	 * @param listId
	 * @param toUnsubscribe
	 * @return
	 * @throws MailChimpApiException
	 */
	public BatchResults listBatchUnsubscribe(String listId, List<String> toUnsubscribe) throws MailChimpApiException {
		return listBatchUnsubscribe(listId, toUnsubscribe, null, null, null);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listbatchunsubscribe.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listbatchunsubscribe.func.php</a>
	 * <br/>
	 * To batch unsubscribe a list of emails from a list.
	 * @param listId
	 * @param toUnsubscribe just a list of the Strings representing each email address
	 * @param deleteMember if true, deletes the member instead of keeping them and unsubscribing them, if null, uses the server default.
	 * @param sendGoodbye if true, sends the user a goodbye message.  if null, uses the server default.
	 * @param sendNotify if true, sends the list owner a notification of the unsubscribes.  if null, uses the server default.
	 * @return
	 * @throws MailChimpApiException
	 */
	@SuppressWarnings("unchecked")
	public BatchResults listBatchUnsubscribe(String listId, List<String> toUnsubscribe, Boolean deleteMember, Boolean sendGoodbye,
			Boolean sendNotify) throws MailChimpApiException {
		Object obj = callMethod("listBatchUnsubscribe", listId, toUnsubscribe, deleteMember == null ? "" : deleteMember, sendGoodbye == null ? "" : sendGoodbye, sendNotify == null ? "" : sendNotify);
		if (obj != null && obj instanceof Map) {
			BatchResults results = new BatchResults();
			results.populateFromRPCStruct(null, (Map) obj);
			return results;
		} else {			
			return null;
		}
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listgrowthhistory.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listgrowthhistory.func.php</a>
	 * <br/>
	 * Get the list of growth history for a given list
	 * @param listId
	 * @return
	 * @throws MailChimpApiException
	 */
	public List<GrowthHistory> listGrowthHistory(String listId) throws MailChimpApiException {
		return callListMethod(GrowthHistory.class, "listGrowthHistory", listId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listinterestgroupadd.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listinterestgroupadd.func.php</a>
	 * <br/>
	 * Add an interest group to a list
	 * @param listId
	 * @param groupName
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listInterestGroupAdd(String listId, String groupName) throws MailChimpApiException {
		return (Boolean) callMethod("listInterestGroupAdd", listId, groupName);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listinterestgroupdel.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listinterestgroupdel.func.php</a>
	 * <br/>
	 * Delete an interest group from a list
	 * @param listId
	 * @param groupName
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listInterestGroupDel(String listId, String groupName) throws MailChimpApiException {
		return (Boolean) callMethod("listInterestGroupDel", listId, groupName);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listinterestgroupupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listinterestgroupupdate.func.php</a>
	 * <br/>
	 * Change the name of an interest group
	 * @param listId
	 * @param oldGroupName
	 * @param newGroupName
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listInterestGroupUpdate(String listId, String oldGroupName, String newGroupName) throws MailChimpApiException {
		return (Boolean) callMethod("listInterestGroupUpdate", listId, oldGroupName, newGroupName);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listinterestgroups.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listinterestgroups.func.php</a>
	 * <br/>
	 * Gets the interest group information
	 * @param listId
	 * @return null if nothing is found
	 * @throws MailChimpApiException
	 */
	@SuppressWarnings("unchecked")
	public InterestGroupInfo listInterestGroups(String listId) throws MailChimpApiException {
		Object obj = callMethod("listInterestGroups", listId);
		if (obj != null && obj instanceof Map) {
			InterestGroupInfo info = new InterestGroupInfo();
			info.populateFromRPCStruct(null, (Map) obj);
			return info;
		} else {
			return null;
		}
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listmergevaradd.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmergevaradd.func.php</a>
	 * <br/>
	 * Adds a merge field to a list
	 * @param listId
	 * @param tag (also called the "name" in some places in the API)
	 * @param description the description
	 * @param options the options for the field
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listMergeVarAdd(String listId, String tag, String description, MergeFieldOptions options) throws MailChimpApiException {
		return (Boolean) callMethod("listMergeVarAdd", listId, tag, description, options == null ? new String[] {""} : options);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listmergevars.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmergevars.func.php</a>
	 * <br/>
	 * Returns a list of merge variables for a given list, as a List of MergeFieldInfo instances (these have much more detail than MergeFieldOptions)
	 * @param listId
	 * @return an empty list if there are no merge fields (though, there should always be at least one - email!)
	 * @throws MailChimpApiException
	 */
	public List<MergeFieldInfo> listMergeVars(String listId) throws MailChimpApiException {
		return callListMethod(MergeFieldInfo.class, "listMergeVars", listId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listmergevardel.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmergevardel.func.php</a>
	 * <br/>
	 * Delete a merge field
	 * @param listId
	 * @param tag
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean listMergeVarDel(String listId, String tag) throws MailChimpApiException {
		return (Boolean) callMethod("listMergeVarDel", listId, tag);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listmergevarupdate.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmergevarupdate.func.php</a>
	 * <br/>
	 * Update merge variable
	 * @param listId
	 * @param tag
	 * @param options
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listMergeVarUpdate(String listId, String tag, MergeFieldOptions options) throws MailChimpApiException {
		return (Boolean) callMethod("listMergeVarUpdate", listId, tag, options == null ? new String[] {""} : options);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listwebhookadd.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listwebhookadd.func.php</a>
	 * <br/>
	 * Adds a web hook.  
	 * @param listId
	 * @param url (note this will be validated by the API)
	 * @param actionsFlag - provide a bitwise OR field of values Constants.WEBHOOK_ACTION_XXX.  If null, will use server defaults 
	 * @param sourcesFlag - provide a bitwise OR field of values Constants.WEBHOOK_SOURCE_XXX.  If null, will use server defaults
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listWebhookAdd(String listId, String url, Integer actionsFlag, Integer sourcesFlag) throws MailChimpApiException {
		ArrayList<Object> params = new ArrayList<Object>(4);
		params.add(listId);
		params.add(url);
		if (actionsFlag != null) params.add(Utils.getWebHookActions(actionsFlag));
		if (sourcesFlag != null) params.add(Utils.getWebHookSources(sourcesFlag));
		return (Boolean) callMethod("listWebhookAdd", params.toArray()); 
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listwebhookdel.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listwebhookdel.func.php</a>
	 * <br/>
	 * Deletes a web hook
	 * @param listId
	 * @param url
	 * @return
	 * @throws MailChimpApiException
	 */
	public boolean listWebhookDel(String listId, String url) throws MailChimpApiException {
		return (Boolean) callMethod("listWebhookDel", listId, url);
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listwebhooks.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listwebhooks.func.php</a>
	 * <br/>
	 * Returns a list of webhooks for a campaign
	 * @param listId
	 * @return a list of webhooks or an empty non-null list if there are none
	 * @throws MailChimpApiException
	 */
	public List<WebHookInfo> listWebhooks(String listId) throws MailChimpApiException {
		return callListMethod(WebHookInfo.class, "listWebhooks", listId);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/lists.func.php" target="_new">http://www.mailchimp.com/api/rtfm/lists.func.php</a>
	 * <br/>
	 * Returns the lists for the account 
	 * @return a list of the list (instances of ListDetails) or a non-empty 0 count array
	 * @throws MailChimpApiException
	 */
	public List<ListDetails> lists() throws MailChimpApiException {
		return callListMethod(ListDetails.class, "lists");
	}
}
