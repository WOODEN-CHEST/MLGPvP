package sus.keiger.mlgpvp.game;

import org.bukkit.Location;
import sus.keiger.mlgpvp.game.entity.GameEntity;

import java.util.Objects;
import java.util.Optional;

/**
 * Holds various settings for how an explosion should be created in the {@link IExplosionCreator}
 */
public class ExplosionCreateOptions
{
    // Private fields.
    private final Location _location;
    private final double _strengthScale;
    private final double _blockScale;
    private final double _damageScale;
    private final double _knockbackScale;
    private final GameEntity _sourceEntity;


    // Constructors.
    private ExplosionCreateOptions(Location location,
                                   double strengthScale,
                                   double blockScale,
                                   double damageScale,
                                   double knockbackScale,
                                   GameEntity sourceEntity)
    {
        _location = Objects.requireNonNull(location, "location is null");
        _strengthScale = strengthScale;
        _blockScale = blockScale;
        _damageScale = damageScale;
        _knockbackScale = knockbackScale;
        _sourceEntity = sourceEntity;

    }


    /**
     * @return The location of the exlosion.
     */
    // Methods.
    public Location GetLocation()
    {
        return _location.clone();
    }

    /**
     * @return The overall explosion strength scale (multiplier) for all explosion related settings.
     * <br>Typically affects the explosion's radius scale.
     */
    public double GetStrengthScale()
    {
        return _strengthScale;
    }


    /**
     * @return The explosion strength scale (multiplier) for only block destruction.
     * <br>Typically affects the radius scale of block destruction.
     */
    public double GetBlockScale()
    {
        return _blockScale;
    }

    /**
     * @return The explosion strength scale (multiplier) for only player damage.
     * <br>Typically affects the radius scale of player damage.
     */
    public double GetDamageScale()
    {
        return _damageScale;
    }

    /**
     * @return The explosion strength scale (multiplier) for only entity knockback.
     * <br>Typically affects the radius scale of entity knockback.
     */
    public double GetKnockbackScale()
    {
        return _knockbackScale;
    }

    /**
     * @return The entity which caused the explosion, returns the entity which directly did it (so if a player shot
     * an arrow, and it exploded, this returns the arrow).
     */
    public Optional<GameEntity> GetSourceEntity()
    {
        return Optional.ofNullable(_sourceEntity);
    }


    /**
     * Creates explosion settings from the given values.
     * @param location The location of the explosion.
     * @param strengthScale The overall strength multiplier.
     * @param blockScale The block damage strength multiplier.
     * @param damageScale The entity damage strength multiplier.
     * @param knockbackScale The entity knockback strength multiplier.
     * @param sourceEntity The entity which caused the explosion, may be <code>null</code>.
     * @return Newly created options.
     */
    // Static methods.
    public static ExplosionCreateOptions Create(Location location,
                                                double strengthScale,
                                                double blockScale,
                                                double damageScale,
                                                double knockbackScale,
                                                GameEntity sourceEntity)
    {
        return new ExplosionCreateOptions(location,
                strengthScale,
                blockScale,
                damageScale,
                knockbackScale,
                sourceEntity);
    }

    /**
     * Creates explosion settings by retrieving relevant data from a game's config.
     * @param location The location of the explosion.
     * @param strengthScale The overall strength multiplier.
     * @param sourceEntity The entity which caused the explosion, may be <code>null</code>.
     * @param values The config from which to pull explosion settings.
     * @return Newly created options.
     */
    public static ExplosionCreateOptions CreateFromValues(Location location,
                                                          double strengthScale,
                                                          GameEntity sourceEntity,
                                                          GameInstanceValues values)
    {
        return new ExplosionCreateOptions(location,
                strengthScale,
                values.ExplosionBlockDamageScale,
                values.ExplosionDamageScale * (values.IsExplosionDamageEnabled ? 1d : 0d),
                values.ExplosionKnockbackScale,
                sourceEntity);
    }
}