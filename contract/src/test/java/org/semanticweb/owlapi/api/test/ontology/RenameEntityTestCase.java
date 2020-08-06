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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public class RenameEntityTestCase extends TestBase {

    @Test
    public void testRenameClass() {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsAIRI1 = Class(iri("ClsA1"));
        OWLClass clsAIRI2 = Class(iri("ClsA2"));
        OWLClass clsB = Class(iri("ClsB"));
        OWLClass clsC = Class(iri("ClsC"));
        OWLObjectPropertyExpression propA = ObjectProperty(iri("propA"));
        OWLDataPropertyExpression propB = DataProperty(iri("propA"));
        OWLIndividual indA = NamedIndividual(iri("indA"));
        OWLAnnotationProperty annoProp = AnnotationProperty(iri("annoProp"));
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubClassOf(clsAIRI1, clsB));
        axioms1.add(EquivalentClasses(clsAIRI1, clsC));
        axioms1.add(DisjointClasses(clsAIRI1, clsC));
        axioms1.add(ObjectPropertyDomain(propA, clsAIRI1));
        axioms1.add(ObjectPropertyRange(propA, clsAIRI1));
        axioms1.add(DataPropertyDomain(propB, clsAIRI1));
        axioms1.add(ClassAssertion(clsAIRI1, indA));
        axioms1.add(AnnotationAssertion(annoProp, clsAIRI1.getIRI(), Literal("X")));
        ont.getOWLOntologyManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubClassOf(clsAIRI2, clsB));
        axioms2.add(EquivalentClasses(clsAIRI2, clsC));
        axioms2.add(DisjointClasses(clsAIRI2, clsC));
        axioms2.add(ObjectPropertyDomain(propA, clsAIRI2));
        axioms2.add(ObjectPropertyRange(propA, clsAIRI2));
        axioms2.add(DataPropertyDomain(propB, clsAIRI2));
        axioms2.add(ClassAssertion(clsAIRI2, indA));
        axioms2.add(AnnotationAssertion(annoProp, clsAIRI2.getIRI(), Literal("X")));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(clsAIRI1, clsAIRI2.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 =
            entityRenamer.changeIRI(clsAIRI2.getIRI(), clsAIRI1.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameObjectProperty() {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = Class(iri("ClsA"));
        OWLObjectProperty propA = ObjectProperty(iri("propA"));
        OWLObjectProperty propA2 = ObjectProperty(iri("propA2"));
        OWLObjectPropertyExpression propB = ObjectProperty(iri("propB")).getInverseProperty();
        OWLIndividual indA = NamedIndividual(iri("indA"));
        OWLIndividual indB = NamedIndividual(iri("indB"));
        OWLAnnotationProperty annoProp = AnnotationProperty(iri("annoProp"));
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubObjectPropertyOf(propA, propB));
        axioms1.add(EquivalentObjectProperties(propA, propB));
        axioms1.add(DisjointObjectProperties(propA, propB));
        axioms1.add(ObjectPropertyDomain(propA, clsA));
        axioms1.add(ObjectPropertyRange(propA, clsA));
        axioms1.add(FunctionalObjectProperty(propA));
        axioms1.add(InverseFunctionalObjectProperty(propA));
        axioms1.add(SymmetricObjectProperty(propA));
        axioms1.add(AsymmetricObjectProperty(propA));
        axioms1.add(TransitiveObjectProperty(propA));
        axioms1.add(ReflexiveObjectProperty(propA));
        axioms1.add(IrreflexiveObjectProperty(propA));
        axioms1.add(ObjectPropertyAssertion(propA, indA, indB));
        axioms1.add(NegativeObjectPropertyAssertion(propA, indA, indB));
        axioms1.add(AnnotationAssertion(annoProp, propA.getIRI(), Literal("X")));
        ont.getOWLOntologyManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubObjectPropertyOf(propA2, propB));
        axioms2.add(EquivalentObjectProperties(propA2, propB));
        axioms2.add(DisjointObjectProperties(propA2, propB));
        axioms2.add(ObjectPropertyDomain(propA2, clsA));
        axioms2.add(ObjectPropertyRange(propA2, clsA));
        axioms2.add(FunctionalObjectProperty(propA2));
        axioms2.add(InverseFunctionalObjectProperty(propA2));
        axioms2.add(SymmetricObjectProperty(propA2));
        axioms2.add(AsymmetricObjectProperty(propA2));
        axioms2.add(TransitiveObjectProperty(propA2));
        axioms2.add(ReflexiveObjectProperty(propA2));
        axioms2.add(IrreflexiveObjectProperty(propA2));
        axioms2.add(ObjectPropertyAssertion(propA2, indA, indB));
        axioms2.add(NegativeObjectPropertyAssertion(propA2, indA, indB));
        axioms2.add(AnnotationAssertion(annoProp, propA2.getIRI(), Literal("X")));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(propA, propA2.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(propA2.getIRI(), propA.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameDataProperty() {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = Class(iri("ClsA"));
        OWLDataProperty propA = DataProperty(iri("propA"));
        OWLDataProperty propA2 = DataProperty(iri("propA2"));
        OWLDataPropertyExpression propB = DataProperty(iri("propB"));
        OWLIndividual indA = NamedIndividual(iri("indA"));
        OWLAnnotationProperty annoProp = AnnotationProperty(iri("annoProp"));
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(SubDataPropertyOf(propA, propB));
        axioms1.add(EquivalentDataProperties(propA, propB));
        axioms1.add(DisjointDataProperties(propA, propB));
        axioms1.add(DataPropertyDomain(propA, clsA));
        axioms1.add(DataPropertyRange(propA, TopDatatype()));
        axioms1.add(FunctionalDataProperty(propA));
        axioms1.add(DataPropertyAssertion(propA, indA, Literal(33)));
        axioms1.add(NegativeDataPropertyAssertion(propA, indA, Literal(44)));
        axioms1.add(AnnotationAssertion(annoProp, propA.getIRI(), Literal("X")));
        ont.getOWLOntologyManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(SubDataPropertyOf(propA2, propB));
        axioms2.add(EquivalentDataProperties(propA2, propB));
        axioms2.add(DisjointDataProperties(propA2, propB));
        axioms2.add(DataPropertyDomain(propA2, clsA));
        axioms2.add(DataPropertyRange(propA2, TopDatatype()));
        axioms2.add(FunctionalDataProperty(propA2));
        axioms2.add(DataPropertyAssertion(propA2, indA, Literal(33)));
        axioms2.add(NegativeDataPropertyAssertion(propA2, indA, Literal(44)));
        axioms2.add(AnnotationAssertion(annoProp, propA2.getIRI(), Literal("X")));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(propA, propA2.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(propA2.getIRI(), propA.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameIndividual() {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = Class(iri("ClsA"));
        OWLDataProperty propA = DataProperty(iri("propA"));
        OWLObjectProperty propB = ObjectProperty(iri("propB"));
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLNamedIndividual indB = NamedIndividual(iri("indA"));
        OWLAnnotationProperty annoProp = AnnotationProperty(iri("annoProp"));
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(ClassAssertion(clsA, indA));
        axioms1.add(DataPropertyAssertion(propA, indA, Literal(33)));
        axioms1.add(NegativeDataPropertyAssertion(propA, indA, Literal(44)));
        axioms1.add(AnnotationAssertion(annoProp, propA.getIRI(), Literal("X")));
        axioms1.add(ObjectPropertyAssertion(propB, indA, indB));
        axioms1.add(NegativeObjectPropertyAssertion(propB, indA, indB));
        ont.getOWLOntologyManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(ClassAssertion(clsA, indB));
        axioms2.add(DataPropertyAssertion(propA, indB, Literal(33)));
        axioms2.add(NegativeDataPropertyAssertion(propA, indB, Literal(44)));
        axioms2.add(AnnotationAssertion(annoProp, propA.getIRI(), Literal("X")));
        axioms2.add(ObjectPropertyAssertion(propB, indB, indB));
        axioms2.add(NegativeObjectPropertyAssertion(propB, indB, indB));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(indA, indB.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(indB.getIRI(), indA.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameDatatype() {
        OWLOntology ont = getOWLOntology("testont");
        OWLDatatype dtA = Datatype(iri("DtA"));
        OWLDatatype dtB = Datatype(iri("DtB"));
        OWLDatatype dtC = Datatype(iri("DtC"));
        OWLDataRange rng1 = DataIntersectionOf(dtA, dtB);
        OWLDataRange rng1R = DataIntersectionOf(dtC, dtB);
        OWLDataRange rng2 = DataUnionOf(dtA, dtB);
        OWLDataRange rng2R = DataUnionOf(dtC, dtB);
        OWLDataRange rng3 = DataComplementOf(dtA);
        OWLDataRange rng3R = DataComplementOf(dtC);
        OWLDataPropertyExpression propB = DataProperty(iri("propA"));
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(DataPropertyRange(propB, rng1));
        axioms1.add(DataPropertyRange(propB, rng2));
        axioms1.add(DataPropertyRange(propB, rng3));
        ont.getOWLOntologyManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(DataPropertyRange(propB, rng1R));
        axioms2.add(DataPropertyRange(propB, rng2R));
        axioms2.add(DataPropertyRange(propB, rng3R));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(dtA, dtC.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(dtC.getIRI(), dtA.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void testRenameAnnotationProperty() {
        OWLOntology ont = getOWLOntology("testont");
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLNamedIndividual indB = NamedIndividual(iri("indB"));
        OWLAnnotationProperty annoProp = AnnotationProperty(iri("annoProp"));
        OWLAnnotationProperty annoPropR = AnnotationProperty(iri("annoPropR"));
        OWLAnnotationProperty annoProp2 = AnnotationProperty(iri("annoProp2"));
        Set<OWLAxiom> axioms1 = new HashSet<>();
        axioms1.add(Declaration(annoProp));
        axioms1.add(AnnotationAssertion(annoProp, indA.getIRI(), indB.getIRI()));
        axioms1.add(SubAnnotationPropertyOf(annoProp, annoProp2));
        axioms1.add(AnnotationPropertyRange(annoProp, indA.getIRI()));
        axioms1.add(AnnotationPropertyDomain(annoProp, indA.getIRI()));
        ont.getOWLOntologyManager().addAxioms(ont, axioms1);
        Set<OWLAxiom> axioms2 = new HashSet<>();
        axioms2.add(Declaration(annoPropR));
        axioms2.add(AnnotationAssertion(annoPropR, indA.getIRI(), indB.getIRI()));
        axioms2.add(SubAnnotationPropertyOf(annoPropR, annoProp2));
        axioms2.add(AnnotationPropertyRange(annoPropR, indA.getIRI()));
        axioms2.add(AnnotationPropertyDomain(annoPropR, indA.getIRI()));
        OWLEntityRenamer entityRenamer =
            new OWLEntityRenamer(ont.getOWLOntologyManager(), singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(annoProp, annoPropR.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes);
        assertEquals(ont.getAxioms(), axioms2);
        List<OWLOntologyChange> changes2 =
            entityRenamer.changeIRI(annoPropR.getIRI(), annoProp.getIRI());
        ont.getOWLOntologyManager().applyChanges(changes2);
        assertEquals(ont.getAxioms(), axioms1);
    }

    @Test
    public void shouldRenameAnnotationPropertyUsages() throws OWLOntologyCreationException {
        //@formatter:off
        String input="<?xml version=\"1.0\"?>\n" + 
            "<rdf:RDF xmlns=\"urn:test:ann#\" xml:base=\"urn:test:ann\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:annotations=\"urn:test:ann#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n" + 
            "    <owl:Ontology rdf:about=\"urn:test:ann\">\n" + 
            "        <testAnnotProp6>Ontology annotation</testAnnotProp6>\n" + 
            "    </owl:Ontology>\n" + 
            "    <owl:AnnotationProperty rdf:about=\"urn:test:ann#testAnnotProp1\"/>\n" + 
            "    <owl:AnnotationProperty rdf:about=\"urn:test:ann#testAnnotProp2\"/>\n" + 
            "    <owl:AnnotationProperty rdf:about=\"urn:test:ann#testAnnotProp3\"/>\n" + 
            "    <owl:AnnotationProperty rdf:about=\"urn:test:ann#testAnnotProp4\">\n" + 
            "        <testAnnotProp5>annotation on annotation property</testAnnotProp5>\n" + 
            "    </owl:AnnotationProperty>\n" + 
            "    <owl:AnnotationProperty rdf:about=\"urn:test:ann#testAnnotProp5\"/>\n" + 
            "    <owl:AnnotationProperty rdf:about=\"urn:test:ann#testAnnotProp7\"/>\n" + 
            "    <owl:Class rdf:about=\"urn:test:ann#Class1\">\n" + 
            "        <testAnnotProp1>test entity annotation</testAnnotProp1>\n" + 
            "        <testAnnotProp7>simple entity annotation (no annotations on this annotation)</testAnnotProp7>\n" + 
            "        <testAnnotProp8>annotation with externally declared annotation property</testAnnotProp8>\n" + 
            "    </owl:Class>\n" + 
            "    <owl:Axiom>\n" + 
            "        <owl:annotatedSource rdf:resource=\"urn:test:ann#Class1\"/>\n" + 
            "        <owl:annotatedProperty rdf:resource=\"urn:test:ann#testAnnotProp1\"/>\n" + 
            "        <owl:annotatedTarget>test entity annotation</owl:annotatedTarget>\n" + 
            "        <testAnnotProp2>test annotation on annotation</testAnnotProp2>\n" + 
            "    </owl:Axiom>\n" + 
            "    <owl:Class rdf:about=\"urn:test:ann#Class2\">\n" + 
            "        <rdfs:subClassOf rdf:resource=\"urn:test:ann#Class1\"/>\n" + 
            "    </owl:Class>\n" + 
            "    <owl:Axiom>\n" + 
            "        <owl:annotatedSource rdf:resource=\"urn:test:ann#Class2\"/>\n" + 
            "        <owl:annotatedProperty rdf:resource=\"http://www.w3.org/2000/01/rdf-schema#subClassOf\"/>\n" + 
            "        <owl:annotatedTarget rdf:resource=\"urn:test:ann#Class1\"/>\n" + 
            "        <testAnnotProp3>test axiom annotation</testAnnotProp3>\n" + 
            "    </owl:Axiom>\n" + 
            "</rdf:RDF>";
        //@formatter:on
        OWLOntology o1 = loadOntologyFromString(input);
        OWLEntityRenamer renamer =
            new OWLEntityRenamer(o1.getOWLOntologyManager(), Collections.singleton(o1));
        for (OWLAnnotationProperty p : o1.getAnnotationPropertiesInSignature()) {
            List<OWLOntologyChange> list =
                renamer.changeIRI(p.getIRI(), IRI.create("urn:test:attempt"));
            assertFalse(p + " should not be empty", list.isEmpty());
        }
    }
}
