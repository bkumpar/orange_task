package com.bkumpar.orange.formatters;

/**
 * Formats value of type Double as XML
 * @author bkumpar
 *
 */
public class DoubleFormatter  implements IFormatter {

	public static final Integer TYPE_INDEX = 0;
	public static final Integer LENGTH_INDEX = 1;
	public static final Integer PRECISION_INDEX = 5;

	@Override
	public  String format(Object value, String[] params, String fieldName, boolean isPrimaryKey) throws Exception {

		if(isDataValid(value, params)) {
			String result = isPrimaryKey ? "<PrimaryKey " : "<Field ";
			result += String.format("Type=\"%s\"",params[TYPE_INDEX]);
			result += String.format("Name=\"%s\"", fieldName);
			result += String.format("Length=\"%s\" ",params[LENGTH_INDEX]);
			result += (params[PRECISION_INDEX]!=null) ? String.format("Precision=\"%s\" >",params[PRECISION_INDEX]) : ">";
			result += value.toString();
			result += isPrimaryKey ? "</PrimaryKey> " : "</Field> ";
			return result;
		} else {
			throw new Exception(String.format("Invalid %s: %s ", params[TYPE_INDEX], value.toString()));
		}		
	}


	@Override
	public boolean isDataValid(Object value,String[] params) {
		return (value instanceof Double);
	}

}
