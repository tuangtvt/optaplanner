package org.optaplanner.core.api.score.stream;

import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;

public interface ConstraintBuilder<ConstraintBuilder_ extends ConstraintBuilder<ConstraintBuilder_>> {

    /**
     * Builds a {@link Constraint} from the constraint stream.
     * The {@link Constraint#getConstraintPackage()} defaults to the package of the {@link PlanningSolution} class.
     *
     * @param constraintName never null, shows up in {@link ConstraintMatchTotal} during score justification
     * @return never null
     */
    Constraint asConstraint(String constraintName);

    /**
     * Builds a {@link Constraint} from the constraint stream.
     *
     * @param constraintName never null, shows up in {@link ConstraintMatchTotal} during score justification
     * @param constraintPackage never null
     * @return never null
     */
    Constraint asConstraint(String constraintPackage, String constraintName);

}
