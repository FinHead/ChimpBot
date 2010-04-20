package rsg.mailchimp.test;

import java.util.List;

import rsg.mailchimp.api.HelperMethods;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.data.AccountDetails;
import rsg.mailchimp.api.data.ChimpChatter;
import android.test.AndroidTestCase;
import android.util.Log;

public class HelperMethodsTest extends AndroidTestCase {

	private HelperMethods api;
	
	public void setUp() {
		api = new HelperMethods(TestConstants.API_KEY);
	}
	
	public void testPing() {
		try {
			String pingRes = api.ping();
			assertNotNull(pingRes);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testChimpChatter() {
		try {
			List<ChimpChatter> chatters = api.chimpChatter();
			assertNotNull(chatters);
			assertTrue(chatters.size() > 0);
			for (ChimpChatter chatter : chatters) {
				assertNotNull(chatter.message);
				assertNotNull(chatter.type);
				switch (chatter.type) {
				case absplit:
					break;
				case inspection:
					break;
				case subscribes:
					break;
				case unsubscribes:
					break;
				case low_credits:
					break;
				case scheduled:
				case sent:
				case best_clicks:
				case best_opens:
				case abuse:
					assertNotNull(chatter.campaignId);
					break;
					default:
						Log.e("MailChimp", "Test doesn't know about ChimpChatter type: " + chatter.type);
						assertTrue(false);
				}
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testAccountDetails() {
		try {
			AccountDetails details = api.getAccountDetails();
			assertNotNull(details);
			
			assertTrue(details.timesLoggedIn > 0);
			assertEquals(details.timezone, TestConstants.ACCOUNT_TIMEZONE);
			assertNotNull(details.memberSince);
			assertEquals(details.username, TestConstants.ACCOUNT_USERNAME);
			
			// contact info
			assertNotNull(details.contact);
			assertEquals(details.contact.fname, TestConstants.ACCOUNT_FNAME);
			assertEquals(details.contact.lname, TestConstants.ACCOUNT_LNAME);
			assertEquals(details.contact.email, TestConstants.ACCOUNT_EMAIL);
			assertEquals(details.contact.address1, TestConstants.ACCOUNT_ADDRESS1);
			assertEquals(details.contact.company, TestConstants.ACCOUNT_COMPANY);
			assertEquals(details.contact.city, TestConstants.ACCOUNT_CITY);
			assertEquals(details.contact.state, TestConstants.ACCOUNT_STATE);
			assertEquals(details.contact.zip, TestConstants.ACCOUNT_ZIP);
			assertEquals(details.contact.country, TestConstants.ACCOUNT_COUNTRY);
			assertEquals(details.contact.url, TestConstants.ACCOUNT_URL);
			
			// TODO: test modules and rewards, once i have an account that actually has them in it
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testInlineCss() {
		try {
			String testhtml = "<html><head><style> #foobar { color: red; } </style></head><body><div id=\"foobar\">asfasdfasdf</div></body></html>";
			String inlined = "<html><head><style> #foobar { color: red; } </style></head><body><div id=\"foobar\" style=\"color: red;\">asfasdfasdf</div></body></html>";
			String result = api.inlineCss(testhtml, false);
			assertEquals(inlined, result);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	
	
}
