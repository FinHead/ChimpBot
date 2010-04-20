package rsg.mailchimp.api.data;

import java.util.Date;


/**
 * This is called "orders" in the API, seems "Payments" makes more sense.
 * @author ericmuntz
 *
 */
public class AccountPayments extends GenericStructConverter {

	public Integer orderId;
	public String type;
	public Double amount;
	public Date date;
	public Double creditsUser;
	
}
