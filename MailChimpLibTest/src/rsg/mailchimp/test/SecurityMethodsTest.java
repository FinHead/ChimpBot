package rsg.mailchimp.test;

import java.util.List;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.SecurityMethods;
import rsg.mailchimp.api.data.ApiKeyInfo;
import android.test.AndroidTestCase;
import android.util.Log;

public class SecurityMethodsTest extends AndroidTestCase {

	private SecurityMethods api;

	public void setUp() {
		api = new SecurityMethods(TestConstants.API_KEY);
	}
	
	public void testKeyOperations() {
		
		try {
			String username = "";
			String password = "";//TODO: figure out how to get these values in here, but not sharing them all over the place.
			
			// create a new key
			String newKey = api.apikeyAdd(username, password);
			assertNotNull(newKey);
			
			// list keys, make sure it's in there
			List<ApiKeyInfo> keys = api.getApiKeys(username, password, false);
			assertNotNull(keys);
			assertTrue(keys.size() > 0);
			boolean found = false;
			for (ApiKeyInfo key : keys) {
				assertNotNull(key.apikey);
				assertNotNull(key.createdAt);
				if (key.apikey.equals(newKey)) {
					found = true;
				}
			}
			assertTrue(found);
			
			// expire the key
			assertTrue(api.apiKeyExpire(username, password, newKey));
			
			// list 'em again, make sure it's expired
			// list keys, make sure it's in there
			keys = api.getApiKeys(username, password, true);
			found = false;
			for (ApiKeyInfo key : keys) {
				assertNotNull(key.apikey);
				assertNotNull(key.createdAt);
				if (key.apikey.equals(newKey)) {
					found = true;
					assertNotNull(key.expiredAt);
				}
			}
			assertTrue(found);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception: " + e.getMessage());
			assertTrue(false);
		}
		
	}
	
}
