/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v2_2.planner.logical.plans.rewriter

import org.neo4j.cypher.internal.commons.CypherFunSuite
import org.neo4j.cypher.internal.compiler.v2_2.LabelId
import org.neo4j.cypher.internal.compiler.v2_2.planner.{PlannerQuery, LogicalPlanningTestSupport}
import org.neo4j.cypher.internal.compiler.v2_2.planner.logical.plans._
import org.neo4j.graphdb.Direction

class UnnestOptionalTest extends CypherFunSuite with LogicalPlanningTestSupport {
  test("should rewrite Apply/Optional/Expand to OptionalExpand when lhs of expand is single row") {
    val singleRow: LogicalPlan = SingleRow(Set(IdName("a")))(solved)(Map.empty)
    val rhs:LogicalPlan =
      Optional(
        Expand(singleRow, IdName("a"), Direction.OUTGOING, Direction.OUTGOING, Seq.empty, IdName("b"), IdName("r"), SimplePatternLength
        )(solved))(solved)
    val lhs = newMockedLogicalPlan("a")
    val input = Apply(lhs, rhs)(solved)

    input.endoRewrite(unnestOptional) should equal(
      OptionalExpand(lhs, IdName("a"), Direction.OUTGOING, Seq.empty, IdName("b"), IdName("r"), SimplePatternLength, Seq.empty)(solved))
  }
}
