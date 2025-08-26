package sus.keiger.mlgpvp.game;

public class PlayerGameStats
{
    // Private fields.
    private double _distanceClimbed = 0d;
    private double _distanceFallen = 0d;
    private double _highestClimb = 0d;
    private long _shotsFired = 0L;
    private long _arrowsFired = 0L;
    private long _arrowsExploded = 0L;
    private long _directHits = 0L;
    private double _damageTaken = 0d;
    private double _damageDealt = 0d;
    private long _fallDamageCount = 0;
    private long _waterBucketsLanded = 0;


    // Methods.
    public void SetDistanceClimbed(double value)
    {
        GenericValidateDouble("distance climbed", value);
        _distanceClimbed = Math.max(0d, value);
    }

    public double GetDistanceClimbed()
    {
        return _distanceClimbed;
    }

    public void SetDistanceFallen(double value)
    {
        GenericValidateDouble("distance fallen", value);
        _distanceFallen = Math.max(0d, value);
    }

    public double GetDistanceFallen()
    {
        return _distanceFallen;
    }

    public void SetShotsFired(long value)
    {
        _shotsFired = Math.max(0L, value);
    }

    public long GetShotsFired()
    {
        return _shotsFired;
    }

    public void SetArrowsFired(long value)
    {
        _arrowsFired = Math.max(0L, value);
    }

    public long GetArrowsFired()
    {
        return _arrowsFired;
    }

    public void SetArrowsExploded(long value)
    {
        _arrowsExploded = Math.max(0L, value);
    }

    public long GetArrowsExploded()
    {
        return _arrowsExploded;
    }

    public void SetDirectHits(long value)
    {
        _directHits = Math.max(0L, value);
    }

    public long GetDirectHits()
    {
        return _directHits;
    }

    public void SetDamageTaken(double value)
    {
        GenericValidateDouble("damage taken", value);
        _damageTaken = Math.max(0d, value);
    }

    public double GetDamageTaken()
    {
        return _damageTaken;
    }

    public void SetDamageDealt(double value)
    {
        GenericValidateDouble("damage dealt", value);
        _damageDealt = Math.max(0d, value);
    }

    public double GetDamageDealt()
    {
        return _damageDealt;
    }

    public void SetFallDamageCount(long value)
    {
        _fallDamageCount = Math.max(0L, value);
    }

    public long GetFallDamageCount()
    {
        return _fallDamageCount;
    }

    public void SetWaterBucketsLanded(long value)
    {
        _waterBucketsLanded = Math.max(0L, value);
    }

    public long GetWaterBucketsLanded()
    {
        return _waterBucketsLanded;
    }

    public void SetHighestClimb(double value)
    {
        GenericValidateDouble("highest climb", value);
        _highestClimb = Math.max(0d, value);
    }

    public double GetHighestClimb()
    {
        return _highestClimb;
    }


    // Private methods.
    private void GenericValidateDouble(String fieldName, double value)
    {
        if (Double.isNaN(value) || Double.isInfinite(value))
        {
            throw new IllegalArgumentException("%s may not be NaN of infinite: %s"
                    .formatted(fieldName, value));
        }
    }
}