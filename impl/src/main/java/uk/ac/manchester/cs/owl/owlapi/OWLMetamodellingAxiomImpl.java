package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLMetamodellingAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Ignacio Vidal<br>
 * FIng - UdelaR<br>
 * Date: 25-Oct-2014<br><br>
 */
public class OWLMetamodellingAxiomImpl extends OWLClassAxiomImpl implements OWLMetamodellingAxiom {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(OWLMetamodellingAxiomImpl.class.getName());	
	private static final long serialVersionUID = 30402L;

	private final OWLClassExpression modelClass;
    private final OWLIndividual metamodelIndividual;

    @SuppressWarnings("javadoc")
    public OWLMetamodellingAxiomImpl(OWLClassExpression modelClass, OWLIndividual metamodel, Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.modelClass = modelClass;
        this.metamodelIndividual = metamodel;
    }
    
    @SuppressWarnings("javadoc")
    public OWLMetamodellingAxiomImpl(OWLClassExpression modelClass, OWLIndividual metamodel) {
    	super(null);
        this.modelClass = modelClass;
        this.metamodelIndividual = metamodel;
    }

    @Override
    public OWLMetamodellingAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return new OWLMetamodellingAxiomImpl(modelClass, metamodelIndividual, mergeAnnos(annotations));
    }

    @Override
    public OWLMetamodellingAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLMetamodellingAxiomImpl(modelClass, metamodelIndividual);
    }

    @Override
    public OWLClassExpression getModelClass() {
        return modelClass;
    }

    @Override
    public OWLIndividual getMetamodelIndividual() {
        return metamodelIndividual;
    }

    @Override
    public boolean isGCI() {
        return modelClass.isAnonymous();
    }

    @Override
	public boolean equals(Object obj) {
    	if (!(obj instanceof OWLMetamodellingAxiom)) {
            return false;
        }
    	if(super.equals(obj)) {

        OWLMetamodellingAxiom other = (OWLMetamodellingAxiom) obj;
        return other.getModelClass().equals(modelClass) && other.getMetamodelIndividual().equals(metamodelIndividual);
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
        return AxiomType.METAMODELLING;
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLMetamodellingAxiom other = (OWLMetamodellingAxiom) object;
        int diff = modelClass.compareTo(other.getModelClass());
        if (diff != 0) {
            return diff;
        }
        return metamodelIndividual.compareTo(other.getMetamodelIndividual());
    }
}