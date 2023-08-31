package com.bkumpar.orange.entities;

/**
 * Generic container for entity field
 * @author bkumpar
 *
 */
public class XbrlNodeLeaf {

	private Object value;
	private Metadata metadata;
	
	public XbrlNodeLeaf(Object value, Metadata metadata)
	{
		this.value = value;
		this.metadata = metadata;
	}
	
	public Object getValue()
	{
		return this.value;
	}
	
	public Metadata getMetadata()
	{
		return this.metadata;
	}
	

}
