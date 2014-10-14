package org.demis.familh.gedcom;

import org.demis.familh.gedcom.model.FamilyTree;

public interface GEDCOMHandler {

    public void handle(GEDCOMTuple tuple);

    public FamilyTree getFamilyTree();

}
