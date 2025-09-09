package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Optional;

public class GamePlayerTakeDamageEvent extends GamePlayerEntityEvent
{
    // Private fields.
    private final EntityDamageEvent _event;


    // Constructors.
    public GamePlayerTakeDamageEvent(GamePlayerEntity entity, EntityDamageEvent event)
    {
        super(entity, event);
        _event = event;
    }


    // Methods.
    public double GetDamageAmount()
    {
        return _event.getDamage();
    }

    public double GetFinalDamageAmount()
    {
        return _event.getFinalDamage();
    }

    public Optional<GamePlayerEntity> GetCausePlayer()
    {
        if (_event instanceof EntityDamageByEntityEvent ByEntityEvent)
        {
            Entity SourceEntity;
            if ((ByEntityEvent.getDamager() instanceof Projectile ProjectileEntity)
                    && (ProjectileEntity.getShooter() instanceof Entity EntityShooter))
            {
                SourceEntity = EntityShooter;
            }
            else
            {
                SourceEntity = ByEntityEvent.getDamager();
            }


            return GetGameInstance().GetEntity(SourceEntity)
                    .filter(entity -> entity instanceof GamePlayerEntity)
                    .map(entity -> (GamePlayerEntity)entity);
        }
        return Optional.empty();
    }

    public EntityDamageEvent.DamageCause GetDamageCause()
    {
        return _event.getCause();
    }

    public boolean IsProjectileHit()
    {
        return (_event instanceof EntityDamageByEntityEvent ByEntityEvent)
                && (ByEntityEvent.getDamager() instanceof Projectile ProjectileDamager)
                && (GetEntity().GetUnderlyingEntity().equals(ProjectileDamager.getShooter()));
    }
}