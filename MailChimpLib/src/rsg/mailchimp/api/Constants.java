package rsg.mailchimp.api;

import java.text.SimpleDateFormat;


/**
 * Yay, constants.
 */
public class Constants {

	/** Flag for adding a webhook to the subscribe action */
	public static final int WEBHOOK_ACTION_SUBSCRIBE = 2;
	
	/** Flag for adding a webhook to the subscribe action */
	public static final int WEBHOOK_ACTION_UNSUBSCRIBE = 4;
	
	/** Flag for adding a webhook to the subscribe action */
	public static final int WEBHOOK_ACTION_PROFILE = 8;
	
	/** Flag for adding a webhook to the subscribe action */
	public static final int WEBHOOK_ACTION_CLEANED = 16;
	
	/** Flag for adding a webhook to the subscribe action */
	public static final int WEBHOOK_ACTION_UPEMAIL = 32;
	
	/** Flag for adding a webhook to the system fired by the user */
	public static final int WEBHOOK_SOURCE_USER = 2;
	
	/** Flag for adding a webhook to the system fired by the admin */
	public static final int WEBHOOK_SOURCE_ADMIN = 4;
	
	/** Flag for adding a webhook to the system fired by the API */
	public static final int WEBHOOK_SOURCE_API = 8;
	
	/** Enum of merge field types - see: <a href="http://www.mailchimp.com/api/rtfm/listmergevaradd.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmergevaradd.func.php</a> */
	public enum MergeFieldType {
		email, text, number, radio, dropdown, date, address, phone, url, imageurl
	}

	/** Enum of interest group types */
	public enum InterestGroupType {
		checkbox, radio, select
	}

	/** Enum of user list subscribe statuses */
	public enum SubscribeStatus {
		subscribed, unsubscribed, cleaned
	}

	/** Enum of email types */
	public enum EmailType {
		html, text, mobile
	}

	/**
	 * Enum for campaign types
	 */
	public enum CampaignType {
		regular, plaintext, absplit, rss, trans, auto
	}

	/**
	 * Enum for campaign status
	 */
	public enum CampaignStatus {
		sent, save, paused, schedule, sending
	}

	// QUICK NOTE: if you are looking at this in relation to the MC API doc, Java date format strings and PHP date format
	// strings are NOT the same.  See this URL for the Java version (well, Android version, but it's equivalent to standard Java version):
	// http://developer.android.com/reference/java/text/SimpleDateFormat.html
	public static final SimpleDateFormat TIME_FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
}
