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
package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/**
 * An interface to objects that can visit
 * {@link org.semanticweb.owlapi.model.OWLClassExpression}s. (See the <a
 * href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 * 
 * @author Matthew Horridge, The University Of Manchester Bio-Health Informatics
 *         Group
 * @since 2.0.0
 */
public interface OWLClassExpressionVisitor {

    /**
     * visit OWLClass type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLClass ce);

    /**
     * visit OWLObjectIntersectionOf type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectIntersectionOf ce);

    /**
     * visit OWLObjectUnionOf type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectUnionOf ce);

    /**
     * visit OWLObjectComplementOf type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectComplementOf ce);

    /**
     * visit OWLObjectSomeValuesFrom type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectSomeValuesFrom ce);

    /**
     * visit OWLObjectAllValuesFrom type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectAllValuesFrom ce);

    /**
     * visit OWLObjectHasValue type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectHasValue ce);

    /**
     * visit OWLObjectMinCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectMinCardinality ce);

    /**
     * visit OWLObjectExactCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectExactCardinality ce);

    /**
     * visit OWLObjectMaxCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectMaxCardinality ce);

    /**
     * visit OWLObjectHasSelf type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectHasSelf ce);

    /**
     * visit OWLObjectOneOf type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLObjectOneOf ce);

    /**
     * visit OWLDataSomeValuesFrom type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLDataSomeValuesFrom ce);

    /**
     * visit OWLDataAllValuesFrom type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLDataAllValuesFrom ce);

    /**
     * visit OWLDataHasValue type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLDataHasValue ce);

    /**
     * visit OWLDataMinCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLDataMinCardinality ce);

    /**
     * visit OWLDataExactCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLDataExactCardinality ce);

    /**
     * visit OWLDataMaxCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    void visit(@Nonnull OWLDataMaxCardinality ce);
}
