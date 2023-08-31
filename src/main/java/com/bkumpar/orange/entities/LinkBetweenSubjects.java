package com.bkumpar.orange.entities;

/**
 * Keeps informations about entity LINK BETWEEN SUBJECTS
 * @author bkumpar
 *
 */
public class LinkBetweenSubjects extends Entity {

	private XbrlNodeLeaf progSoggPrim;
	private XbrlNodeLeaf progSoggSec;
	private XbrlNodeLeaf typeLinkPeoplePeople;
	private XbrlNodeLeaf description;

	public LinkBetweenSubjects(XbrlNodeLeaf progSoggPrim
							, XbrlNodeLeaf progSoggSec
							, XbrlNodeLeaf typeLinkPeoplePeople
							, XbrlNodeLeaf description)
	{
		this.progSoggPrim = progSoggPrim;
		this.progSoggSec = progSoggSec;
		this.typeLinkPeoplePeople = typeLinkPeoplePeople;
		this.description = description;
	}

	public XbrlNodeLeaf getProgSoggPrim() {
		return progSoggPrim;
	}

	public XbrlNodeLeaf getProgSoggSec() {
		return progSoggSec;
	}

	public XbrlNodeLeaf getTypeLinkPeoplePeople() {
		return typeLinkPeoplePeople;
	}

	public XbrlNodeLeaf getDescription() {
		return description;
	}
}
