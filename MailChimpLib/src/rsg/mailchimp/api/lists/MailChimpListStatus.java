package rsg.mailchimp.api.lists;

import rsg.mailchimp.api.Constants;

/**
 * Object representation of a user's subscribe status on a given list
 * <br/><a href="http://www.mailchimp.com/api/rtfm/listmemberinfo.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmemberinfo.func.php</a>
 * @author ericmuntz
 */
public class MailChimpListStatus {

	public String listId;
	public Constants.SubscribeStatus status;
	
}
