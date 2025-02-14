package org.optaplanner.constraint.streams.common.quad;

import java.math.BigDecimal;

import org.optaplanner.constraint.streams.common.ScoreImpactType;
import org.optaplanner.core.api.function.QuadFunction;
import org.optaplanner.core.api.function.ToIntQuadFunction;
import org.optaplanner.core.api.function.ToLongQuadFunction;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.quad.QuadConstraintBuilder;
import org.optaplanner.core.api.score.stream.quad.QuadConstraintStream;

public interface InnerQuadConstraintStream<A, B, C, D> extends QuadConstraintStream<A, B, C, D> {

    /**
     * This method will return true if the constraint stream is guaranteed to only produce distinct tuples.
     * See {@link #distinct()} for details.
     *
     * @return true if the guarantee of distinct tuples is provided
     */
    boolean guaranteesDistinct();

    @Override
    default QuadConstraintStream<A, B, C, D> distinct() {
        if (guaranteesDistinct()) {
            return this;
        } else {
            return groupBy((a, b, c, d) -> a, (a, b, c, d) -> b, (a, b, c, d) -> c, (a, b, c, d) -> d);
        }
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> penalize(Score<?> constraintWeight, ToIntQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.PENALTY);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> penalizeLong(Score<?> constraintWeight,
            ToLongQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.PENALTY);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> penalizeBigDecimal(Score<?> constraintWeight,
            QuadFunction<A, B, C, D, BigDecimal> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.PENALTY);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> penalizeConfigurable(ToIntQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.PENALTY);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> penalizeConfigurableLong(ToLongQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.PENALTY);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D>
            penalizeConfigurableBigDecimal(QuadFunction<A, B, C, D, BigDecimal> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.PENALTY);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> reward(Score<?> constraintWeight, ToIntQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.REWARD);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> rewardLong(Score<?> constraintWeight,
            ToLongQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.REWARD);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> rewardBigDecimal(Score<?> constraintWeight,
            QuadFunction<A, B, C, D, BigDecimal> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.REWARD);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> rewardConfigurable(ToIntQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.REWARD);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> rewardConfigurableLong(ToLongQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.REWARD);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> rewardConfigurableBigDecimal(QuadFunction<A, B, C, D, BigDecimal> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.REWARD);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> impact(Score<?> constraintWeight, ToIntQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.MIXED);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> impactLong(Score<?> constraintWeight,
            ToLongQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.MIXED);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> impactBigDecimal(Score<?> constraintWeight,
            QuadFunction<A, B, C, D, BigDecimal> matchWeigher) {
        return innerImpact(constraintWeight, matchWeigher, ScoreImpactType.MIXED);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> impactConfigurable(ToIntQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.MIXED);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> impactConfigurableLong(ToLongQuadFunction<A, B, C, D> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.MIXED);
    }

    @Override
    default QuadConstraintBuilder<A, B, C, D> impactConfigurableBigDecimal(QuadFunction<A, B, C, D, BigDecimal> matchWeigher) {
        return innerImpact(null, matchWeigher, ScoreImpactType.MIXED);
    }

    QuadConstraintBuilder<A, B, C, D> innerImpact(Score<?> constraintWeight, ToIntQuadFunction<A, B, C, D> matchWeigher,
            ScoreImpactType scoreImpactType);

    QuadConstraintBuilder<A, B, C, D> innerImpact(Score<?> constraintWeight, ToLongQuadFunction<A, B, C, D> matchWeigher,
            ScoreImpactType scoreImpactType);

    QuadConstraintBuilder<A, B, C, D> innerImpact(Score<?> constraintWeight, QuadFunction<A, B, C, D, BigDecimal> matchWeigher,
            ScoreImpactType scoreImpactType);

    @Override
    default Constraint penalize(String constraintName, Score<?> constraintWeight) {
        return penalize(constraintWeight)
                .asConstraint(constraintName);
    }

    @Override
    default Constraint penalize(String constraintPackage, String constraintName, Score<?> constraintWeight) {
        return penalize(constraintWeight)
                .asConstraint(constraintPackage, constraintName);
    }

    @Override
    default Constraint penalizeConfigurable(String constraintName) {
        return penalizeConfigurable()
                .asConstraint(constraintName);
    }

    @Override
    default Constraint penalizeConfigurable(String constraintPackage, String constraintName) {
        return penalizeConfigurable()
                .asConstraint(constraintPackage, constraintName);
    }

    @Override
    default Constraint reward(String constraintName, Score<?> constraintWeight) {
        return reward(constraintWeight)
                .asConstraint(constraintName);
    }

    @Override
    default Constraint reward(String constraintPackage, String constraintName, Score<?> constraintWeight) {
        return reward(constraintWeight)
                .asConstraint(constraintPackage, constraintName);
    }

    @Override
    default Constraint rewardConfigurable(String constraintName) {
        return rewardConfigurable()
                .asConstraint(constraintName);
    }

    @Override
    default Constraint rewardConfigurable(String constraintPackage, String constraintName) {
        return penalizeConfigurable()
                .asConstraint(constraintPackage, constraintName);
    }

    @Override
    default Constraint impact(String constraintName, Score<?> constraintWeight) {
        return impact(constraintWeight)
                .asConstraint(constraintName);
    }

    @Override
    default Constraint impact(String constraintPackage, String constraintName, Score<?> constraintWeight) {
        return impact(constraintWeight)
                .asConstraint(constraintPackage, constraintName);
    }

}
