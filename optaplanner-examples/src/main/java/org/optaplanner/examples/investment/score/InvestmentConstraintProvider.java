package org.optaplanner.examples.investment.score;

import java.util.function.Function;

import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import org.optaplanner.examples.investment.domain.AssetClassAllocation;
import org.optaplanner.examples.investment.domain.InvestmentParametrization;
import org.optaplanner.examples.investment.domain.Region;
import org.optaplanner.examples.investment.domain.Sector;

public class InvestmentConstraintProvider implements ConstraintProvider {

    private static final String CONSTRAINT_PACKAGE = "org.optaplanner.examples.investment.solver";

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                assetsDeviationGreaterThanMaximumPenalty(constraintFactory),
                regionQuantityGreaterThanMaximumPenalty(constraintFactory),
                sectorQuantityGreaterThanMaximumPenalty(constraintFactory),
                assetExpectedReturnReward(constraintFactory)
        };
    }

    private Constraint assetsDeviationGreaterThanMaximumPenalty(ConstraintFactory cf) {
        return cf.forEach(AssetClassAllocation.class)
                .join(AssetClassAllocation.class)
                .groupBy(ConstraintCollectors.sumLong(AssetClassAllocation::calculateSquaredStandardDeviationFemtosFromTo))
                .join(InvestmentParametrization.class)
                .filter((deviation,
                        parametrization) -> deviation > parametrization.calculateSquaredStandardDeviationFemtosMaximum())
                .penalizeLong(HardSoftLongScore.ONE_HARD,
                        (deviation, parametrization) -> deviation
                                - parametrization.calculateSquaredStandardDeviationFemtosMaximum())
                .asConstraint(CONSTRAINT_PACKAGE, "Standard deviation maximum");
    }

    private Constraint regionQuantityGreaterThanMaximumPenalty(ConstraintFactory cf) {
        return cf.forEach(Region.class)
                .join(AssetClassAllocation.class, Joiners.equal(Function.identity(), AssetClassAllocation::getRegion))
                .groupBy((region, asset) -> region, ConstraintCollectors.sumLong((region, asset) -> asset.getQuantityMillis()))
                .filter((region, totalQuantity) -> totalQuantity > region.getQuantityMillisMaximum())
                .penalizeLong(HardSoftLongScore.ONE_HARD,
                        (region, totalQuantity) -> totalQuantity - region.getQuantityMillisMaximum())
                .asConstraint(CONSTRAINT_PACKAGE, "Region quantity maximum");
    }

    private Constraint sectorQuantityGreaterThanMaximumPenalty(ConstraintFactory cf) {
        return cf.forEach(Sector.class)
                .join(AssetClassAllocation.class, Joiners.equal(Function.identity(), AssetClassAllocation::getSector))
                .groupBy((sector, asset) -> sector, ConstraintCollectors.sumLong((sector, asset) -> asset.getQuantityMillis()))
                .filter((sector, totalQuantity) -> totalQuantity > sector.getQuantityMillisMaximum())
                .penalizeLong(HardSoftLongScore.ONE_HARD,
                        (sector, totalQuantity) -> totalQuantity - sector.getQuantityMillisMaximum())
                .asConstraint(CONSTRAINT_PACKAGE, "Sector quantity maximum");
    }

    private Constraint assetExpectedReturnReward(ConstraintFactory cf) {
        return cf.forEach(AssetClassAllocation.class)
                .rewardLong(HardSoftLongScore.ONE_SOFT, AssetClassAllocation::getQuantifiedExpectedReturnMicros)
                .asConstraint(CONSTRAINT_PACKAGE, "Maximize expected return");
    }
}
