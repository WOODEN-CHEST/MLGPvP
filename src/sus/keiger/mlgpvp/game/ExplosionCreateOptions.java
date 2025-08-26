package sus.keiger.mlgpvp.game;

import org.bukkit.Location;

import java.util.Objects;

public class ExplosionCreateOptions
{
    // Private fields.
    private final Location _location;
    private final double _strengthScale;
    private final double _blockScale;
    private final double _damageScale;
    private final double _knockbackScale;


    // Constructors.
    private ExplosionCreateOptions(Location location,
                                   double strengthScale,
                                   double blockScale,
                                   double damageScale,
                                   double knockbackScale)
    {
        _location = Objects.requireNonNull(location, "location is null");
        _strengthScale = strengthScale;
        _blockScale = blockScale;
        _damageScale = damageScale;
        _knockbackScale = knockbackScale;

    }


    // Methods.
    public Location GetLocation()
    {
        return _location.clone();
    }

    public double GetStrengthScale()
    {
        return _strengthScale;
    }

    public double GetBlockScale()
    {
        return _blockScale;
    }

    public double GetDamageScale()
    {
        return _damageScale;
    }

    public double GetKnockbackScale()
    {
        return _knockbackScale;
    }


    // Static methods.
    public static ExplosionCreateOptions Create(Location location,
                                                double strengthScale,
                                                double blockScale,
                                                double damageScale,
                                                double knockbackScale)
    {
        return new ExplosionCreateOptions(location,
                strengthScale,
                blockScale,
                damageScale,
                knockbackScale);
    }

    public static ExplosionCreateOptions CreateFromValues(Location location,
                                                          double strengthScale,
                                                          GameInstanceValues values)
    {
        return new ExplosionCreateOptions(location,
                strengthScale,
                values.ExplosionBlockDamageScale,
                values.ExplosionDamageScale,
                values.ExplosionKnockbackScale);
    }
}