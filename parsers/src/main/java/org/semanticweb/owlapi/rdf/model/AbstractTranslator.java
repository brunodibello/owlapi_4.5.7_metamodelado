/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.rdf.model;

import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DIFFERENT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DISJOINT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DISJOINT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_SOURCE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_TARGET;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ASSERTION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ASYMMETRIC_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_AXIOM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATATYPE_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISJOINT_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISJOINT_WITH;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISTINCT_MEMBERS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_FUNCTIONAL_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_HAS_KEY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_HAS_SELF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_HAS_VALUE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_IMPORTS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INVERSE_FUNCTIONAL_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INVERSE_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_IRREFLEXIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MAX_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MAX_QUALIFIED_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MEMBERS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MIN_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MIN_QUALIFIED_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONTOLOGY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_DATA_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_DATA_TYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_PROPERTY_CHAIN_AXIOM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_PROPERTY_DISJOINT_WITH;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_QUALIFIED_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_REFLEXIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SAME_AS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SOME_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SOURCE_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SYMMETRIC_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TARGET_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TARGET_VALUE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TRANSITIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_VERSION_IRI;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_WITH_RESTRICTIONS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_SUBCLASS_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_SUB_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_FIRST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_LIST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_NIL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_REST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_TYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_METAMODELLING;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ARGUMENTS;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ARGUMENT_1;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ARGUMENT_2;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ATOM_LIST;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BODY;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BUILT_IN;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BUILT_IN_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BUILT_IN_CLASS;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.CLASS_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.CLASS_PREDICATE;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DATAVALUED_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DATA_RANGE;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DATA_RANGE_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DIFFERENT_INDIVIDUALS_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.HEAD;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.IMP;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.INDIVIDUAL_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.PROPERTY_PREDICATE;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.SAME_INDIVIDUAL_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.VARIABLE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.rdf.RDFRendererBase;
import org.semanticweb.owlapi.util.AxiomAppearance;
import org.semanticweb.owlapi.util.IndividualAppearance;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import com.google.common.base.Optional;

/**
 * An abstract translator that can produce an RDF graph from an OWLOntology. Subclasses must provide
 * implementations to create concrete representations of resources, triples etc.
 * 
 * @param <N> the basic node
 * @param <R> a resource node
 * @param <P> a predicate node
 * @param <L> a literal node
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public abstract class AbstractTranslator<N extends Serializable, R extends N, P extends N, L extends N>
    implements OWLObjectVisitor, SWRLObjectVisitor {

    private final OWLOntologyManager manager;
    private final OWLOntology ont;
    private final boolean useStrongTyping;
    protected final IndividualAppearance multipleOccurrences;
    protected final AxiomAppearance axiomOccurrences;
    protected RDFGraph graph = new RDFGraph();
    private final AtomicInteger nextBlankNodeId;
    private final Map<Object, Integer> blankNodeMap;
    /** Maps Objects to nodes. */
    @Nonnull
    private final Map<OWLObject, N> nodeMap = new HashMap<>();
    private final Map<OWLObject, N> expressionMap = new IdentityHashMap<>();

    /**
     * @param manager the manager
     * @param ontology the ontology
     * @param useStrongTyping true if strong typing should be used
     * @param multiple multiple individuals appearance
     * @param appearance axiom appearance
     * @param nextNode next blank id counter
     */
    public AbstractTranslator(@Nonnull OWLOntologyManager manager, @Nonnull OWLOntology ontology,
        boolean useStrongTyping, IndividualAppearance multiple, AxiomAppearance appearance,
        AtomicInteger nextNode, Map<Object, Integer> blankNodeMap) {
        this.ont = checkNotNull(ontology, "ontology cannot be null");
        this.manager = checkNotNull(manager, "manager cannot be null");
        this.useStrongTyping = useStrongTyping;
        multipleOccurrences = multiple;
        this.axiomOccurrences = appearance;
        this.nextBlankNodeId = nextNode;
        this.blankNodeMap = blankNodeMap;
    }

    @Nonnull
    protected RDFResourceBlankNode getBlankNodeFor(Object key, boolean isIndividual, boolean needId,
        boolean isAxiom) {
        Integer id = blankNodeMap.get(key);
        if (id == null) {
            id = Integer.valueOf(nextBlankNodeId.getAndIncrement());
            blankNodeMap.put(key, id);
        }
        return new RDFResourceBlankNode(id, isIndividual, needId, isAxiom);
    }

    @Override
    public void visit(@Nonnull OWLDeclarationAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getEntity(), RDF_TYPE.getIRI(),
            axiom.getEntity().accept(OWLEntityTypeProvider.INSTANCE));
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        translateAnonymousNode(property);
        addTriple(property, OWL_INVERSE_OF.getIRI(), property.getInverse());
    }

    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_INTERSECTION_OF.getIRI(), node.getOperands());
    }

    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_UNION_OF.getIRI(), node.getOperands());
    }

    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addTriple(node, OWL_DATATYPE_COMPLEMENT_OF.getIRI(), node.getDataRange());
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_ONE_OF.getIRI(), node.getValues());
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addTriple(node, OWL_ON_DATA_TYPE.getIRI(), node.getDatatype());
        addListTriples(node, OWL_WITH_RESTRICTIONS.getIRI(), node.getFacetRestrictions());
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        translateAnonymousNode(ce);
        addListTriples(ce, OWL_INTERSECTION_OF.getIRI(), ce.getOperands());
        addTriple(ce, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf ce) {
        translateAnonymousNode(ce);
        addTriple(ce, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addListTriples(ce, OWL_UNION_OF.getIRI(), ce.getOperands());
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf ce) {
        translateAnonymousNode(ce);
        addTriple(ce, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addTriple(ce, OWL_COMPLEMENT_OF.getIRI(), ce.getOperand());
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf ce) {
        translateAnonymousNode(ce);
        addTriple(ce, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addListTriples(ce, OWL_ONE_OF.getIRI(), ce.getIndividuals());
        processIfAnonymous(ce.getIndividuals(), null);
    }

    // Translation of restrictions
    /**
     * Add type triples and the owl:onProperty triples for an OWLRestriction.
     * 
     * @param desc The restriction
     * @param property property
     */
    private void addRestrictionCommonTriplePropertyRange(@Nonnull OWLRestriction desc,
        @Nonnull OWLPropertyExpression property) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(desc, OWL_ON_PROPERTY.getIRI(), property);
    }

    private void addRestrictionCommonTriplePropertyExpression(@Nonnull OWLRestriction desc,
        @Nonnull OWLPropertyExpression property) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(desc, OWL_ON_PROPERTY.getIRI(), property);
    }

    private void addObjectCardinalityRestrictionTriples(
        @Nonnull OWLCardinalityRestriction<OWLClassExpression> ce, @Nonnull OWLPropertyExpression p,
        @Nonnull OWLRDFVocabulary cardiPredicate) {
        addRestrictionCommonTriplePropertyRange(ce, p);
        addTriple(ce, cardiPredicate.getIRI(), toTypedConstant(ce.getCardinality()));
        if (ce.isQualified()) {
            if (ce.isObjectRestriction()) {
                addTriple(ce, OWL_ON_CLASS.getIRI(), ce.getFiller());
            } else {
                addTriple(ce, OWL_ON_DATA_RANGE.getIRI(), ce.getFiller());
            }
        }
    }

    private void addDataCardinalityRestrictionTriples(
        @Nonnull OWLCardinalityRestriction<OWLDataRange> ce, @Nonnull OWLPropertyExpression p,
        @Nonnull OWLRDFVocabulary cardiPredicate) {
        addRestrictionCommonTriplePropertyRange(ce, p);
        addTriple(ce, cardiPredicate.getIRI(), toTypedConstant(ce.getCardinality()));
        if (ce.isQualified()) {
            if (ce.isObjectRestriction()) {
                addTriple(ce, OWL_ON_CLASS.getIRI(), ce.getFiller());
            } else {
                addTriple(ce, OWL_ON_DATA_RANGE.getIRI(), ce.getFiller());
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        addRestrictionCommonTriplePropertyRange(ce, ce.getProperty());
        addTriple(ce, OWL_SOME_VALUES_FROM.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLObjectAllValuesFrom ce) {
        addRestrictionCommonTriplePropertyRange(ce, ce.getProperty());
        addTriple(ce, OWL_ALL_VALUES_FROM.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue ce) {
        addRestrictionCommonTriplePropertyExpression(ce, ce.getProperty());
        addTriple(ce, OWL_HAS_VALUE.getIRI(), ce.getFiller());
        processIfAnonymous(ce.getFiller(), null);
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf ce) {
        translateAnonymousNode(ce);
        addTriple(ce, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(ce, OWL_ON_PROPERTY.getIRI(), ce.getProperty());
        addTriple(ce, OWL_HAS_SELF.getIRI(), manager.getOWLDataFactory().getOWLLiteral(true));
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality ce) {
        if (ce.isQualified()) {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(),
                OWL_MIN_QUALIFIED_CARDINALITY);
        } else {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_MIN_CARDINALITY);
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality ce) {
        if (ce.isQualified()) {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(),
                OWL_MAX_QUALIFIED_CARDINALITY);
        } else {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_MAX_CARDINALITY);
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality ce) {
        if (ce.isQualified()) {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_QUALIFIED_CARDINALITY);
        } else {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_CARDINALITY);
        }
    }

    @Override
    public void visit(@Nonnull OWLDataSomeValuesFrom ce) {
        addRestrictionCommonTriplePropertyRange(ce, ce.getProperty());
        addTriple(ce, OWL_SOME_VALUES_FROM.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLDataAllValuesFrom ce) {
        addRestrictionCommonTriplePropertyRange(ce, ce.getProperty());
        addTriple(ce, OWL_ALL_VALUES_FROM.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue ce) {
        addRestrictionCommonTriplePropertyExpression(ce, ce.getProperty());
        addTriple(ce, OWL_HAS_VALUE.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality ce) {
        if (ce.isQualified()) {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(),
                OWL_MIN_QUALIFIED_CARDINALITY);
        } else {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_MIN_CARDINALITY);
        }
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality ce) {
        if (ce.isQualified()) {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(),
                OWL_MAX_QUALIFIED_CARDINALITY);
        } else {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_MAX_CARDINALITY);
        }
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality ce) {
        if (ce.isQualified()) {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_QUALIFIED_CARDINALITY);
        } else {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_CARDINALITY);
        }
    }

    // Axioms
    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubClass(), RDFS_SUBCLASS_OF.getIRI(),
            axiom.getSuperClass());
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() == 2) {
            addPairwise(axiom, axiom.getClassExpressions(), OWL_EQUIVALENT_CLASS.getIRI());
        } else {
            for (OWLEquivalentClassesAxiom ax : sort(axiom.splitToAnnotatedPairs())) {
                ax.accept(this);
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() == 2) {
            addPairwiseClassExpressions(axiom, axiom.getClassExpressions(),
                OWL_DISJOINT_WITH.getIRI());
        } else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_CLASSES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.getClassExpressions());
            translateAnnotations(axiom);
        }
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> classExpressions = axiom.getClassExpressions();
        List<OWLClassExpression> list = new ArrayList<>(classExpressions);
        Collections.sort(list);
        addSingleTripleAxiom(axiom, axiom.getOWLClass(), OWL_DISJOINT_UNION_OF.getIRI(), list);
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(),
            axiom.getSuperProperty());
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSuperProperty(), OWL_PROPERTY_CHAIN_AXIOM.getIRI(),
            axiom.getPropertyChain());
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_EQUIVALENT_PROPERTY.getIRI());
        } else {
            for (OWLEquivalentObjectPropertiesAxiom ax : sort(axiom.splitToAnnotatedPairs())) {
                ax.accept(this);
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_PROPERTY_DISJOINT_WITH.getIRI());
        } else {
            translateAnonymousNode(axiom);
            translateAnnotations(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_PROPERTIES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.getProperties());
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getFirstProperty(), OWL_INVERSE_OF.getIRI(),
            axiom.getSecondProperty());
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_FUNCTIONAL_PROPERTY.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_INVERSE_FUNCTIONAL_PROPERTY.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_REFLEXIVE_PROPERTY.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_IRREFLEXIVE_PROPERTY.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_SYMMETRIC_PROPERTY.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_ASYMMETRIC_PROPERTY.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_TRANSITIVE_PROPERTY.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(),
            axiom.getSuperProperty());
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_EQUIVALENT_PROPERTY.getIRI());
        } else {
            for (OWLEquivalentDataPropertiesAxiom ax : axiom.splitToAnnotatedPairs()) {
                ax.accept(this);
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_PROPERTY_DISJOINT_WITH.getIRI());
        } else {
            translateAnonymousNode(axiom);
            translateAnnotations(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_PROPERTIES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.getProperties());
        }
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_FUNCTIONAL_PROPERTY.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getDatatype(), OWL_EQUIVALENT_CLASS.getIRI(),
            axiom.getDataRange());
    }

    @Override
    public void visit(@Nonnull OWLHasKeyAxiom axiom) {
        Set<OWLPropertyExpression> propertyExpressions = axiom.getPropertyExpressions();
        List<OWLPropertyExpression> list = new ArrayList<>(propertyExpressions);
        Collections.sort(list);
        addSingleTripleAxiom(axiom, axiom.getClassExpression(), OWL_HAS_KEY.getIRI(), list);
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        addPairwise(axiom, axiom.getIndividuals(), OWL_SAME_AS.getIRI());
        processIfAnonymous(axiom.getIndividuals(), axiom);
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DIFFERENT.getIRI());
        addListTriples(axiom, OWL_DISTINCT_MEMBERS.getIRI(), axiom.getIndividuals());
        processIfAnonymous(axiom.getIndividuals(), axiom);
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        addSingleTripleAxiom(axiom, axiom.getIndividual(), RDF_TYPE.getIRI(),
            axiom.getClassExpression());
        processIfAnonymous(axiom.getIndividual(), axiom);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        OWLObjectPropertyAssertionAxiom simplified = axiom.getSimplified();
        addSingleTripleAxiom(simplified, simplified.getSubject(), simplified.getProperty(),
            simplified.getObject());
        processIfAnonymous(simplified.getObject(), axiom);
        processIfAnonymous(simplified.getSubject(), axiom);
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getIRI(), OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        addTriple(axiom, OWL_SOURCE_INDIVIDUAL.getIRI(), axiom.getSubject());
        addTriple(axiom, OWL_ASSERTION_PROPERTY.getIRI(), axiom.getProperty());
        addTriple(axiom, OWL_TARGET_INDIVIDUAL.getIRI(), axiom.getObject());
        translateAnnotations(axiom);
        processIfAnonymous(axiom.getSubject(), axiom);
        processIfAnonymous(axiom.getObject(), axiom);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getIRI(), OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        addTriple(axiom, OWL_SOURCE_INDIVIDUAL.getIRI(), axiom.getSubject());
        addTriple(axiom, OWL_ASSERTION_PROPERTY.getIRI(), axiom.getProperty());
        addTriple(axiom, OWL_TARGET_VALUE.getIRI(), axiom.getObject());
        translateAnnotations(axiom);
        processIfAnonymous(axiom.getSubject(), axiom);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getValue());
        if (axiom.getValue() instanceof OWLAnonymousIndividual) {
            processIfAnonymous((OWLAnonymousIndividual) axiom.getValue(), axiom);
        }
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(),
            axiom.getSuperProperty());
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }
    
    @Override
	public void visit(OWLMetamodellingAxiom axiom) {
    	addSingleTripleAxiom(axiom, axiom.getMetamodelIndividual(), RDFS_METAMODELLING.getIRI(), axiom.getModelClass());
	}

    @Override
    public void visit(@Nonnull OWLClass ce) {
        storeNodeIfNotPresent(ce, getResourceNode(ce.getIRI()));
        addStrongTyping(ce);
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        storeNodeIfNotPresent(node, getResourceNode(node.getIRI()));
        addStrongTyping(node);
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, node.getFacet().getIRI(), node.getFacetValue());
    }

    @Override
    public void visit(IRI iri) {
        storeNodeIfNotPresent(iri, getResourceNode(iri));
    }

    @Override
    public void visit(OWLLiteral node) {
        storeNodeIfNotPresent(node, getLiteralNode(node));
    }

    @Override
    public void visit(@Nonnull OWLDataProperty property) {
        storeNodeIfNotPresent(property, getPredicateNode(property.getIRI()));
        addStrongTyping(property);
    }

    @Override
    public void visit(@Nonnull OWLObjectProperty property) {
        storeNodeIfNotPresent(property, getPredicateNode(property.getIRI()));
        addStrongTyping(property);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationProperty property) {
        storeNodeIfNotPresent(property, getPredicateNode(property.getIRI()));
        addStrongTyping(property);
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual individual) {
        storeNodeIfNotPresent(individual, getResourceNode(individual.getIRI()));
        addStrongTyping(individual);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        translateAnonymousNode(individual);
    }

    @Override
    public void visit(@Nonnull OWLOntology ontology) {
        Optional<IRI> ontologyIRI = ontology.getOntologyID().getOntologyIRI();
        if (ontologyIRI.isPresent()) {
            storeNodeIfNotPresent(ontology, getResourceNode(ontologyIRI.get()));
        } else {
            translateAnonymousNode(ontology);
        }
        addTriple(ontology, RDF_TYPE.getIRI(), OWL_ONTOLOGY.getIRI());
        addVersionIRIToOntologyHeader(ontology);
        addImportsDeclarationsToOntologyHeader(ontology);
        addAnnotationsToOntologyHeader(ontology);
    }

    private void addVersionIRIToOntologyHeader(OWLOntology ontology) {
        OWLOntologyID ontID = ontology.getOntologyID();
        if (ontID.getVersionIRI().isPresent()) {
            addTriple(ontology, OWL_VERSION_IRI.getIRI(), ontID.getVersionIRI().get());
        }
    }

    private void addImportsDeclarationsToOntologyHeader(OWLOntology ontology) {
        ontology.getImportsDeclarations()
            .forEach(decl -> addTriple(ontology, OWL_IMPORTS.getIRI(), decl.getIRI()));
    }

    private void addAnnotationsToOntologyHeader(OWLOntology ontology) {
        ontology.getAnnotations().forEach(a -> translateAnnotation(ontology, a));
    }



    @Override
    public void visit(@Nonnull SWRLRule rule) {
        translateAnonymousNode(rule);
        translateAnnotations(rule);
        addTriple(rule, RDF_TYPE.getIRI(), IMP.getIRI());
        Set<SWRLAtom> antecedent = rule.getBody();
        addTriple(rule, BODY.getIRI(), antecedent, ATOM_LIST.getIRI());
        Set<SWRLAtom> consequent = rule.getHead();
        addTriple(rule, HEAD.getIRI(), consequent, ATOM_LIST.getIRI());
    }

    @Override
    public void visit(@Nonnull SWRLClassAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), CLASS_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, CLASS_PREDICATE.getIRI(), node.getPredicate());
        node.getArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getArgument());
    }

    @Override
    public void visit(@Nonnull SWRLDataRangeAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), DATA_RANGE_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, DATA_RANGE.getIRI(), node.getPredicate());
        node.getArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getArgument());
    }

    @Override
    public void visit(@Nonnull SWRLObjectPropertyAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), INDIVIDUAL_PROPERTY_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, PROPERTY_PREDICATE.getIRI(), node.getPredicate());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }

    @Override
    public void visit(@Nonnull SWRLDataPropertyAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), DATAVALUED_PROPERTY_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, PROPERTY_PREDICATE.getIRI(), node.getPredicate());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }

    @Override
    public void visit(@Nonnull SWRLBuiltInAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), BUILT_IN_ATOM.getIRI());
        addTriple(node, BUILT_IN.getIRI(), node.getPredicate());
        addTriple(getResourceNode(node.getPredicate()), getPredicateNode(RDF_TYPE.getIRI()),
            getResourceNode(BUILT_IN_CLASS.getIRI()));
        addTriple(getResourceNode(node), getPredicateNode(ARGUMENTS.getIRI()),
            translateList(new ArrayList<>(node.getArguments())));
    }

    @Override
    public void visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), DIFFERENT_INDIVIDUALS_ATOM.getIRI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }

    @Override
    public void visit(@Nonnull SWRLSameIndividualAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), SAME_INDIVIDUAL_ATOM.getIRI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }

    @Override
    public void visit(@Nonnull SWRLVariable node) {
        storeNodeIfNotPresent(node, getResourceNode(node.getIRI()));
        if (!ont.containsIndividualInSignature(node.getIRI())) {
            addTriple(node, RDF_TYPE.getIRI(), VARIABLE.getIRI());
        }
    }

    @Override
    public void visit(@Nonnull SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
        storeNodeIfNotPresent(node, nodeMap.get(node.getIndividual()));
    }

    @Override
    public void visit(@Nonnull SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
        storeNodeIfNotPresent(node, nodeMap.get(node.getLiteral()));
    }

    // Methods to add triples
    private void addSingleTripleAxiom(@Nonnull OWLAxiom ax, @Nonnull OWLObject subject,
        @Nonnull IRI pred, @Nonnull OWLObject obj) {
        addSingleTripleAxiom(ax, getResourceNode(subject), getPredicateNode(pred), getNode(obj));
    }

    private void addSingleTripleAxiom(@Nonnull OWLAxiom ax, @Nonnull OWLObject subject,
        @Nonnull IRI pred, @Nonnull IRI obj) {
        addSingleTripleAxiom(ax, getResourceNode(subject), getPredicateNode(pred),
            getResourceNode(obj));
    }

    private void addSingleTripleAxiom(@Nonnull OWLAxiom ax, @Nonnull OWLObject subj,
        @Nonnull IRI pred, @Nonnull List<? extends OWLObject> obj) {
        addSingleTripleAxiom(ax, getResourceNode(subj), getPredicateNode(pred), translateList(obj));
    }

    private void addSingleTripleAxiom(@Nonnull OWLAxiom ax, @Nonnull OWLObject subj,
        @Nonnull OWLObject pred, @Nonnull OWLObject obj) {
        addSingleTripleAxiom(ax, getResourceNode(subj), getPredicateNode(pred), getNode(obj));
    }

    /**
     * Adds the representation of an axiom to the RDF graph where the axiom has a SINGLE MAIN TRIPLE
     * (specified by the subject, predicate, object parameters). The triple specified by the
     * subject, predicate and object parameters will be added to the graph. If the axiom has any
     * annotations on it then extra triples will be added. These will consist of three triples, that
     * "reify" (not in the RDF sense) the specified triple using the OWL 2 annotation vocabulary:
     * owl:annotatedSource, owl:annotatedProperty, owl:annotatedTarget, and other triples to encode
     * the annotations.
     * 
     * @param ax The axiom that the triple specified as subject, pred, obj represents.
     * @param subject The subject of the triple representing the axiom
     * @param predicate The predicate of the triple representing the axiom
     * @param object The object of the triple representing the axiom
     */
    private void addSingleTripleAxiom(@Nonnull OWLAxiom ax, @Nonnull R subject,
        @Nonnull P predicate, @Nonnull N object) {
        // Base triple
        addTriple(subject, predicate, object);
        if (ax.getAnnotations().isEmpty()) {
            return;
        }
        // The axiom has annotations and we therefore need to reify the axiom in
        // order to add the annotations
        translateAnonymousNode(ax);
        addTriple(getResourceNode(ax), getPredicateNode(RDF_TYPE.getIRI()),
            getResourceNode(OWL_AXIOM.getIRI()));
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_SOURCE.getIRI()), subject);
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_PROPERTY.getIRI()),
            predicate);
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_TARGET.getIRI()), object);
        translateAnnotations(ax);
    }

    private void translateAnnotations(@Nonnull OWLAxiom ax) {
        translateAnonymousNode(ax);
        for (OWLAnnotation anno : sort(ax.getAnnotations())) {
            assert anno != null;
            translateAnnotation(ax, anno);
        }
    }

    /**
     * Translates an annotation on a given subject. This method implements the TANN(ann, y)
     * translation in the spec
     * 
     * @param subject The subject of the annotation
     * @param annotation The annotation
     */
    private void translateAnnotation(@Nonnull OWLObject subject,
        @Nonnull OWLAnnotation annotation) {
        // We first add the base triple
        addTriple(subject, annotation.getProperty().getIRI(), annotation.getValue());
        annotation.getProperty().accept(this);
        // if the annotation has a blank node as subject, add the triples here
        if (annotation.getValue() instanceof OWLAnonymousIndividual) {
            OWLAnonymousIndividual ind = (OWLAnonymousIndividual) annotation.getValue();
            translateAnonymousNode(ind);
            processIfAnonymous(ind);
        }
        // If the annotation doesn't have annotations on it then we're done
        Collection<OWLAnnotation> annotations = annotation.getAnnotations();
        if (annotations.isEmpty()) {
            return;
        }
        // The annotation has annotations on it so we need to reify the
        // annotation
        // The main node is the annotation, it is typed as an annotation
        translateAnonymousNode(annotation);
        addTriple(annotation, RDF_TYPE.getIRI(), OWL_ANNOTATION.getIRI());
        addTriple(annotation, OWL_ANNOTATED_SOURCE.getIRI(), subject);
        addTriple(annotation, OWL_ANNOTATED_PROPERTY.getIRI(), annotation.getProperty().getIRI());
        addTriple(annotation, OWL_ANNOTATED_TARGET.getIRI(), annotation.getValue());
        for (OWLAnnotation anno : sort(annotations)) {
            assert anno != null;
            translateAnnotation(annotation, anno);
        }
    }

    @Override
    public void visit(OWLAnnotation node) {
        throw new OWLRuntimeException(
            "The translator should not be used directly on instances of OWLAnnotation because an annotation cannot be translated without a subject.");
    }

    private void translateAnonymousNode(@Nonnull OWLObject object) {
        storeNode(object, getAnonymousNode(object));
    }

    private void storeNode(@Nonnull OWLObject object, N value) {
        if (!RDFRendererBase.isNotAnonymousExpression(object)) {
            expressionMap.put(object, value);
        } else {
            nodeMap.put(object, value);
        }
    }

    private void storeNodeIfNotPresent(@Nonnull OWLObject object, N value) {
        N v = getMappedNode(object);
        if (v == null) {
            storeNode(object, value);
        }
    }

    /**
     * @param object that has already been mapped
     * @param <T> type needed
     * @return mapped node, or null if the node is absent
     */
    public <T extends N> T getMappedNode(OWLObject object) {
        if (!RDFRendererBase.isNotAnonymousExpression(object)) {
            return (T) expressionMap.get(object);
        }
        return (T) nodeMap.get(object);
    }

    /**
     * @param subject subject
     * @param pred predicate
     * @param object object
     */
    public void addTriple(R subject, IRI pred, IRI object) {
        addTriple(subject, getPredicateNode(pred), getResourceNode(object));
    }

    /**
     * @param subject subject
     * @param pred predicate
     * @param object object
     */
    public void addTriple(R subject, IRI pred, OWLObject object) {
        addTriple(subject, getPredicateNode(pred), getNode(object));
    }

    /**
     * Gets a resource that has a IRI.
     * 
     * @param iri The IRI of the resource
     * @return The resource with the specified IRI
     */
    @Nonnull
    protected abstract R getResourceNode(@Nonnull IRI iri);

    @Nonnull
    protected abstract P getPredicateNode(@Nonnull IRI iri);

    /**
     * Gets an anonymous resource.
     * 
     * @param key A key for the resource. For a given key identity, the resources that are returned
     *        should be equal and have the same hashcode.
     * @return The resource
     */
    @Nonnull
    protected abstract R getAnonymousNode(@Nonnull Object key);

    /**
     * Gets a fresh anonymous resource.
     * 
     * @param key A key for the resource. Each call will create a new node; nodes cannot clash.
     * @param isAxiom true if axiom
     * @return The resource
     */
    @Nonnull
    protected abstract R getAnonymousNodeForExpressions(@Nonnull Object key, boolean isAxiom);

    @Nonnull
    protected abstract L getLiteralNode(@Nonnull OWLLiteral literal);

    protected abstract void addTriple(@Nonnull R subject, @Nonnull P pred, @Nonnull N object);

    @Nonnull
    @SuppressWarnings("unchecked")
    private R getResourceNode(@Nonnull OWLObject object) {
        R r = (R) getMappedNode(object);
        if (r == null) {
            object.accept(this);
            r = (R) getMappedNode(object);
            if (r == null) {
                throw new IllegalStateException(
                    "Node is null. Attempting to get node for " + object);
            }
        }
        return r;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private P getPredicateNode(@Nonnull OWLObject object) {
        P p = (P) getMappedNode(object);
        if (p == null) {
            object.accept(this);
            p = (P) getMappedNode(object);
            if (p == null) {
                throw new IllegalStateException(
                    "Node is null. Attempting to get node for " + object);
            }
        }
        return p;
    }

    @Nonnull
    private N getNode(@Nonnull OWLObject object) {
        N node = getMappedNode(object);
        if (node == null) {
            object.accept(this);
            node = getMappedNode(object);
            if (node == null) {
                throw new IllegalStateException(
                    "Node is null. Attempting to get node for " + object);
            }
        }
        return node;
    }

    @Nonnull
    private R translateList(@Nonnull List<? extends OWLObject> list) {
        return translateList(list, RDF_LIST.getIRI());
    }

    @Nonnull
    private R translateList(@Nonnull List<? extends OWLObject> list, @Nonnull IRI listType) {
        if (list.isEmpty()) {
            return getResourceNode(RDF_NIL.getIRI());
        }
        R main = getResourceNode(RDF_NIL.getIRI());
        int listSize = list.size() - 1;
        for (int i = listSize; i >= 0; i--) {
            R anonNode = getAnonymousNode(list.subList(i, listSize));
            addTriple(anonNode, getPredicateNode(RDF_TYPE.getIRI()), getResourceNode(listType));
            @Nonnull
            OWLObject obj = list.get(i);
            addTriple(anonNode, getPredicateNode(RDF_FIRST.getIRI()), getNode(obj));
            addTriple(anonNode, getPredicateNode(RDF_REST.getIRI()), main);
            main = anonNode;
        }
        return main;
    }

    private void addTriple(@Nonnull OWLObject subject, @Nonnull IRI pred, @Nonnull IRI object) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), getResourceNode(object));
    }

    private void addTriple(@Nonnull OWLObject subject, @Nonnull IRI pred,
        @Nonnull OWLObject object) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), getNode(object));
    }

    private void addListTriples(@Nonnull OWLObject subject, @Nonnull IRI pred,
        @Nonnull Set<? extends OWLObject> objects) {
        ArrayList<OWLObject> list = new ArrayList<>(objects);
        Collections.sort(list);
        addTriple(getResourceNode(subject), getPredicateNode(pred), translateList(list));
    }

    private void addTriple(@Nonnull OWLObject subject, @Nonnull IRI pred,
        @Nonnull Set<? extends OWLObject> objects, @Nonnull IRI listType) {
        addTriple(getResourceNode(subject), getPredicateNode(pred),
            translateList(new ArrayList<>(objects), listType));
    }

    @Nonnull
    private OWLLiteral toTypedConstant(int i) {
        return manager.getOWLDataFactory().getOWLLiteral(Integer.toString(i), manager
            .getOWLDataFactory().getOWLDatatype(XSDVocabulary.NON_NEGATIVE_INTEGER.getIRI()));
    }

    private void processIfAnonymous(@Nonnull Set<OWLIndividual> inds, OWLAxiom root) {
        for (OWLIndividual ind : sortOptionally(inds)) {
            assert ind != null;
            processIfAnonymous(ind, root);
        }
    }

    @Nonnull
    private final Set<OWLIndividual> currentIndividuals = new HashSet<>();

    private void processIfAnonymous(@Nonnull OWLIndividual ind, @Nullable OWLAxiom root) {
        if (!currentIndividuals.contains(ind)) {
            currentIndividuals.add(ind);
            if (ind.isAnonymous()) {
                for (OWLAxiom ax : getSortedIndividualAxioms(ind)) {
                    if (root == null || !root.equals(ax)) {
                        ax.accept(this);
                    }
                }
                for (OWLAnnotationAssertionAxiom ax : sort(
                    getAnnotationAssertionsForAnonymous(ind))) {
                    ax.accept(this);
                }
            }
            currentIndividuals.remove(ind);
        }
    }

    private void processIfAnonymous(@Nonnull OWLIndividual ind) {
        if (!currentIndividuals.contains(ind)) {
            currentIndividuals.add(ind);
            if (ind.isAnonymous()) {
                for (OWLAxiom ax : getSortedIndividualAxioms(ind)) {
                    ax.accept(this);
                }
                for (OWLAnnotationAssertionAxiom ax : sort(
                    getAnnotationAssertionsForAnonymous(ind))) {
                    ax.accept(this);
                }
            }
            currentIndividuals.remove(ind);
        }
    }

    @Nonnull
    private static <T extends OWLObject> List<T> sort(Collection<T> axioms) {
        return sortOptionally(axioms);
    }

    @Nonnull
    private Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionsForAnonymous(
        @Nonnull OWLIndividual ind) {
        OWLAnonymousIndividual owlAnonymousIndividual = ind.asOWLAnonymousIndividual();
        return ont.getAnnotationAssertionAxioms(owlAnonymousIndividual);
    }

    @Nonnull
    private List<OWLIndividualAxiom> getSortedIndividualAxioms(@Nonnull OWLIndividual ind) {
        return sort(ont.getAxioms(ind, Imports.EXCLUDED));
    }

    private void addPairwise(@Nonnull OWLAxiom axiom,
        @Nonnull Collection<? extends OWLObject> objects, @Nonnull IRI iri) {
        List<? extends OWLObject> objectList = new ArrayList<>(objects);
        Collections.sort(objectList);
        for (int i = 0; i < objectList.size(); i++) {
            for (int j = i; j < objectList.size(); j++) {
                if (i != j) {
                    addSingleTripleAxiom(axiom, objectList.get(i), iri, objectList.get(j));
                }
            }
        }
    }

    /**
     * Renders a set of class expressions in a pairwise manner using the specified IRI. It is
     * assumed that the relationship described by the IRI (e.g. disjointWith) is symmetric. The
     * method delegates to the {@code addPairwise} method after sorting the class expressions so
     * that named classes appear first.
     * 
     * @param axiom The axiom which will dictate which axiom annotation get rendered
     * @param classExpressions The set of class expressions to be rendered.
     * @param iri The IRI which describes the relationship between pairs of class expressions.
     */
    private void addPairwiseClassExpressions(@Nonnull OWLAxiom axiom,
        @Nonnull Set<OWLClassExpression> classExpressions, @Nonnull IRI iri) {
        addPairwise(axiom, classExpressions, iri);
    }

    /**
     * Adds triples to strong type an entity. Triples are only added if the useStrongTyping flag is
     * set to {@code true} and the entity is not a built in entity.
     * 
     * @param entity The entity for which strong typing triples should be added.
     */
    private void addStrongTyping(@Nonnull OWLEntity entity) {
        if (!useStrongTyping) {
            return;
        }
        if (!OWLDocumentFormatImpl.isMissingType(entity, ont)) {
            return;
        }
        addTriple(entity, RDF_TYPE.getIRI(), entity.accept(OWLEntityTypeProvider.INSTANCE));
    }

    /** Visits entities and returns their RDF type. */
    private static class OWLEntityTypeProvider implements OWLEntityVisitorEx<IRI> {

        @Nonnull
        public static final OWLEntityTypeProvider INSTANCE = new OWLEntityTypeProvider();

        @Override
        public IRI visit(OWLClass cls) {
            return OWL_CLASS.getIRI();
        }

        @Override
        public IRI visit(OWLObjectProperty property) {
            return OWL_OBJECT_PROPERTY.getIRI();
        }

        @Override
        public IRI visit(OWLDataProperty property) {
            return OWL_DATA_PROPERTY.getIRI();
        }

        @Override
        public IRI visit(OWLNamedIndividual individual) {
            return OWL_NAMED_INDIVIDUAL.getIRI();
        }

        @Override
        public IRI visit(OWLDatatype datatype) {
            return RDFS_DATATYPE.getIRI();
        }

        @Override
        public IRI visit(OWLAnnotationProperty property) {
            return OWL_ANNOTATION_PROPERTY.getIRI();
        }
    }

    /**
     * @return the graph
     */
    public RDFGraph getGraph() {
        return graph;
    }

    /** Clear the graph. */
    public void reset() {
        graph = new RDFGraph();
    }
}
