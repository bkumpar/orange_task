package com.bkumpar.orange.entities;


/**
 * Immutable class holds metadata
 * @author bkumpar
 *
 */
public class Metadata {

	private String name; 
	private String format;
	private boolean isPrimaryKey;
	
	public Metadata(String name, String format, boolean isPrimaryKey ) {
		this.name = name;
		this.isPrimaryKey = isPrimaryKey; 
		this.format = format;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getFormat(){
		return this.format;
	}

	public boolean isPrimaryKey() {
		return this.isPrimaryKey;
	}
}
