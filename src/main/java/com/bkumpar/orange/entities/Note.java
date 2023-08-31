package com.bkumpar.orange.entities;

/**
 * Keeps informations about entity NOTE
 * @author bkumpar
 *
 */
public class Note extends Entity {

	private XbrlNodeLeaf progNote;
	private XbrlNodeLeaf typeNote;
	private XbrlNodeLeaf textNote;

	public Note(XbrlNodeLeaf progNote
				, XbrlNodeLeaf typeNote
				, XbrlNodeLeaf textNote )
	{
		this.progNote = progNote;
		this.typeNote = typeNote;
		this.textNote = textNote;
		this.processed = false;
	}
	
	public XbrlNodeLeaf getProgNote() {
		return this.progNote;
	}
	
	public XbrlNodeLeaf getTypeNote() {
		return this.typeNote;
	}
	
	public XbrlNodeLeaf getTextNote(){
		return this.textNote;	
	}


}
