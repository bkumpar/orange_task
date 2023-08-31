package com.bkumpar.orange.formatters;

/**
 * Default formatter. If no type specified, we assume that type is CHAR.
 * 
 * @author bkumpar
 *
 */
public class Formatter  implements IFormatter {

	public static final Integer TYPE_INDEX = 0;
	public static final Integer LENGTH_INDEX = 1;
	public static final Integer UNIT_INDEX = 3;
	public static final Integer FORMAT_INDEX = 7;
	private static final String DEFAULT_TYPE = "CHAR";
	@Override
	public String  format(Object value, String[] params, String fieldName, boolean isPrimaryKey) throws Exception {
		if(isDataValid(value, params)) {
			String result = isPrimaryKey ? "<PrimaryKey " : "<Field ";
			result += String.format("Name=\"%s\" ",fieldName);
			result += String.format("Type=\"%s\" ", DEFAULT_TYPE);
			result += String.format("Length=\"%s\" >",params[LENGTH_INDEX]);
			result += value.toString();
			result += isPrimaryKey ? "</PrimaryKey> " : "</Field> ";
			return result;
		} else {
			throw new Exception(String.format("Invalid %s: %s ", DEFAULT_TYPE, value.toString()));
		}		
	}

	@Override
	public boolean isDataValid(Object value, String[] params) {
		return (value instanceof String);
	}


}
