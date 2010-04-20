package rsg.mailchimp.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xmlrpc.android.XMLRPCException;

import rsg.mailchimp.api.data.ApiKeyInfo;

import android.content.Context;

/**
 * Provides support for the security methods.
 * <br/>See <a href="http://www.mailchimp.com/api/rtfm/" target="_new">http://www.mailchimp.com/api/rtfm/</a>
 * @author ericmuntz
 */
public class SecurityMethods extends MailChimpApi {

	public SecurityMethods(CharSequence apiKey) {
		super(apiKey);
	}

	public SecurityMethods(Context ctx) {
		super(ctx);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/apikeyadd.func.php" target="_new">http://www.mailchimp.com/api/rtfm/apikeyadd.func.php</a>
	 * <br/>
	 * Adds a new API Key to the account
	 * @param username
	 * @param password
	 * @return the new API key to be used
	 * @throws MailChimpApiException
	 */
	public String apikeyAdd(String username, String password) throws MailChimpApiException {
		return (String) callMethod("apikeyAdd", username, password, apiKey);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/apikeyexpire.func.php" target="_new">http://www.mailchimp.com/api/rtfm/apikeyexpire.func.php</a>
	 * <br/>
	 * Expires an API key
	 * @param username
	 * @param password
	 * @param key - the key to expire, if null it'll use the key this instance was created with.
	 * @return true if successful
	 * @throws MailChimpApiException
	 */
	public boolean apiKeyExpire(String username, String password, String key) throws MailChimpApiException {
		return (Boolean) callMethod("apikeyExpire", username, password, key == null ? apiKey : key);
	}
	
	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/apikeys.func.php" target="_new">http://www.mailchimp.com/api/rtfm/apikeys.func.php</a>
	 * <br/>
	 * Gets the list of ApiKeyInfo for the account specified by username and password.  Note that this method requires 
	 * username and password and does not at all use the API key this instance was created with
	 * @param username
	 * @param password
	 * @param expiredOnly it true, will send only expired keys.  defaults to false if null
	 * @return
	 * @throws MailChimpApiException
	 */
	@SuppressWarnings("unchecked")
	public List<ApiKeyInfo> getApiKeys(String username, String password, Boolean expiredOnly) throws MailChimpApiException {
		Object callResult = callMethod("apikeys", username, password, apiKey, expiredOnly == null ? false : expiredOnly);
		if (callResult instanceof Object[]) {
			Object[] results = (Object[]) callResult;
			ArrayList<ApiKeyInfo> list = new ArrayList<ApiKeyInfo>(results.length);
			for (Object result : results) {
				Map struct = (Map) result;
				ApiKeyInfo info = new ApiKeyInfo();
				info.populateFromRPCStruct(null, struct);
				list.add(info);
			}
			return list;
		} else {
			return new ArrayList<ApiKeyInfo>(0);
		}
	}
	
	/**
	 * Overrides callMethod from MailChimpApi because these methods hate us and require the apiKey parameter to be all over the place
	 */
	protected Object callMethod(String methodName, Object... params) throws MailChimpApiException {
		try {
			// call it
			return getClient().callEx(methodName, params);
		} catch (XMLRPCException e) {
			throw buildMailChimpException(e);
		}
	}

}
