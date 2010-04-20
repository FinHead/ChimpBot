package rsg.mailchimp.api.campaigns;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

/**
 * Typesafe structure representing the analytics data returned from the campaign analytics function
 * @author ericmuntz
 *
 */
public class CampaignAnalytics implements RPCStructConverter {

	public Integer visits;
	public Integer pages;
	public Integer newVisits;
	public Integer bounces;
	public Double timeOnSite;
	public Integer goalConversions;
	public Double goalValue;
	public Double revenue;
	public Integer transactions;
	public Integer ecommConversions;
	public GoalConversions[] goals;

	/**
	 * Nested class that represents GoalConversions
	 * @author ericmuntz
	 *
	 */
	public class GoalConversions {
		public String name;
		public Integer conversions;
	}

	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true);

		// build up the goals object, if there is any information on that.
		Object goalsObj = struct.get("goals");
		if (goalsObj != null && goalsObj instanceof Object[]) {
			Object[] goals = (Object[]) goalsObj;
			this.goals = new GoalConversions[goals.length];
			int i = 0;
			for (Object goalObj : goals) {
				Map goalStruct = (Map) goalObj;
				this.goals[i] = new GoalConversions();
				this.goals[i].name = (String) goalStruct.get("name");
				this.goals[i].conversions = ((Number) goalStruct.get("conversions")).intValue();
				i++;
			}
		}
	}
}
