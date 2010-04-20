package rsg.mailchimp.test;

import java.util.ArrayList;
import java.util.List;

import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.campaigns.AbuseReport;
import rsg.mailchimp.api.lists.BatchResults;
import rsg.mailchimp.api.lists.GrowthHistory;
import rsg.mailchimp.api.lists.InterestGroupInfo;
import rsg.mailchimp.api.lists.ListMethods;
import rsg.mailchimp.api.lists.ListDetails;
import rsg.mailchimp.api.lists.MemberInfo;
import rsg.mailchimp.api.lists.MergeFieldInfo;
import rsg.mailchimp.api.lists.MergeFieldListUtil;
import rsg.mailchimp.api.lists.WebHookInfo;
import android.test.AndroidTestCase;
import android.util.Log;

public class ListMethodsTest extends AndroidTestCase {

	private ListMethods api;
	
	public void setUp() {
		api = new ListMethods(TestConstants.API_KEY);
	}
	
	public void testSingleSubscribeMergeTags() {
		try {			
			// subscribe with a few merge tags set and no single opt-in
			MergeFieldListUtil merges = new MergeFieldListUtil();
			merges.addField("FNAME", "Eric");
			merges.addField("LNAME", "Muntz");
			assertTrue(api.listSubscribe(TestConstants.TEST_LIST_ID, TestConstants.TEST_EMAIL_ADDY, merges));
			
			// get the information on this user to validate the merge fields
			MemberInfo info = api.listMemberInfo(TestConstants.TEST_LIST_ID, TestConstants.TEST_EMAIL_ADDY);
			assertNotNull(info);
			assertEquals(info.email, TestConstants.TEST_EMAIL_ADDY);
			
			// unsubscribe
			assertTrue(api.listUnsubscribe(TestConstants.TEST_LIST_ID, TestConstants.TEST_EMAIL_ADDY));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testSingleSubscribe() {
		try {
			
			// subscribe
			assertTrue(api.listSubscribe(TestConstants.TEST_LIST_ID, TestConstants.TEST_EMAIL_ADDY));
			
			// get the information on this user to validate the merge fields
			MemberInfo info = api.listMemberInfo(TestConstants.TEST_LIST_ID, TestConstants.TEST_EMAIL_ADDY);
			assertNotNull(info);
			assertEquals(info.email, TestConstants.TEST_EMAIL_ADDY);
			
			// unsubscribe
			assertTrue(api.listUnsubscribe(TestConstants.TEST_LIST_ID, TestConstants.TEST_EMAIL_ADDY));	
			
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testMemberInfo() {
		try {
			MemberInfo info = api.listMemberInfo(TestConstants.TEST_LIST_ID, TestConstants.TEST_EMAIL_ADDY);
			assertNotNull(info);
			assertEquals(info.status, Constants.SubscribeStatus.subscribed);
			assertTrue(info.memberRating > 0);
			assertEquals(info.mergeFields.get("FNAME"), "Eric");
			assertNotNull(info.lists);
			assertTrue(info.lists.length > 0);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testAbuseReports() {
		try {
			List<AbuseReport> list = api.listAbuseReports(TestConstants.TEST_LIST_ID);
			assertNotNull(list);
			assertTrue(list.size() > 0);
			for (AbuseReport report : list) {
				assertNotNull(report.campaignId);
				assertNotNull(report.email);
				assertNotNull(report.date);
				assertNotNull(report.type);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
		
	}
	
	public void testBatchSubscribe() {
		try {
			ArrayList<MergeFieldListUtil> fields = new ArrayList<MergeFieldListUtil>(10);
			for (int i = 0; i < 10; i++) {
				MergeFieldListUtil merges = new MergeFieldListUtil();
				merges.addEmail(TestConstants.TEST_EMAIL_HEAD + i + "@" + TestConstants.TEST_DOMAIN);
				merges.addField("FNAME", "Eric" + i);
				merges.addField("LNAME", "Muntz" + i);
				fields.add(merges);
			}
			BatchResults results = api.listBatchSubscribe(TestConstants.TEST_LIST_ID, fields);
			assertNotNull(results);
			assertEquals(results.successCount, new Integer(10));
			
			// now batch unsubscribe them
			ArrayList<String> unsubList = new ArrayList<String>(10);
			for (int i = 0; i < 10; i++) {
				unsubList.add(TestConstants.TEST_EMAIL_HEAD + i + "@" + TestConstants.TEST_DOMAIN);
			}
			results = api.listBatchUnsubscribe(TestConstants.TEST_LIST_ID, unsubList, true, false, false);
			assertNotNull(results);
			assertEquals(results.successCount, new Integer(10));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testListGrowthHistory() {
		try {
			List<GrowthHistory> history = api.listGrowthHistory(TestConstants.TEST_LIST_ID);
			assertNotNull(history);
			assertTrue(history.size() > 0);
			for (GrowthHistory growth : history) {
				assertNotNull(growth);
				assertNotNull(growth.month);
				assertNotNull(growth.existing);
				assertNotNull(growth.imports);
				assertNotNull(growth.optins);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testInterestGroups() {
		try {
			String testGroupName = "MonkeyingAround";
			
			// add group
			assertTrue(api.listInterestGroupAdd(TestConstants.TEST_LIST_ID, testGroupName));
			
			// make sure it's there
			InterestGroupInfo groupInfo = api.listInterestGroups(TestConstants.TEST_LIST_ID);
			assertNotNull(groupInfo);
			assertNotNull(groupInfo.groups);
			assertTrue(groupInfo.groups.size() > 0);
			assertTrue(groupInfo.groups.contains(testGroupName));
			
			// delete group
			assertTrue(api.listInterestGroupDel(TestConstants.TEST_LIST_ID, testGroupName));
			
			// make sure it's not there
			groupInfo = api.listInterestGroups(TestConstants.TEST_LIST_ID);
			assertNotNull(groupInfo);
			assertNotNull(groupInfo.groups);
			assertTrue(groupInfo.groups.size() > 0);
			assertFalse(groupInfo.groups.contains(testGroupName));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testListsAssignedTo() {
		try {
			List<String> listIds = api.listsForEmail(TestConstants.TEST_EMAIL_ADDY);
			assertNotNull(listIds);
			assertTrue(listIds.size() > 0);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testMemberList() {
		try {
			List<MemberInfo> list = api.listMembers(TestConstants.TEST_LIST_ID);
			assertNotNull(list);
			assertTrue(list.size() > 0);
			for (MemberInfo info : list) {
				assertNotNull(info.email);
				assertNotNull(info.timestamp);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testAddMergeVar() {
		try {
			String testFieldName = "TESTY";
			String testFieldDesc = "Just testing";
			
			// add merge var with default options
			assertTrue(api.listMergeVarAdd(TestConstants.TEST_LIST_ID, testFieldName, testFieldDesc, null));
			
			// make sure it's there and is setup appropriately
			List<MergeFieldInfo> mergeFields = api.listMergeVars(TestConstants.TEST_LIST_ID);
			assertNotNull(mergeFields);
			assertTrue(mergeFields.size() > 0);
			MergeFieldInfo addedField = null;
			for (MergeFieldInfo field : mergeFields) {
				if (field.tag.equals(testFieldName)) {					
					addedField = field;
					break;
				}
			}
			assertNotNull(addedField);
			assertEquals(addedField.name, testFieldDesc);
			
			// now delete it
			assertTrue(api.listMergeVarDel(TestConstants.TEST_LIST_ID, testFieldName));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testWebhooks() {
		try {
			String url = "http://www.google.com";
			assertTrue(api.listWebhookAdd(TestConstants.TEST_LIST_ID, url, Constants.WEBHOOK_ACTION_SUBSCRIBE, null));
			
			List<WebHookInfo> list = api.listWebhooks(TestConstants.TEST_LIST_ID);
			assertNotNull(list);
			assertTrue(list.size() > 0);
			for (WebHookInfo info : list) {
				assertNotNull(info.url);
				assertNotNull(info.actions);
				assertNotNull(info.sources);
			}
			
			assertTrue(api.listWebhookDel(TestConstants.TEST_LIST_ID, url));
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
	public void testLists() {
		try {
			List<ListDetails> lists = api.lists();
			assertNotNull(lists);
			assertTrue(lists.size() > 0);
			for (ListDetails list : lists) {
				assertNotNull(list.id);
				assertNotNull(list.name);
				assertNotNull(list.defaultFromEmail);
				assertNotNull(list.defaultFromName);
				assertNotNull(list.defaultSubject);
				assertNotNull(list.dateCreated);
				assertTrue(list.memberCount > 0);
			}
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
	}
	
}
