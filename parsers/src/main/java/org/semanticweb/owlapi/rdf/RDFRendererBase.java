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
package org.semanticweb.owlapi.rdf;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_AXIOM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NOTHING;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONTOLOGY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_THING;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_FIRST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_LIST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_NIL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_REST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_TYPE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.AnonymousIndividualProperties;
import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatImpl;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPrimitive;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.rdf.model.RDFGraph;
import org.semanticweb.owlapi.rdf.model.RDFTranslator;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences;
import org.semanticweb.owlapi.util.AlwaysOutputId;
import org.semanticweb.owlapi.util.AxiomAppearance;
import org.semanticweb.owlapi.util.AxiomSubjectProvider;
import org.semanticweb.owlapi.util.IndividualAppearance;
import org.semanticweb.owlapi.util.OWLAnonymousIndividualsWithMultipleOccurrences;
import org.semanticweb.owlapi.util.OWLAxiomsWithNestedAnnotations;
import org.semanticweb.owlapi.util.OWLEntityIRIComparator;
import org.semanticweb.owlapi.util.OWLObjectDesharer;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;
import org.semanticweb.owlapi.util.OWLObjectWalker;
import org.semanticweb.owlapi.util.SWRLVariableExtractor;
import org.semanticweb.owlapi.util.StructureWalker.AnnotationWalkingControl;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public abstract class RDFRendererBase {

    @Nonnull
    private static final String ANNOTATION_PROPERTIES_BANNER_TEXT = "Annotation properties";
    @Nonnull
    private static final String DATATYPES_BANNER_TEXT = "Datatypes";
    @Nonnull
    private static final String OBJECT_PROPERTIES_BANNER_TEXT = "Object Properties";
    @Nonnull
    private static final String DATA_PROPERTIES_BANNER_TEXT = "Data properties";
    @Nonnull
    private static final String CLASSES_BANNER_TEXT = "Classes";
    @Nonnull
    private static final String INDIVIDUALS_BANNER_TEXT = "Individuals";
    @Nonnull
    private static final String ANNOTATED_IRIS_BANNER_TEXT = "Annotations";
    /** general axioms */
    @Nonnull
    private static final String GENERAL_AXIOMS_BANNER_TEXT = "General axioms";
    /** rules banner */
    @Nonnull
    private static final String RULES_BANNER_TEXT = "Rules";
    @Nonnull
    private static final OWLEntityIRIComparator OWL_ENTITY_IRI_COMPARATOR =
        new OWLEntityIRIComparator();
    @Nonnull
    protected final OWLOntology ontology;
    protected RDFGraph graph;
    @Nonnull
    protected final Set<IRI> prettyPrintedTypes = initPrettyTypes();
    private final OWLDocumentFormat format;
    private Set<IRI> punned;
    protected final IndividualAppearance occurrences;
    protected final AxiomAppearance axiomOccurrences;
    protected Map<RDFTriple, RDFResourceBlankNode> triplesWithRemappedNodes;
    protected final Set<RDFResource> pending = new HashSet<>();
    private final Map<Object, Integer> blankNodeMap = new IdentityHashMap<>();

    @Nonnull
    protected static Set<IRI> initPrettyTypes() {
        return new HashSet<>(Arrays.asList(OWL_CLASS.getIRI(), OWL_OBJECT_PROPERTY.getIRI(),
            OWL_DATA_PROPERTY.getIRI(), OWL_ANNOTATION_PROPERTY.getIRI(), OWL_RESTRICTION.getIRI(),
            OWL_THING.getIRI(), OWL_NOTHING.getIRI(), OWL_ONTOLOGY.getIRI(),
            OWL_ANNOTATION_PROPERTY.getIRI(), OWL_NAMED_INDIVIDUAL.getIRI(), RDFS_DATATYPE.getIRI(),
            OWL_AXIOM.getIRI(), OWL_ANNOTATION.getIRI()));
    }

    /**
     * @param ontology ontology
     */
    public RDFRendererBase(@Nonnull OWLOntology ontology) {
        this(ontology, ontology.getOWLOntologyManager().getOntologyFormat(ontology));
    }

    protected RDFRendererBase(@Nonnull OWLOntology ontology, OWLDocumentFormat format) {
        this.ontology = ontology;
        this.format = format;
        if (AnonymousIndividualProperties.shouldSaveIdsForAllAnonymousIndividuals()) {
            occurrences = new AlwaysOutputId();
            axiomOccurrences = new AlwaysOutputId();
        } else {
            OWLAnonymousIndividualsWithMultipleOccurrences visitor =
                new OWLAnonymousIndividualsWithMultipleOccurrences();
            occurrences = visitor;
            ontology.accept(visitor);
            axiomOccurrences = new OWLAxiomsWithNestedAnnotations();
        }
    }

    /** Hooks for subclasses */
    /**
     * Called before the ontology document is rendered.
     * 
     * @throws IOException if there was a problem writing to the output stream
     */
    protected abstract void beginDocument() throws IOException;

    /**
     * Called after the ontology document has been rendered.
     * 
     * @throws IOException if there was a problem writing to the output stream
     */
    protected abstract void endDocument() throws IOException;

    /**
     * Called before an OWLObject such as an entity, anonymous individual, rule etc. is rendered.
     * 
     * @throws IOException if there was a problem writing to the output stream
     */
    @SuppressWarnings("unused")
    protected void beginObject() throws IOException {}

    /**
     * Called after an OWLObject such as an entity, anonymous individual, rule etc. has been
     * rendered.
     * 
     * @throws IOException if there was a problem writing to the output stream
     */
    @SuppressWarnings("unused")
    protected void endObject() throws IOException {}

    /**
     * Called before an annotation property is rendered to give subclasses the chance to prefix the
     * rendering with comments etc.
     * 
     * @param prop The property being rendered
     * @throws IOException if there was a problem writing to the output stream
     */
    protected abstract void writeAnnotationPropertyComment(@Nonnull OWLAnnotationProperty prop)
        throws IOException;

    /**
     * Called before a data property is rendered to give subclasses the chance to prefix the
     * rendering with comments etc.
     * 
     * @param prop The property being rendered
     * @throws IOException if there was a problem writing to the output stream
     */
    protected abstract void writeDataPropertyComment(@Nonnull OWLDataProperty prop)
        throws IOException;

    /**
     * Called before an object property is rendered.
     * 
     * @param prop The property being rendered
     * @throws IOException if there was a problem writing to the output stream
     */
    protected abstract void writeObjectPropertyComment(@Nonnull OWLObjectProperty prop)
        throws IOException;

    /**
     * Called before a class is rendered to give subclasses the chance to prefix the rendering with
     * comments etc.
     * 
     * @param cls The class being rendered
     * @throws IOException if there was a problem writing to the output stream
     */
    protected abstract void writeClassComment(@Nonnull OWLClass cls) throws IOException;

    /**
     * Called before a datatype is rendered to give subclasses the chance to prefix the rendering
     * with comments etc.
     * 
     * @param datatype The datatype being rendered
     * @throws IOException if there was a problem writing to the output stream
     */
    protected abstract void writeDatatypeComment(@Nonnull OWLDatatype datatype) throws IOException;

    /**
     * Called before an individual is rendered to give subclasses the chance to prefix the rendering
     * with comments etc.
     * 
     * @param ind The individual being rendered
     * @throws IOException if there was a problem writing to the output stream
     */
    protected abstract void writeIndividualComments(@Nonnull OWLNamedIndividual ind)
        throws IOException;

    /**
     * @throws IOException io error
     */
    public void render() throws IOException {
        graph = new RDFGraph();
        triplesWithRemappedNodes = graph.computeRemappingForSharedNodes();
        punned = ontology.getPunnedIRIs(EXCLUDED);
        beginDocument();
        renderOntologyHeader();
        renderOntologyComponents();
        endDocument();
    }

    private void renderOntologyComponents() throws IOException {
        renderInOntologySignatureEntities(OWLDocumentFormatImpl.determineIllegalPunnings(
            shouldInsertDeclarations(), ontology.getSignature(), ontology.getPunnedIRIs(INCLUDED)));
        renderAnonymousIndividuals();
        renderUntypedIRIAnnotationAssertions();
        renderGeneralAxioms();
        renderSWRLRules();
    }

    private void renderInOntologySignatureEntities(Collection<IRI> illegalPuns) throws IOException {
        Set<OWLAnnotationProperty> annotationProperties =
            ontology.getAnnotationPropertiesInSignature(EXCLUDED);
        renderEntities(annotationProperties, ANNOTATION_PROPERTIES_BANNER_TEXT, illegalPuns);
        Set<OWLDatatype> datatypes = ontology.getDatatypesInSignature();
        renderEntities(datatypes, DATATYPES_BANNER_TEXT, illegalPuns);
        Set<OWLObjectProperty> objectProperties = ontology.getObjectPropertiesInSignature();
        renderEntities(objectProperties, OBJECT_PROPERTIES_BANNER_TEXT, illegalPuns);
        Set<OWLDataProperty> dataProperties = ontology.getDataPropertiesInSignature();
        renderEntities(dataProperties, DATA_PROPERTIES_BANNER_TEXT, illegalPuns);
        Set<OWLClass> clses = ontology.getClassesInSignature();
        renderEntities(clses, CLASSES_BANNER_TEXT, illegalPuns);
        Set<OWLNamedIndividual> individuals = ontology.getIndividualsInSignature();
        renderEntities(individuals, INDIVIDUALS_BANNER_TEXT, illegalPuns);
    }

    /**
     * Renders a set of entities.
     * 
     * @param entities The entities. Not null.
     * @param bannerText The banner text that will prefix the rendering of the entities if anything
     *        is rendered. Not null. May be empty, in which case no banner will be written.
     * @param illegalPuns illegal puns
     * @throws IOException If there was a problem writing the rendering
     */
    private void renderEntities(@Nonnull Set<? extends OWLEntity> entities,
        @Nonnull String bannerText, Collection<IRI> illegalPuns) throws IOException {
        boolean firstRendering = true && XMLWriterPreferences.getInstance().isBannersEnabled();
        for (OWLEntity entity : toSortedSet(entities)) {
            assert entity != null;
            if (createGraph(entity, illegalPuns)) {
                if (firstRendering) {
                    firstRendering = false;
                    if (!bannerText.isEmpty()) {
                        writeBanner(bannerText);
                    }
                }
                renderEntity(entity);
            }
        }
    }

    private void renderEntity(@Nonnull OWLEntity entity) throws IOException {
        beginObject();
        writeEntityComment(entity);
        render(new RDFResourceIRI(entity.getIRI()), true);
        renderAnonRoots();
        endObject();
    }

    /**
     * Calls the appropriate hook method to write the comments for an entity.
     * 
     * @param entity The entity for which comments should be written.
     * @throws IOException if there was a problem writing the comment
     */
    private void writeEntityComment(@Nonnull OWLEntity entity) throws IOException {
        if (entity.isOWLClass()) {
            writeClassComment(entity.asOWLClass());
        } else if (entity.isOWLDatatype()) {
            writeDatatypeComment(entity.asOWLDatatype());
        } else if (entity.isOWLObjectProperty()) {
            writeObjectPropertyComment(entity.asOWLObjectProperty());
        } else if (entity.isOWLDataProperty()) {
            writeDataPropertyComment(entity.asOWLDataProperty());
        } else if (entity.isOWLAnnotationProperty()) {
            writeAnnotationPropertyComment(entity.asOWLAnnotationProperty());
        } else if (entity.isOWLNamedIndividual()) {
            writeIndividualComments(entity.asOWLNamedIndividual());
        }
    }

    private void renderUntypedIRIAnnotationAssertions() throws IOException {
        Set<IRI> annotatedIRIs = new TreeSet<>();
        for (OWLAnnotationAssertionAxiom ax : ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationSubject subject = ax.getSubject();
            if (subject instanceof IRI) {
                IRI iri = (IRI) subject;
                if (punned.contains(iri) || !ontology.containsEntityInSignature(iri, EXCLUDED)) {
                    annotatedIRIs.add(iri);
                }
            }
        }
        if (!annotatedIRIs.isEmpty()) {
            writeBanner(ANNOTATED_IRIS_BANNER_TEXT);
            for (IRI iri : annotatedIRIs) {
                assert iri != null;
                beginObject();
                createGraph(ontology.getAnnotationAssertionAxioms(iri));
                render(new RDFResourceIRI(iri), true);
                renderAnonRoots();
                endObject();
            }
        }
    }

    private void renderAnonymousIndividuals() throws IOException {
        for (OWLAnonymousIndividual anonInd : sortOptionally(
            ontology.getReferencedAnonymousIndividuals(EXCLUDED))) {
            assert anonInd != null;
            boolean anonRoot = true;
            Set<OWLAxiom> axioms = new TreeSet<>();
            for (OWLAxiom ax : sortOptionally(ontology.getReferencingAxioms(anonInd, EXCLUDED))) {
                if (!(ax instanceof OWLDifferentIndividualsAxiom)) {
                    assert ax != null;
                    AxiomSubjectProvider subjectProvider = new AxiomSubjectProvider();
                    OWLObject obj = subjectProvider.getSubject(ax);
                    if (!obj.equals(anonInd)) {
                        anonRoot = false;
                        break;
                    } else {
                        axioms.add(ax);
                    }
                }
            }
            if (anonRoot) {
                createGraph(axioms);
                renderAnonRoots();
            }
        }
    }

    private void renderSWRLRules() throws IOException {
        Set<SWRLRule> ruleAxioms = new TreeSet<>(ontology.getAxioms(AxiomType.SWRL_RULE));
        createGraph(ruleAxioms);
        if (!ruleAxioms.isEmpty()) {
            writeBanner(RULES_BANNER_TEXT);
            SWRLVariableExtractor variableExtractor = new SWRLVariableExtractor();
            for (SWRLRule rule : ruleAxioms) {
                rule.accept(variableExtractor);
            }
            for (SWRLVariable var : variableExtractor.getVariables()) {
                render(new RDFResourceIRI(var.getIRI()), true);
            }
            renderAnonRoots();
        }
    }

    private void renderGeneralAxioms() throws IOException {
        boolean haveWrittenBanner = false;
        Set<OWLAxiom> generalAxioms = getGeneralAxioms();
        for (OWLAxiom axiom : generalAxioms) {
            createGraph(Collections.singleton(axiom));
            Set<RDFResourceBlankNode> rootNodes = graph.getRootAnonymousNodes();
            if (!rootNodes.isEmpty()) {
                if (!haveWrittenBanner) {
                    writeBanner(GENERAL_AXIOMS_BANNER_TEXT);
                    haveWrittenBanner = true;
                }
                beginObject();
                renderAnonRoots();
                endObject();
            }
        }
    }

    protected RDFGraph getRDFGraph() {
        return verifyNotNull(graph, "rdfGraph not initialised yet");
    }

    /**
     * Gets the general axioms in the ontology. These are axioms such as DifferentIndividuals,
     * General Class axioms which do not describe or define a named class and so can't be written
     * out as a frame, nary disjoint classes, disjoint object properties, disjoint data properties
     * and HasKey axioms where the class expression is anonymous.
     * 
     * @return A set of axioms that are general axioms (and can't be written out in a frame-based
     *         style).
     */
    @Nonnull
    private Set<OWLAxiom> getGeneralAxioms() {
        Set<OWLAxiom> generalAxioms = new TreeSet<>();
        generalAxioms.addAll(ontology.getGeneralClassAxioms());
        generalAxioms.addAll(ontology.getAxioms(AxiomType.DIFFERENT_INDIVIDUALS));
        for (OWLDisjointClassesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                generalAxioms.add(ax);
            }
        }
        for (OWLDisjointObjectPropertiesAxiom ax : ontology
            .getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                generalAxioms.add(ax);
            }
        }
        for (OWLDisjointDataPropertiesAxiom ax : ontology
            .getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                generalAxioms.add(ax);
            }
        }
        for (OWLHasKeyAxiom ax : ontology.getAxioms(AxiomType.HAS_KEY)) {
            if (ax.getClassExpression().isAnonymous()) {
                generalAxioms.add(ax);
            }
        }
        return generalAxioms;
    }

    protected void renderOntologyHeader() throws IOException {
        RDFTranslator translator = new RDFTranslator(ontology.getOWLOntologyManager(), ontology,
            shouldInsertDeclarations(), occurrences, axiomOccurrences, nextBlankNodeId,
            blankNodeMap);
        graph = translator.getGraph();
        graph = translator.getGraph();
        ontology.accept(translator);
        if (!getRDFGraph().isEmpty()) {
            RDFResource node = translator.getMappedNode(ontology);
            render(node);
        }
        triplesWithRemappedNodes = getRDFGraph().computeRemappingForSharedNodes();
    }

    protected void render(RDFResource node) throws IOException {
        render(node, true);
        for (RDFResource n : getRDFGraph().getSubjectsForObject(node)) {
            render(n, true);
        }
    }

    private boolean createGraph(@Nonnull OWLEntity entity, Collection<IRI> illegalPuns) {
        final Set<OWLAxiom> axioms = new TreeSet<>();
        axioms.addAll(ontology.getDeclarationAxioms(entity));
        entity.accept(new OWLEntityVisitor() {

            @Override
            public void visit(OWLClass cls) {
                for (OWLAxiom ax : ontology.getAxioms(cls, EXCLUDED)) {
                    if (ax instanceof OWLDisjointClassesAxiom) {
                        OWLDisjointClassesAxiom disjAx = (OWLDisjointClassesAxiom) ax;
                        if (disjAx.getClassExpressions().size() > 2) {
                            continue;
                        }
                    }
                    axioms.add(ax);
                }
                for (OWLHasKeyAxiom ax : ontology.getAxioms(AxiomType.HAS_KEY)) {
                    if (ax.getClassExpression().equals(cls)) {
                        axioms.add(ax);
                    }
                }
            }

            @Override
            public void visit(OWLDatatype datatype) {
                axioms.addAll(ontology.getDatatypeDefinitions(datatype));
                createGraph(axioms);
            }

            @Override
            public void visit(OWLNamedIndividual individual) {
                for (OWLAxiom ax : sortOptionally(ontology.getAxioms(individual, EXCLUDED))) {
                    if (ax instanceof OWLDifferentIndividualsAxiom) {
                        continue;
                    }
                    axioms.add(ax);
                }
                // for object property assertion axioms where the property is
                // anonymous and the individual is the object, the renderer will
                // save the simplified version of the axiom.
                // As they will have subject and object inverted, we need to
                // collect them here, otherwise the triple will not be included
                // because the subject will not match
                for (OWLAxiom ax : ontology.getReferencingAxioms(individual)) {
                    if (ax instanceof OWLObjectPropertyAssertionAxiom) {
                        OWLObjectPropertyAssertionAxiom candidate =
                            (OWLObjectPropertyAssertionAxiom) ax;
                        if (candidate.getProperty().isAnonymous()
                            && candidate.getObject().equals(individual)) {
                            axioms.add(candidate);
                        }
                    }
                }
            }

            @Override
            public void visit(OWLDataProperty property) {
                for (OWLAxiom ax : ontology.getAxioms(property, EXCLUDED)) {
                    if (ax instanceof OWLDisjointDataPropertiesAxiom
                        && ((OWLDisjointDataPropertiesAxiom) ax).getProperties().size() > 2) {
                        continue;
                    }
                    axioms.add(ax);
                }
            }

            @Override
            public void visit(OWLObjectProperty property) {
                for (OWLAxiom ax : ontology.getAxioms(property, EXCLUDED)) {
                    if (ax instanceof OWLDisjointObjectPropertiesAxiom
                        && ((OWLDisjointObjectPropertiesAxiom) ax).getProperties().size() > 2) {
                        continue;
                    }
                    axioms.add(ax);
                }
                for (OWLSubPropertyChainOfAxiom ax : ontology
                    .getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    if (ax.getSuperProperty().equals(property)) {
                        axioms.add(ax);
                    }
                }
                axioms.addAll(ontology.getAxioms(ontology.getOWLOntologyManager()
                    .getOWLDataFactory().getOWLObjectInverseOf(property), EXCLUDED));
            }

            @Override
            public void visit(OWLAnnotationProperty property) {
                axioms.addAll(ontology.getAxioms(property, EXCLUDED));
            }
        });
        if (axioms.isEmpty() && shouldInsertDeclarations() && !illegalPuns.contains(entity.getIRI())
            && OWLDocumentFormatImpl.isMissingType(entity, ontology)) {
            axioms.add(ontology.getOWLOntologyManager().getOWLDataFactory()
                .getOWLDeclarationAxiom(entity));
        }
        // Don't write out duplicates for punned annotations!
        if (!punned.contains(entity.getIRI())) {
            axioms.addAll(ontology.getAnnotationAssertionAxioms(entity.getIRI()));
        }
        createGraph(axioms);
        return !axioms.isEmpty();
    }

    protected boolean shouldInsertDeclarations() {
        return format == null || format.isAddMissingTypes();
    }

    private AtomicInteger nextBlankNodeId = new AtomicInteger(1);

    protected void createGraph(@Nonnull Set<? extends OWLObject> objects) {
        RDFTranslator translator = new RDFTranslator(ontology.getOWLOntologyManager(), ontology,
            shouldInsertDeclarations(), occurrences, axiomOccurrences, nextBlankNodeId,
            blankNodeMap);
        for (OWLObject obj : objects) {
            deshare(obj).accept(translator);
        }
        graph = translator.getGraph();
        triplesWithRemappedNodes = graph.computeRemappingForSharedNodes();
    }

    protected OWLObject deshare(OWLObject o) {
        if (hasSharedStructure(o)) {
            return o.accept(new OWLObjectDesharer(ontology.getOWLOntologyManager()));
        }
        return o;
    }

    /**
     * @param o object to check
     * @return true if this object contains anonymous expressions referred multiple times. This is
     *         called structure sharing. An example can be:<br>
     * 
     *         <pre>
     * some P C subClassOf some Q (some P C)
     *         </pre>
     * 
     *         <br>
     *         This can happen in axioms as well as in expressions:<br>
     * 
     *         <pre>
     * (some P C) and (some Q (some P C))
     *         </pre>
     * 
     *         <br>
     */
    private static boolean hasSharedStructure(OWLObject o) {
        final Map<OWLObject, AtomicInteger> counters = new HashMap<>();
        OWLObjectWalker<OWLObject> walker = new OWLObjectWalker<>(Collections.singleton(o), true,
            AnnotationWalkingControl.DONT_WALK_ANNOTATIONS);
        walker.walkStructure(new OWLObjectVisitorAdapter() {

            @Override
            protected void handleDefault(OWLObject axiom) {
                if (isNotAnonymousExpression(axiom)) {
                    return;
                }
                AtomicInteger i = counters.get(axiom);
                if (i == null) {
                    i = new AtomicInteger();
                    counters.put(axiom, i);
                }
                i.incrementAndGet();
            }
        });
        for (AtomicInteger i : counters.values()) {
            if (i.get() > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param o expression to check
     * @return true if not anonymous
     */
    public static boolean isNotAnonymousExpression(OWLObject o) {
        boolean b = o instanceof OWLAxiom || o instanceof OWLOntology || o instanceof OWLPrimitive
            || o instanceof HasIRI;
        if (!b && o instanceof SWRLIndividualArgument) {
            b = isNotAnonymousExpression(((SWRLIndividualArgument) o).getIndividual());
        }
        return b;
    }

    protected abstract void writeBanner(@Nonnull String name) throws IOException;

    @Nonnull
    private static List<OWLEntity> toSortedSet(@Nonnull Set<? extends OWLEntity> entities) {
        List<OWLEntity> results = new ArrayList<>(entities);
        Collections.sort(results, OWL_ENTITY_IRI_COMPARATOR);
        return results;
    }

    /**
     * @throws IOException io error
     */
    public void renderAnonRoots() throws IOException {
        Set<RDFResourceBlankNode> rootAnonymousNodes = new TreeSet<>(graph.getRootAnonymousNodes());
        for (RDFResourceBlankNode node : rootAnonymousNodes) {
            assert node != null;
            render(node, true);
        }
    }

    /**
     * Renders the triples in the current graph into a concrete format. Subclasses of this class
     * decide upon how the triples get rendered.
     * 
     * @param node The main node to be rendered
     * @param root true if root
     * @throws IOException If there was a problem rendering the triples.
     */
    public abstract void render(@Nonnull RDFResource node, boolean root) throws IOException;

    protected boolean isObjectList(RDFResource node) {
        for (RDFTriple triple : graph.getTriplesForSubject(node)) {
            if (triple.getPredicate().getIRI().equals(RDF_TYPE.getIRI())
                && !triple.getObject().isAnonymous()
                && triple.getObject().getIRI().equals(RDF_LIST.getIRI())) {
                List<RDFNode> items = new ArrayList<>();
                toJavaList(node, items);
                for (RDFNode n : items) {
                    if (n.isLiteral()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    protected void toJavaList(RDFNode n, @Nonnull List<RDFNode> list) {
        RDFNode currentNode = n;
        while (currentNode != null) {
            for (RDFTriple triple : graph.getTriplesForSubject(currentNode)) {
                if (triple.getPredicate().getIRI().equals(RDF_FIRST.getIRI())) {
                    list.add(triple.getObject());
                }
            }
            for (RDFTriple triple : graph.getTriplesForSubject(currentNode)) {
                if (triple.getPredicate().getIRI().equals(RDF_REST.getIRI())) {
                    if (!triple.getObject().isAnonymous()) {
                        if (triple.getObject().getIRI().equals(RDF_NIL.getIRI())) {
                            // End of list
                            currentNode = null;
                        }
                    } else {
                        if (triple.getObject() instanceof RDFResource) {
                            // Should be another list
                            currentNode = triple.getObject();
                            // toJavaList(triple.getObject(), list);
                        }
                    }
                }
            }
        }
    }

    protected RDFTriple remapNodesIfNecessary(final RDFResource node, final RDFTriple triple) {
        RDFTriple tripleToRender = triple;
        RDFResourceBlankNode remappedNode = triplesWithRemappedNodes.get(tripleToRender);
        if (remappedNode != null) {
            tripleToRender = new RDFTriple(tripleToRender.getSubject(),
                tripleToRender.getPredicate(), remappedNode);
        }
        if (!node.equals(tripleToRender.getSubject())) {
            // the node will not match the triple subject if the node itself
            // is a remapped blank node
            // in which case the triple subject needs remapping as well
            tripleToRender =
                new RDFTriple(node, tripleToRender.getPredicate(), tripleToRender.getObject());
        }
        return tripleToRender;
    }
}
