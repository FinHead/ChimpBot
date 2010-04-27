package rsg.mailchimp.test;

import java.util.List;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.campaigns.CampaignMethods;
import rsg.mailchimp.api.campaigns.EmailAIMStats;
import rsg.mailchimp.api.campaigns.EmailClickCounts;
import rsg.mailchimp.api.campaigns.EmailOpenCounts;
import android.test.AndroidTestCase;
import android.util.Log;

public class CampaignAIMStatsTests extends AndroidTestCase {

	private CampaignMethods api;

	public void setUp() {
		api = new CampaignMethods(TestConstants.API_KEY);
	}
	
	public void testClickDetailAIM() {
		try {
			List<EmailClickCounts> clickCounts = api.campaignClickDetailAIM(TestConstants.TEST_CAMPAIGN_ID, TestConstants.AIM_URL_WITH_CLICK_DETAILS);
			assertNotNull(clickCounts);
			assertTrue(clickCounts.size() > 0);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testEmailStatsAIM() {
		try {
			List<EmailAIMStats> list = api.campaignEmailStatsAIMAll(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(list);
			assertTrue(list.size() > 0);
			assertNotNull(list);
			assertTrue(list.size() > 0);
			for (EmailAIMStats stat : list) {
				assertNotNull(stat.email);
				assertNotNull(stat.stats);
				if (!"eric@keonesoftware.com".equals(stat.email)) {
					assertTrue(stat.stats.size() > 0);
				} else {
					assertEquals(stat.stats.size(), 0);
				}
			}

			list = api.campaignEmailStatsAIMAll(TestConstants.TEST_CAMPAIGN_ID, TestConstants.TEST_EMAIL_ADDY);
			for (EmailAIMStats stat : list) {
				assertEquals(TestConstants.TEST_EMAIL_ADDY, stat.email);
				assertNotNull(stat.stats);
				assertTrue(stat.stats.size() > 0);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "testEmailStatsAIM, Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testNotOpenedAIM() {
		try {
			List<String> unopened = api.campaignNotOpenedAIM(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(unopened);
			assertTrue(unopened.size() > 0);
			assertTrue(unopened.contains(TestConstants.AIM_EMAIL_ADDY_WHO_DIDNT_OPEN));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testOpenedAIM() {
		try {
			List<EmailOpenCounts> list = api.campaignOpenedAIM(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(list);
			assertTrue(list.size() > 0);
			for (EmailOpenCounts counts : list) {
				assertNotNull(counts.email);
				assertNotNull(counts.openCount);
				assertTrue(counts.openCount > 0);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
}
