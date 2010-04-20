package rsg.mailchimp.test;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.campaigns.CampaignAdvice;
import rsg.mailchimp.api.campaigns.CampaignContent;
import rsg.mailchimp.api.campaigns.CampaignDetails;
import rsg.mailchimp.api.campaigns.CampaignListFilters;
import rsg.mailchimp.api.campaigns.CampaignMethods;
import rsg.mailchimp.api.campaigns.CampaignOptions;
import rsg.mailchimp.api.campaigns.CampaignStats;
import rsg.mailchimp.api.campaigns.EepUrlStats;
import rsg.mailchimp.api.campaigns.EmailDomainPerformance;
import rsg.mailchimp.api.campaigns.GeoOpens;
import rsg.mailchimp.api.campaigns.SharedReportDetails;
import rsg.mailchimp.api.campaigns.TemplateDetails;
import rsg.mailchimp.api.campaigns.URLClickStats;
import rsg.mailchimp.api.data.FolderInfo;
import android.test.AndroidTestCase;
import android.util.Log;

public class CampaignMethodsTest extends AndroidTestCase {

	private CampaignMethods api;

	public void setUp() {
		api = new CampaignMethods(TestConstants.API_KEY);
	}

	public void testPlainCampaignCreate() {
		try {
			CampaignContent content = new CampaignContent();
			content.text = "this is a plain-text campaign";
			content.html = "<div>this is in a div, what?</div>";
			CampaignOptions options = new CampaignOptions();
			options.listId =TestConstants.TEST_LIST_ID;
			options.subject = "testing";
			options.fromEmail = "freddie@mailchimp.com";
			options.fromName = "Freddie TheChimp";
			options.toEmail = "Fans of Freddie";
			String id = api
					.campaignCreate(Constants.CampaignType.regular,
							options, content);
			assertNotNull(id);
			
			// also test the get campaign functionality
			content = api.campaignContent(id);
			assertNotNull(content);
			assertNotNull(content.text);
			assertNotNull(content.html);
			
			// test sending a test
			assertTrue(api.campaignSendTest(id, new String[] {TestConstants.TEST_OWNER_EMAIL_ADDY, TestConstants.TEST_EMAIL_ADDY}));
			assertTrue(api.campiagnSendTest(id, new String[] {TestConstants.TEST_OWNER_EMAIL_ADDY}, "html"));
			
			// now delete
			assertTrue(api.campaignDelete(id));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Execption: " + e.getMessage());
			assertTrue(false);
		}
	}

	public void testFolderList() {
		try {
			// create a folder
			Integer id = api.createFolder("testing" + randomString());
			assertNotNull(id);
			
			// now list 'em and they'll be non-empty
			List<FolderInfo> folders = api.campaignFolders();
			assertNotNull(folders);
			assertTrue(folders.size() > 0);
			for (FolderInfo folder : folders) {
				assertNotNull(folder.folderId);
				assertNotNull(folder.name);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}

	/** quickl utility to create a psudeorandom string */
	private String randomString() {
		Random rand = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			buf.append(Integer.toHexString(rand.nextInt(15)));
		}
		return buf.toString();
	}

	public void testRSSCampaignActions() {
		try {
			// test creating an RSS Campaign
			CampaignContent content = new CampaignContent();
			content.text = "*|RSS:POSTS_TEXT|* \n *|UNSUB|* \n *|REWARDS|*";
			content.html = "<div>*|RSS:POSTS_HTML|*</div><p><a href=\"*|UNSUB|*\">Remove me from this list</a></p><p>*|REWARDS|*</p>";
			CampaignOptions options = new CampaignOptions();
			options.listId = TestConstants.TEST_LIST_ID;
			options.subject = "testing";
			options.fromEmail = TestConstants.TEST_OWNER_EMAIL_ADDY;
			options.fromName = "Freddie TheChimp";
			options.toEmail = "Fans of Freddie";
			Hashtable<String, String> rssOpts = new Hashtable<String, String>();
			rssOpts.put("url", "http://www.mailchimp.com/blog/feed/");
			rssOpts.put("schedule", "monthly");

			String id = api.campaignCreate(
					Constants.CampaignType.rss, options, content,
					null, rssOpts);
			assertNotNull(id);

			// test starting it
			assertTrue(api.campaignSendNow(id));

			// now test pausing
			assertTrue(api.campaignPause(id));
			assertTrue(api.campaignResume(id));

			// and deleting
			assertTrue(api.campaignDelete(id));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}

	}
	
	public void testShareReport() {
		try {
			SharedReportDetails details = api.campaignShareReport(TestConstants.TEST_CAMPAIGN_ID, TestConstants.TEST_OWNER_EMAIL_ADDY, true, "chimperrific");
			assertNotNull(details);
			assertEquals(details.password, "chimperrific");
			assertNotNull(details.title);
			assertNotNull(details.secureUrl);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testGetTemplates() {
		try {
			List<TemplateDetails> templates = api.campiagnTemplates();
			assertNotNull(templates);
			assertTrue(templates.size() > 0);
			
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}

	public void testCampaignListLimit() {
		try {
			int limit = 1;
			CampaignListFilters filters = new CampaignListFilters().setLimit(limit);
			List<CampaignDetails> details = api.listCampaigns(filters);
			assertNotNull(details);
			assertEquals(limit, details.size());

		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testCampaignListStatus() {
		try {
			int limit = 1;
			CampaignListFilters filters = new CampaignListFilters().setLimit(limit).setStatus(Constants.CampaignStatus.sent);
			List<CampaignDetails> details = api.listCampaigns(filters);
			assertNotNull(details);
			assertEquals(limit, details.size());
			assertEquals(details.get(0).status, Constants.CampaignStatus.sent);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testCampaignListType() {
		// this should return 0!
		try {
			CampaignListFilters filters = new CampaignListFilters().setType(Constants.CampaignType.absplit);
			List<CampaignDetails> details = api.listCampaigns(filters);
			assertNotNull(details);
			assertEquals(0, details.size());
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	
	public void testCampaignAdvice() {
	
		try {
			List<CampaignAdvice> advice = api.campaignAdvice(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(advice);
			assertTrue(advice.size() > 0);
			for (CampaignAdvice vice : advice) {
				assertNotNull(vice.message);
				assertNotNull(vice.type);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testCampaignClickStats() {
		try {
			List<URLClickStats> clickStats = api.campaignClickStats(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(clickStats);
			assertTrue(clickStats.size() > 0);
			for (URLClickStats stat : clickStats) {
				assertNotNull(stat.url);
				assertTrue(stat.clicks >= stat.unique);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testEepUrlStats() {
		try {
			List<EepUrlStats> stats = api.campaignEepUrlStats(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(stats);
			assertEquals(stats.size(), 0);// TODO: Get EepUrl Stats setup for the test campaign
//			assertTrue(stats.size() > 0);
//			for (EepUrlStats stat : stats) {
//				assertNotNull(stat.service);
//				assertTrue(stat.retweets > 0);
//				assertNotNull(stat.firstTweet);
//				assertNotNull(stat.firstRetweet);
//				assertTrue(stat.firstTweet.after(stat.firstRetweet));
//			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testEmailDomainPerformance() {
		try {
			List<EmailDomainPerformance> perfs = api.campaignEmailDomainPerformance(TestConstants.TEST_CAMPAIGN_ID) ;
			assertNotNull(perfs);
			assertTrue(perfs.size() > 0);
			for (EmailDomainPerformance perf : perfs) {
				assertNotNull(perf.domain);
				assertTrue(perf.emails > 0);
				if (perf.domain.equals(TestConstants.TEST_DOMAIN)) {					
					assertTrue(perf.opens > 0);
					assertTrue(perf.delivered > 0);
				}
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testGeoOpens() {
		try {
			List<GeoOpens> opens = api.campaignGeoOpens(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(opens);
			assertTrue(opens.size() > 0);
			for (GeoOpens open : opens) {
				assertNotNull(open.code);
				assertNotNull(open.name);
				assertTrue(open.opens > 0);
				assertTrue(open.regionDetail);
			}
			
			opens = api.campaignGeoOpens(TestConstants.TEST_CAMPAIGN_ID, "US");
			assertNotNull(opens);
			assertTrue(opens.size() > 0);
			for (GeoOpens open : opens) {
				assertNotNull(open.code);
				assertNotNull(open.name);
				assertTrue(open.opens > 0);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}

	public void testHardBounces() {
		try {
			List<String> bounces = api.campaignHardBounces(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(bounces);
			assertTrue(bounces.size() > 0);
			for (String s : bounces) {
				assertNotNull(s);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testSoftBounces() {
		try {
			List<String> bounces = api.campaignSoftBounces(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(bounces);
			assertTrue(bounces.size() > 0);
			for (String s : bounces) {
				assertNotNull(s);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testUnsubscribes() {
		try {
			List<String> unsubs = api.campaignUnsubscribes(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(unsubs);
			assertTrue(unsubs.size() > 0);
			for (String s : unsubs) {
				assertNotNull(s);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testCampaignStats() {
		try {
			CampaignStats stats = api.campaignStats(TestConstants.TEST_CAMPAIGN_ID);
			assertNotNull(stats);
			assertEquals(stats.syntaxErrors, new Integer(0));
			assertEquals(stats.hardBounces, new Integer(1));
			assertEquals(stats.softBounces, new Integer(0));
			assertEquals(stats.unsubscribes, new Integer(1));
			assertEquals(stats.forwards, new Integer(1));
			assertEquals(stats.abuseReports, new Integer(0));
			assertEquals(stats.opens, new Integer(1));
			assertEquals(stats.clicks, new Integer(3));
			assertEquals(stats.uniqueClicks, new Integer(3));
			assertEquals(stats.usersWhoClicked, new Integer(1));
			assertNotNull(stats.lastClick);
			assertNotNull(stats.lastOpen);
			assertEquals(stats.emailsSent, new Integer(5));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	// TODO: get a campaign with analytics so I can test this!
//	public void testCampaignAnalytics() {
//		try {
//			CampaignAnalytics analytics = api.getCampaignAnalytics("3642f8fa86");
//			assertNotNull(analytics);
//		} catch (MailChimpApiException e) {
//			assertTrue(false);
//			Log.e("MailChimp", "Exception: " + e.getMessage());
//		}
//	}
	
	// TODO: get a campaign with EComm orders so I can test that
	
	
	
}
