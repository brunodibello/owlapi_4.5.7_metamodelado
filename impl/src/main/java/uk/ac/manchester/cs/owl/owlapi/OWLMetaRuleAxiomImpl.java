package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLMetaRuleAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Bruno Di Bello<br>
 * FIng - UdelaR<br>
 * Date: 26-Jun-2020<br><br>
 */
public class OWLMetaRuleAxiomImpl extends OWLClassAxiomImpl implements OWLMetaRuleAxiom {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(OWLMetaRuleAxiomImpl.class.getName());	
	private static final long serialVersionUID = 30403L;

	private final OWLObjectPropertyExpression propertyR;
    private final OWLObjectPropertyExpression propertyS;

    @SuppressWarnings("javadoc")
    public OWLMetaRuleAxiomImpl(OWLObjectPropertyExpression propertyR, OWLObjectPropertyExpression propertyS, Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.propertyR = propertyR;
        this.propertyS = propertyS;
    }
    
    @SuppressWarnings("javadoc")
    public OWLMetaRuleAxiomImpl(OWLObjectPropertyExpression propertyR, OWLObjectPropertyExpression propertyS) {
    	super(null);
        this.propertyR = propertyR;
        this.propertyS = propertyS;
    }

    @Override
    public OWLMetaRuleAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return new OWLMetaRuleAxiomImpl(propertyR, propertyS, mergeAnnos(annotations));
    }

    @Override
    public OWLMetaRuleAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLMetaRuleAxiomImpl(propertyR, propertyS);
    }

    @Override
    public OWLObjectPropertyExpression getPropertyR() {
        return propertyR;
    }

    @Override
    public OWLObjectPropertyExpression getPropertyS() {
        return propertyS;
    }

    @Override
    public boolean isGCI() {
        return propertyR.isAnonymous();
    }

    @Override
	public boolean equals(Object obj) {
    	if (!(obj instanceof OWLMetaRuleAxiom)) {
            return false;
        }
    	if(super.equals(obj)) {

    		OWLMetaRuleAxiom other = (OWLMetaRuleAxiom) obj;
        return other.getPropertyR().equals(propertyR) && other.getPropertyS().equals(propertyS);
    	}
    	return false;
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.METARULE;
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
    	OWLMetaRuleAxiom other = (OWLMetaRuleAxiom) object;
        int diff = propertyR.compareTo(other.getPropertyR());
        if (diff != 0) {
            return diff;
        }
        return propertyS.compareTo(other.getPropertyS());
    }
}