package com.bkumpar.orange.formatters;

/**
 * Formats value of type String as XML
 * @author bkumpar
 *
 */
public class Varchar2Formatter  implements IFormatter {

	public static final Integer TYPE_INDEX = 0;
	public static final Integer LENGTH_INDEX = 1;
	public static final Integer UNIT_INDEX = 3;
	public static final Integer FORMAT_INDEX = 7;
	
	@Override
	public String format(Object value,String[] params, String fieldName, boolean isPrimaryKey) throws Exception {

		if(isDataValid(value, params)) {
			String result = isPrimaryKey ? "<PrimaryKey " : "<Field ";
			result += String.format("Name=\"%s\" ", fieldName);
			result += String.format("Type=\"%s\" ", params[TYPE_INDEX]);
			result += String.format("Length=\"%s %s\" ",params[LENGTH_INDEX],params[UNIT_INDEX]);
			result += (params[FORMAT_INDEX] != "") ? String.format("Format=\"%s\" >",params[FORMAT_INDEX]) : " >";
			result += value.toString();
			result += isPrimaryKey ? "</PrimaryKey> \n" : "</Field> \n";
			return result;
		} else {
			throw new Exception(String.format("Invalid %s: %s ", params[TYPE_INDEX], value.toString()));
		}		
	}

	@Override
	public boolean isDataValid(Object value, String[] params) {
		return (value instanceof String); 
		// and check size of string in BYTES or CHARS!!!
	}



}
