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
    private final boolean _mayDestroyInvulnerableBlocks;


    // Constructors.
    private ExplosionCreateOptions(Location location,
                                   double strengthScale,
                                   double blockScale,
                                   double damageScale,
                                   double knockbackScale,
                                   boolean mayDestroyInvulnerableBlocks)
    {
        _location = Objects.requireNonNull(location, "location is null");
        _strengthScale = strengthScale;
        _blockScale = blockScale;
        _damageScale = damageScale;
        _knockbackScale = knockbackScale;
        _mayDestroyInvulnerableBlocks = mayDestroyInvulnerableBlocks;

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

    public boolean GetMayDestroyInvulnerableBlocks()
    {
        return _mayDestroyInvulnerableBlocks;
    }


    // Static methods.
    public static ExplosionCreateOptions Create(Location location,
                                                double strengthScale,
                                                double blockScale,
                                                double damageScale,
                                                double knockbackScale,
                                                boolean mayDestroyInvulnerableBlocks)
    {
        return new ExplosionCreateOptions(location,
                strengthScale,
                blockScale,
                damageScale,
                knockbackScale,
                mayDestroyInvulnerableBlocks);
    }

    public static ExplosionCreateOptions CreateFromValues(Location location,
                                                          double strengthScale,
                                                          GameInstanceValues values)
    {
        return new ExplosionCreateOptions(location,
                strengthScale,
                values.ExplosionBlockDamageScale * strengthScale,
                values.ExplosionDamageScale * strengthScale,
                values.ExplosionKnockbackScale * strengthScale,
                values.MayExplosionsDestroyInvulnerableBlocks);
    }
}