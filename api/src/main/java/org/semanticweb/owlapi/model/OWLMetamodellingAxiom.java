package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.model.OWLLogicalAxiom;

public interface OWLMetamodellingAxiom extends OWLLogicalAxiom {
	
	/**
     * Gets the ModelClass in this axiom
     * @return The class expression that represents the Model in this axiom.
     */
    OWLClassExpression getModelClass();

    /**
     * Gets the Metamodel in this axiom.
     * @return The individual that represents the Metamodel in this axiom.
     */
    OWLIndividual getMetamodelIndividual();


    /**
     * @return <code>true</code> if this axiom is a GCI, other wise <code>false</code>.
     */
    boolean isGCI();

    @Override
    OWLMetamodellingAxiom getAxiomWithoutAnnotations();
    
}
