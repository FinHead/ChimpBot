package rsg.mailchimp.api.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class AccountDetails implements RPCStructConverter {
	
	public String userId;
	public String username;
	public Date memberSince;
	public Boolean isApproved;
	public Boolean isTrial;
	public String timezone;
	public PlanType planType;
	public Integer emailsLeft;
	public Boolean pendingMonthly;
	public Date firstPayment;
	public Date lastPayment;
	public Integer timesLoggedIn;
	public Date lastLogin;
	public String affiliateLink;
	public List<ModuleInfo> modules;
	public AccountContact contact;
	public List<AccountPayments> payments;
	
	/** typesafe enum for the plan type */
	public enum PlanType {
		monthly, payasyougo, free
	}

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true, "orders", "rewards", "modules", "contact", "planType");
		
		String planTypeStr = (String) struct.get("plan_type");
		planType = PlanType.valueOf(planTypeStr);
		
		// build the contact
		contact = new AccountContact();
		contact.populateFromRPCStruct(null, (Map) struct.get("contact"));
		
		// get the payments, if there are any
		Object ordersObj = struct.get("orders");
		if (ordersObj instanceof Object[]) {
			payments = Utils.extractObjectsFromList(AccountPayments.class, (Object[]) ordersObj);
		}
		
		// TODO: handle referrals and modules, once i've got an account that enables them
	}

}
