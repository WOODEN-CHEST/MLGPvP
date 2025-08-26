package sus.keiger.mlgpvp.game;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.plugincommon.PCMath;

import java.util.Random;
import java.util.function.BiConsumer;

public class CustomExplosionCreator implements IExplosionCreator
{
    // Private static fields.
    private static final double RADIUS_PER_STRENGTH = 3.5d; // Main factor in radius calculations.
    private static final double DAMAGE_PER_STRENGTH = PCMath.HeartsToHealth(2.0d);
    private static final double KNOCKBACK_RADIUS_SCALE = 6d;
    private static final double DAMAGE_RADIUS_SCALE = 2.5d;
    private static final double BLOCK_RADIUS_SCALE = 1d;


    private static final double PARTICLE_RADIUS_SCALE = 0.85d;
    private static final double PARTICLES_PER_BLOCK_EXPLOSION = 0.25d;
    private static final double PARTICLES_PER_BLOCK_DUST = 4d;
    private static final double PARTICLE_SPEED_EXPLOSION = 0d;
    private static final double PARTICLE_SPEED_DUST = 0.2d;

    private static final double MAX_CHANCE = 1d;
    private static final double MIN_CHANCE = 0d;
    private static final double RADIUS_FALLOFF_EXPONENT = 3d; // Used so that the chance of block destruction isn't linear.

    private static final float EXPLOSION_SOUND_VOLUME = 1.25f;
    private static final float EXPLOSION_SOUND_PITCH = 0.8f;

    private static final double MOTION_HORIZONTAL_PER_STRENGTH = 0.75d;
    private static final double MOTION_VERTICAL_PER_STRENGTH = 2d;


    // Private fields.
    private final IGameInstanceExtended _gameInstance;
    private static final Random _rng = new Random();


    // Constructors.

    public CustomExplosionCreator(IGameInstanceExtended gameInstance)
    {
        _gameInstance = gameInstance;
    }


    // Private methods.
    private double FactorInFalloff(double linearValue)
    {
        return Math.pow(linearValue, RADIUS_FALLOFF_EXPONENT);
    }

    private double DistanceToFactor(double radius, double distance)
    {
        return Math.min(MAX_CHANCE, Math.max(MIN_CHANCE, MAX_CHANCE - FactorInFalloff(distance / radius)));
    }

    private void CreateExplosionContent(Location location, double strength)
    {
        double MaxDelta = strength * RADIUS_PER_STRENGTH * PARTICLE_RADIUS_SCALE;

        double Volume = (MaxDelta * MaxDelta * MaxDelta);

        int CountExplosion = (int)Math.round(Volume * PARTICLES_PER_BLOCK_EXPLOSION);
        _gameInstance.SpawnParticle(Particle.EXPLOSION, location, MaxDelta, MaxDelta, MaxDelta,
                CountExplosion, PARTICLE_SPEED_EXPLOSION, null);

        int CountDust = (int)Math.round(Volume * PARTICLES_PER_BLOCK_DUST);
        _gameInstance.SpawnParticle(Particle.DUST_PLUME, location, MaxDelta, MaxDelta, MaxDelta,
                CountDust, PARTICLE_SPEED_DUST, null);

        _gameInstance.PlaySound(Sound.ENTITY_GENERIC_EXPLODE,
                location,
                SoundCategory.NEUTRAL,
                EXPLOSION_SOUND_VOLUME,
                EXPLOSION_SOUND_PITCH);
    }

    private void DestroyBlocks(Location location, double strength)
    {
        double RadiusDecimal = strength * RADIUS_PER_STRENGTH * BLOCK_RADIUS_SCALE;
        int Radius = (int)Math.round(RadiusDecimal);

        for (int x = -Radius; x < Radius; x++)
        {
            for (int y = -Radius; y < Radius; y++)
            {
                for (int z = -Radius; z < Radius; z++)
                {
                    int FinalX = location.getBlockX() + x;
                    int FinalY = location.getBlockY() + y;
                    int FinalZ = location.getBlockZ() + z;
                    DestroySingleBlock(location,
                            RadiusDecimal,
                            location.getWorld().getBlockAt(FinalX, FinalY, FinalZ));
                }
            }
        }
    }

    private void DestroySingleBlock(Location explosionLocation,
                                    double radiusDecimal,
                                    Block block)
    {
        double ChanceToDestroy = DistanceToFactor(radiusDecimal, block.getLocation().distance(explosionLocation));

        if (_rng.nextDouble() <= ChanceToDestroy)
        {
            /* This destroys evey block equally (including blocks like bedrock), but that is intentional. */
            block.setType(Material.AIR);
        }
    }

    private void DealDamage(Location explosionLocation, double strength)
    {
        ModifyPlayersBasedOnFactor(explosionLocation, strength, DAMAGE_RADIUS_SCALE, (player, factor) ->
        {
            if (!player.GetIsAlive())
            {
                return;
            }

            player.Damage(factor * DAMAGE_PER_STRENGTH * strength);
        });
    }

    private void DealKnockback(Location explosionLocation, double strength)
    {
        ModifyPlayersBasedOnFactor(explosionLocation, strength, KNOCKBACK_RADIUS_SCALE, (player, factor) ->
        {
            if (!player.GetIsAlive())
            {
                return;
            }

            Vector ToPlayerVector = player.GetCenter().subtract(explosionLocation).toVector();

            final double MARGIN_OF_ERROR = 0.000001d;
            if (ToPlayerVector.length() <= MARGIN_OF_ERROR)
            {
                ToPlayerVector.setX(1d);
                ToPlayerVector.setY(0d);
                ToPlayerVector.setZ(0d);
            }
            else
            {
                ToPlayerVector.normalize();
            }

            Vector AddedMotion = ToPlayerVector.multiply(new Vector(
                    MOTION_HORIZONTAL_PER_STRENGTH,
                    MOTION_VERTICAL_PER_STRENGTH,
                    MOTION_HORIZONTAL_PER_STRENGTH))
                    .multiply(factor * strength);

            player.AddMotion(AddedMotion);

            if (AddedMotion.getY() > 0d)
            {
                player.MarkClimbStart();
            }
        });
    }

    private void ModifyPlayersBasedOnFactor(Location explosionLocation,
                                            double strength,
                                            double radiusScale,
                                            BiConsumer<GamePlayerEntity, Double> function)
    {
        double Radius = strength * RADIUS_PER_STRENGTH * radiusScale;

        for (GameEntity Entity : _gameInstance.GetEntities())
        {
            if (!(Entity instanceof GamePlayerEntity PlayerEntity))
            {
                continue;
            }

            double Factor = DistanceToFactor(Radius, explosionLocation.distance(Entity.GetCenter()));
            if (Factor > 0d)
            {
                function.accept(PlayerEntity, Factor);
            }
        }
    }


    // Inherited methods.
    @Override
    public void CreateExplosion(ExplosionCreateOptions options)
    {
        Location TargetLocation = options.GetLocation();

        double FinalBlockStrength = options.GetBlockScale() * options.GetStrengthScale();
        double FinalDamageStrength = options.GetDamageScale() * options.GetStrengthScale();
        double FinalKnockbackStrength = options.GetKnockbackScale() * options.GetStrengthScale();

        DestroyBlocks(TargetLocation, FinalBlockStrength);
        //DealDamage(TargetLocation, FinalDamageStrength);
        DealKnockback(TargetLocation, FinalKnockbackStrength);
        CreateExplosionContent(TargetLocation, FinalBlockStrength);
    }
}