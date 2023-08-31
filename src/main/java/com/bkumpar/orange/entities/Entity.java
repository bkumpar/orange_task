package com.bkumpar.orange.entities;

/**
 * Base class for all entities. Keeps useful common information(s)
 * @author bkumpar
 *
 */
public class Entity {
	
	protected boolean processed;
	
	public boolean getProcessed()
	{
		return this.processed;
	}
	public void setProcessed(boolean value)
	{
		this.processed = value;
	}
}
