package rsg.mailchimp.api;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import rsg.mailchimp.R;
import android.content.Context;

/**
 * This is the main class for doing operations with the MailChimp API.
 * 
 * @author ericmuntz
 *
 */
@SuppressWarnings("unchecked")
public class MailChimpApi {

	private static final MessageFormat API_URL = new MessageFormat("https://{0}.api.mailchimp.com/1.2/");
	private static final String DEFAULT_DATA_CENTER = "us1";
	
	protected String apiKey;
	private String endpointUrl;
	
	/**
	 * Default constructor will look for the API key in the Resources file with:
	 * R.strings.mc_api_key
	 */
	public MailChimpApi(Context ctx) {
		this(ctx.getResources().getText(R.string.mc_api_key));
	}
	
	/**
	 * Constructor that takes a specific apikey.
	 * @param apiKey
	 */
	public MailChimpApi(CharSequence apiKey) {
		this.apiKey = apiKey.toString();
		this.endpointUrl = API_URL.format(new Object[] { parseDataCenter(this.apiKey) });
	}

	/**
	 * Utility method to convert an XMLRPCException into an exception with more information from the API
	 * @param e the exception thrown by the XMLRPCClient
	 * @return an instance of MailChimpApiException with more pertinent information in it
	 */
	protected MailChimpApiException buildMailChimpException(XMLRPCException e) {
		// TODO: parse out the resulting error code and return something more hu-man readable
		return new MailChimpApiException("API Call failed, due to '" + e.getMessage() + "'", e);
	}
	
	protected <T extends RPCStructConverter> List<T> callListMethod(Class<T> clazz, String methodName, Object... params) throws MailChimpApiException {
		// call the method
		Object callResult = callMethod(methodName, params);
		if (callResult instanceof Object[]) {
			ArrayList<T> retVal = Utils.extractObjectsFromList(clazz, (Object[]) callResult);
			return retVal;
		} else if (callResult instanceof Map) {
			Map struct = (Map) callResult;
			Set<Map.Entry<String, Object>> entries = struct.entrySet();
			ArrayList<T> retVal = new ArrayList<T>(entries.size());			
			
			for (Map.Entry<String, Object> entry : entries) {				
				T newInst; 
				try {
					newInst = clazz.newInstance();
				} catch (IllegalAccessException e) {
					throw new MailChimpApiException("Couldn't create instance of class (" + clazz.getName() + ") make sure it is publically accessible");
				} catch (InstantiationException e) {
					throw new MailChimpApiException("Couldn't create instance of class (" + clazz.getName() + ") make sure it has a zero-args constructor");
				}
				newInst.populateFromRPCStruct(entry.getKey().toString(), (Map) entry.getValue());
				retVal.add(newInst);
			}
			
			return retVal;
		} else {
			return new ArrayList<T>(0);
		}
	}

	/**
	 * Convenience method to handle calling the XMLRPC library and throwing the MailChimp exception in case
	 * there was an XMLRPCException
	 * @param methodName the name of the method to call
	 * @param params parameters to pass, if any
	 * @return
	 * @throws MailChimpApiException
	 */
	protected Object callMethod(String methodName, Object... params) throws MailChimpApiException {
		try {
			// build the paramters to send and copy the input params, if necessary
			Object[] parameters = new Object[params == null ? 1 : params.length + 1];
			if (params != null && params.length > 0) {				
				System.arraycopy(params, 0, parameters, 1, params.length);
			}
			
			// add the api key
			parameters[0] = apiKey;
			
			// call it
			return getClient().callEx(methodName, parameters);
		} catch (XMLRPCException e) {
			throw buildMailChimpException(e);
		}
	}

	/**
	 * Simple utility method to get an instance of XMLRPCClient with the URL set
	 * @return
	 */
	protected XMLRPCClient getClient() {
		XMLRPCClient client = new XMLRPCClient(this.endpointUrl);
		return client;
	}
	
	
	/**
	 * Utility method to parse the data center out of the API key.  The apikey has the datacenter after
	 * the last dash in the key
	 * @param apiKey
	 * @return the datacenter value
	 */
	private static final String parseDataCenter(String apiKey) {
		String dataCenter = DEFAULT_DATA_CENTER;
		int index = apiKey.lastIndexOf('-');
		// add the datacenter
		if (index > 0) {
			dataCenter = apiKey.substring(index + 1);
		}
		return dataCenter;
	}
	
	
	
	
}
