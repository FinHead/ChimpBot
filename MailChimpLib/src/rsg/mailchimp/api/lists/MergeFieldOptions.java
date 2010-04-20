package rsg.mailchimp.api.lists;

import java.util.Date;
import java.util.Hashtable;

import rsg.mailchimp.api.Constants;

/**
 * Class to set merge field options when adding a new merge field to a list
 * <br/><a href="http://www.mailchimp.com/api/rtfm/listmergevaradd.func.php" target="_new">http://www.mailchimp.com/api/rtfm/listmergevaradd.func.php</a>
 * @author ericmuntz
 */
public class MergeFieldOptions extends Hashtable<String, Object> {

	private static final long serialVersionUID = -4664327902592046297L;

	public void addFieldType(Constants.MergeFieldType type) {
		put("field_type", type.toString());
	}
	
	public void setRequired(boolean required) {
		put("req", required);
	}
	
	public void setPublic(boolean isPublic) {
		put("public", isPublic);
	}
	
	public void setShow(boolean show) {
		put("show", show);
	}
	
	public void setDefaultValue(Date val) {
		setDefaultValue(Constants.TIME_FMT.format(val));
	}
	
	public void setDefaultValue(String val) {
		put("default_value", val);
	}
	
	public void setChoices(String[] choices) {
		put("choices", choices);
	}
	
}
