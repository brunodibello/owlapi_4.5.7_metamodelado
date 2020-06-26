package org.semanticweb.owlapi.model;

public interface OWLMetaRuleAxiom extends OWLLogicalAxiom {
	
	/**
     * Gets the property R in this axiom
     * @return The property for the individuals from the metamodelling axiom
     */
	OWLObjectPropertyExpression getPropertyR();

    /**
     * Gets the property S in this axiom
     * @return The property for the elements of the classes from the metamodelling axiom
     */
	OWLObjectPropertyExpression getPropertyS();


    /**
     * @return <code>true</code> if this axiom is a GCI, other wise <code>false</code>.
     */
    boolean isGCI();

    @Override
    OWLMetaRuleAxiom getAxiomWithoutAnnotations();

}
