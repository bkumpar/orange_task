package com.bkumpar.orange.formatters;

/**
 * 
 * @author bkumpar
 *
 */
public interface IFormatter {

	
	boolean isDataValid(Object value, String[] params) ;
	
	String format(Object value, String[] params, String fieldName, boolean isPrimaryKey) throws Exception;

}
