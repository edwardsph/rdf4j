/*******************************************************************************
 * Copyright (c) 2020 Eclipse RDF4J contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 *******************************************************************************/

package org.eclipse.rdf4j.sail.shacl.ast.constraintcomponents;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.SHACL;
import org.eclipse.rdf4j.sail.shacl.SourceConstraintComponent;
import org.eclipse.rdf4j.sail.shacl.ValidationSettings;
import org.eclipse.rdf4j.sail.shacl.ast.ShaclShapeParsingException;
import org.eclipse.rdf4j.sail.shacl.ast.SparqlFragment;
import org.eclipse.rdf4j.sail.shacl.ast.StatementMatcher;
import org.eclipse.rdf4j.sail.shacl.ast.ValidationApproach;
import org.eclipse.rdf4j.sail.shacl.ast.paths.Path;
import org.eclipse.rdf4j.sail.shacl.ast.planNodes.CheckLessThanValuesBasedOnPathAndPredicate;
import org.eclipse.rdf4j.sail.shacl.ast.planNodes.PlanNode;
import org.eclipse.rdf4j.sail.shacl.ast.planNodes.PlanNodeProvider;
import org.eclipse.rdf4j.sail.shacl.ast.planNodes.UnionNode;
import org.eclipse.rdf4j.sail.shacl.ast.planNodes.Unique;
import org.eclipse.rdf4j.sail.shacl.ast.planNodes.UnorderedSelect;
import org.eclipse.rdf4j.sail.shacl.ast.planNodes.ValidationTuple;
import org.eclipse.rdf4j.sail.shacl.ast.targets.EffectiveTarget;
import org.eclipse.rdf4j.sail.shacl.ast.targets.TargetChain;
import org.eclipse.rdf4j.sail.shacl.wrapper.data.ConnectionsGroup;

public class LessThanConstraintComponent extends AbstractPairwiseConstraintComponent {

	public LessThanConstraintComponent(IRI predicate) {
		super(predicate);
	}

	@Override
	IRI getIRI() {
		return SHACL.LESS_THAN;
	}

	@Override
	public SourceConstraintComponent getConstraintComponent() {
		return SourceConstraintComponent.LessThanConstraintComponent;
	}

	PlanNode getPairwiseCheck(ConnectionsGroup connectionsGroup, ValidationSettings validationSettings,
			PlanNode allTargets, StatementMatcher.Variable<Resource> subject, StatementMatcher.Variable<Value> object,
			SparqlFragment targetQueryFragment) {
		return new CheckLessThanValuesBasedOnPathAndPredicate(connectionsGroup.getBaseConnection(),
				validationSettings.getDataGraph(), allTargets, predicate, subject, object, targetQueryFragment);
	}

	@Override
	public ConstraintComponent deepClone() {
		return new LessThanConstraintComponent(predicate);
	}

	@Override
	public List<Literal> getDefaultMessage() {
		return List.of();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		LessThanConstraintComponent that = (LessThanConstraintComponent) o;

		return predicate.equals(that.predicate);
	}

	@Override
	public int hashCode() {
		return predicate.hashCode() + "LessThanConstraintComponent".hashCode();
	}
}
