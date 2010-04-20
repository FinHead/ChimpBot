package rsg.mailchimp.api.lists;

import java.util.Date;
import java.util.Hashtable;

import org.xmlrpc.android.XMLRPCSerializable;

import rsg.mailchimp.api.Constants;

/**
 * Utility class for building up a list of merge fields.  This has some utility to handle some of the speficic types 
 * like dates and adddresses.  
 * 
 * @author ericmuntz
 *
 */
public class MergeFieldListUtil extends Hashtable<Object, Object> implements XMLRPCSerializable {

	private static final long serialVersionUID = 2340843397084407707L;
	
	/**
	 * Add an interest, calling this multiple times will handle properly appending interests to the list
	 * @param interest
	 */
	public void addInterest(String interest) {
		String interests = (String) get("INTERESTS");
		if (interests == null) {
			interests = "";
		} else {
			interests += ",";
			remove("INTERESTS");
		}
		interests += interest.replaceAll(",", "\\,");// must escape commas with \
		put("INTERESTS", interests);
	}
	
	/**
	 * Method to get the interests.  
	 * @return a non-null array of the interests, if there were none set, this will return a 0-length array
	 */
	public String[] getInterests() {
		String[] retVal;
		Object obj = get("INTERESTS");
		if (obj != null) {
			String interestsStr = (String) obj;
			String[] interests = interestsStr.split("(?<!\\\\),"); // comma's are separated by a \, so we use negative lookbehind to find all comma's not preceeded by a backslash
			retVal = new String[interests.length];
			for (int i = 0; i < retVal.length; i++) {
				retVal[i] = interests[i].trim().replaceAll("\\\\,", ",");
			}
		} else {
			retVal = new String[0];
		}
		return retVal;
	}
	
	/**
	 * Adds an Opt-In IP address, NOTE that this will be validated!
	 * @param ip
	 */
	public void addOptInIp(String ip) {
		put("OPTINIP", ip);
	}
	
	/**
	 * Adds an adddress field with the specific key
	 * @param key
	 * @param address1
	 * @param address2
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public void addAddressField(String key, String address1, String address2, String city, String state, String zip, String country) {
		Hashtable<String, String> addressVals = new Hashtable<String, String>();
		if (address1 != null) addressVals.put("addr1", address1);
		if (address2 != null) addressVals.put("addr21", address1);
		if (city != null) addressVals.put("city", address1);
		if (state != null) addressVals.put("state", address1);
		if (zip != null) addressVals.put("zip", address1);
		if (country != null) addressVals.put("country", address1);
		put(key, addressVals);
	}
	
	/**
	 * This will make sure the date is formatted properly, this date should be in GMT
	 * @param key
	 * @param date
	 */
	public void addDateField(String key, Date date) {
		put(key, Constants.TIME_FMT.format(date));
	}
	
	/**
	 * Adds the number field properly
	 * @param key
	 * @param number
	 */
	public void addNumberField(String key, Number number) {
		put(key, number);
	}
	
	/**
	 * Adds a field of any type.  If you are trying to add a complex object here, make sure it implements
	 * XMLRPCSerializable or is a primitive type
	 * @param key
	 * @param value
	 */
	public void addField(String key, Object value) {
		put(key, value);
	}
	
	/**
	 * Implements XMLRPCSerializable
	 * @return the serialized version of everything built-up as an XMLRPC struct
	 */
	public Object getSerializable() {
		return this;
	}

	/**
	 * Convenience to add an email address
	 * @param string
	 */
	public void addEmail(String string) {
		put("EMAIL", string);
	}

	
}
