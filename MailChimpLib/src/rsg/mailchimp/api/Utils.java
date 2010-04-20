package rsg.mailchimp.api;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility functions
 * 
 * @author ericmuntz
 *
 */
public class Utils {

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listwebhookadd.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listwebhookadd.func.php</a>
	 * <br/>
	 * Gets the hash for the API call for adding webhook actions
	 * @param flags bitwise OR list of WEBHOOK_ACTION_XXX constants from Constants
	 * @return
	 */
	public static final Hashtable<String, Boolean> getWebHookActions(int flags) {
		Hashtable<String, Boolean> retVal = new Hashtable<String, Boolean>();
		if ((flags & Constants.WEBHOOK_ACTION_SUBSCRIBE) != 0) retVal.put("subscribe", true);
		else retVal.put("subscribe", false);
		if ((flags & Constants.WEBHOOK_ACTION_UNSUBSCRIBE) != 0) retVal.put("unsubscribe", true);
		else retVal.put("unsubscribe", false);
		if ((flags & Constants.WEBHOOK_ACTION_PROFILE) != 0) retVal.put("profile", true);
		else retVal.put("profile", false);
		if ((flags & Constants.WEBHOOK_ACTION_CLEANED) != 0) retVal.put("cleaned", true);
		else retVal.put("cleaned", false);
		if ((flags & Constants.WEBHOOK_ACTION_UPEMAIL) != 0) retVal.put("upemail", true);
		else retVal.put("upemail", false);
	
		return retVal;
	}

	/**
	 * Wraps method <a href="http://www.mailchimp.com/api/rtfm/listwebhookadd.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listwebhookadd.func.php</a>
	 * <br/>
	 * Gets the hash for the API call for adding webhook sources
	 * @param flags bitwise OR list of WEBHOOK_SOURCE_XXX constants from Constants
	 * @return
	 */
	public static final Hashtable<String, Boolean> getWebHookSources(int flags) {
		Hashtable<String, Boolean> retVal = new Hashtable<String, Boolean>();
		if ((flags & Constants.WEBHOOK_SOURCE_USER) != 0) retVal.put("user", true);
		else retVal.put("user", false);
		if ((flags & Constants.WEBHOOK_SOURCE_ADMIN) != 0) retVal.put("admin", true);
		else retVal.put("admin", false);
		if ((flags & Constants.WEBHOOK_SOURCE_API) != 0) retVal.put("api", true);
		else retVal.put("api", false);
		
		return retVal;
	}

	@SuppressWarnings("unchecked")
	public static void populateObjectFromRPCStruct(final Object obj, final Map vals, boolean skipNoSuchField, String... ignoreFieldNames) throws MailChimpApiException {
		Set<Map.Entry> entries = vals.entrySet();
		Field f;
		Class type = null;
		String key = null, fieldName = null, value = null;
		for (Map.Entry entry : entries) {
			key = entry.getKey().toString();
			fieldName = Utils.translateFieldName(key);
			if (!Utils.ignoreFieldName(ignoreFieldNames, fieldName)) {				
				try {
					f = obj.getClass().getField(Utils.translateFieldName(key));
					type = f.getType();
					if (type == Boolean.class) {
						f.set(obj, new Boolean(entry.getValue().toString()));
					} else if (type == Date.class) {
						value = entry.getValue().toString();
						if (value.trim().length() > 0) {						
							f.set(obj, Constants.TIME_FMT.parse(entry.getValue().toString()));
						}
					} else if (type == Integer.class) {
						// we have problems if an integer is returned as a double, so we'll make sure we cleanly convert it here
						try {
							Number num = (Number) entry.getValue();
							f.set(obj, num.intValue());							
						} catch (ClassCastException e) {
							// try to really really shove the value into it, if this fails, so be it.
							f.set(obj, Integer.parseInt((String) entry.getValue()));
						}
					} else if (type == Double.class) {
						if (entry.getValue().getClass().equals(String.class)) {
							f.set(obj, Double.parseDouble((String) entry.getValue()));
						} else {
							f.set(obj, entry.getValue());
						}
					} else {					
						
						f.set(obj, entry.getValue());
					}
				} catch (SecurityException e) {
					throw new MailChimpApiException("Couldn't translate values to " + obj.getClass() + ", key: " + key + " causing SecurityException, value: " + entry.getValue(), e);
				} catch (NoSuchFieldException e) {
					if (skipNoSuchField) {					
//						Log.e("MailChimp", "Unable to find field named: " + fieldName + " in class " + obj.getClass());
					} else {					
						throw new MailChimpApiException("Couldn't translate values to " + obj.getClass() + ", key: " + key + " field doens't exist: " + fieldName, e);
					}
				} catch (IllegalArgumentException e) {
					throw new MailChimpApiException("Couldn't translate values to " + obj.getClass() + ", key: " + key + " causing IllegalArgumentException, value: " + entry.getValue(), e);
				} catch (IllegalAccessException e) {
					throw new MailChimpApiException("Couldn't translate values to " + obj.getClass() + ", key: " + key + " causing IllegalAccessException, value: " + entry.getValue(), e);
				} catch (ParseException e) {
					throw new MailChimpApiException("Couldn't translate values to " + obj.getClass() + ", key: " + key + " causing parse failure, value that couldn't be parsed: " + entry.getValue(), e);
				} catch (ClassCastException e) {
					throw new MailChimpApiException("Couldn't transalte values to " + obj.getClass() + ", key: " + key + " is of type: " + entry.getValue().getClass() + " and field is of type: " + type);
				}
			}
		}
		
	}

	/**
	 * So lame an "array contains" included in the stdlib
	 * @param ignoreFieldNames
	 * @param fieldName
	 * @return
	 */
	static boolean ignoreFieldName(String[] ignoreFieldNames, String fieldName) {
		for (String ignore : ignoreFieldNames) {
			if (ignore.equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	public static String translateFieldName(String key) {	
		StringBuffer buf = new StringBuffer();
		char c;
		for (int i = 0; i < key.length(); i++) {
			c = key.charAt(i);
			switch (c) {
			case '_':
				buf.append(Character.toUpperCase(key.charAt(i+1)));
				i++;
				break;
				default:
					buf.append(c);
			}
		}
		return buf.toString();
	}

	@SuppressWarnings("unchecked")
	public static final <T extends RPCStructConverter> ArrayList<T> extractObjectsFromList(Class<T> clazz, Object[] callResult) throws MailChimpApiException {
		// mega-ugly fun with typecasting ensues 
		Object[] callResultArray = (Object[]) callResult;
		ArrayList<T> retVal = new ArrayList<T>(callResultArray.length);
		
		// now scroll through the Map[] (why we can't cast to Map[] kills me) and use the callback to build each object
		Map struct = null;
		for (Object resultObj : callResultArray) {
			struct = (Map) resultObj;
			T newInst;
			try {
				newInst = clazz.newInstance();
			} catch (IllegalAccessException e) {
				throw new MailChimpApiException("Couldn't create instance of class (" + clazz.getName() + ") make sure it is publically accessible");
			} catch (InstantiationException e) {
				throw new MailChimpApiException("Couldn't create instance of class (" + clazz.getName() + ") make sure it has a zero-args constructor");
			}
			newInst.populateFromRPCStruct(null, struct);
			retVal.add(newInst);
		}
		return retVal;
	}

	/**
	 * Yay, more lame convenience methods!
	 * @param array
	 * @return
	 */
	public static List<String> convertObjectArrayToString(Object[] array) {
		ArrayList<String> vals = new ArrayList<String>(array.length);
		for (Object o : array) {
			vals.add(o.toString());
		}
		return vals;
	}

}
