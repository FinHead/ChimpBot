package rsg.mailchimp.api.campaigns;

import java.util.Map;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class ECommOrders implements RPCStructConverter {

	public String storeId;
	public String storeName;
	public String orderId;
	public String email;
	public Double orderTotal;
	public Double taxTotal;
	public Double shipTotal;
	public String orderDate;
	public OrderDetails[] lines;
	
	public class OrderDetails implements RPCStructConverter {
		public String product;
		public String category;
		public String quantity;
		public Double itemCost;
		
		@SuppressWarnings("unchecked")
		public void populateFromRPCStruct(String key, Map struct)
				throws MailChimpApiException {
			Utils.populateObjectFromRPCStruct(this, struct, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
		Utils.populateObjectFromRPCStruct(this, struct, true);
		
		Object linesObj = struct.get("lines");
		if (linesObj instanceof Object[]) {
			Object[] lines = (Object[]) linesObj;
			this.lines = new OrderDetails[lines.length];
			int i = 0;
			for (Object line : lines) {
				this.lines[i] = new OrderDetails();
				this.lines[i].populateFromRPCStruct(null, (Map) line);
				i++;
			}
		}
	}

}
