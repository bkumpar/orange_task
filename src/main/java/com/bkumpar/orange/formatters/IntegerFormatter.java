package com.bkumpar.orange.formatters;

/**
 * Formats value of type Integer as XML
 * @author bkumpar
 *
 */
public class IntegerFormatter implements IFormatter {

	public static final Integer TYPE_INDEX = 0;
	public static final Integer LENGTH_INDEX = 1;

	@Override
	public  String format(Object value,String[] params, String fieldName, boolean isPrimaryKey) throws Exception {

		if(isDataValid(value, params)) {
			String result = isPrimaryKey ? "<PrimaryKey " : "<Field ";
			result += String.format("Name=\"%s\" ", fieldName);
			result += String.format("Type=\"%s\" ",params[TYPE_INDEX]);
			result += String.format("Length=\"%s\" ",params[LENGTH_INDEX]);
			result += value.toString();
			result += isPrimaryKey ? "</PrimaryKey> \n" : "</Field> \n";
			return result;
		} else {
			throw new Exception(String.format("Invalid %s: %s ", params[TYPE_INDEX], value.toString()));
		}		
	}

	@Override
	public boolean isDataValid(Object value, String[] params) {
		return ((value instanceof Integer));
	}

}
