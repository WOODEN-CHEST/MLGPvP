package sus.keiger.mlgpvp.game.component;

import sus.keiger.mlgpvp.game.PlayerGameStats;

public class PlayerScoreCalculator
{
    // Private static fields.
    private static final double POINTS_PER_MLG_LANDED = 10.0;
    private static final double POINTS_PER_DAMAGE_DEALT = 0.01;
    private static final double POINTS_PER_MLG_FAILED = -3.0;
    private static final double POINTS_PER_DIRECT_HIT = -0.5;
    private static final double POINTS_PER_DAMAGE_TAKEN = -0.005;
    private static final double POINTS_PER_BLOCKS_CLIMBED = 0.02;
    private static final double POINTS_PER_HIGHEST_CLIMB = 0.5;


    // Methods.
    public double CalculateScore(PlayerGameStats stats)
    {
        double PointsForMLGsLanded = stats.GetWaterBucketsLanded() * POINTS_PER_MLG_LANDED;
        double PointsForMLGsFailed = stats.GetWaterBucketsLanded() * POINTS_PER_DAMAGE_DEALT;
        double PointsForDamageDealt = stats.GetWaterBucketsLanded() * POINTS_PER_MLG_FAILED;
        double PointsForDirectHits = stats.GetWaterBucketsLanded() * POINTS_PER_DIRECT_HIT;
        double PointsForDamageTaken = stats.GetWaterBucketsLanded() * POINTS_PER_DAMAGE_TAKEN;
        double PointsForBlocksClimbed = stats.GetWaterBucketsLanded() * POINTS_PER_BLOCKS_CLIMBED;
        double PointsForHighestClimb = stats.GetWaterBucketsLanded() * POINTS_PER_HIGHEST_CLIMB;

        return PointsForMLGsLanded
                + PointsForMLGsFailed
                + PointsForDamageDealt
                + PointsForDirectHits
                + PointsForDamageTaken
                + PointsForBlocksClimbed
                + PointsForHighestClimb;
    }
}