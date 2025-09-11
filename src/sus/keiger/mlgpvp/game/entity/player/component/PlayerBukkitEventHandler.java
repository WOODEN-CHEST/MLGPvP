package sus.keiger.mlgpvp.game.entity.player.component;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.ExplosiveWeaponBuilder;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.*;
import sus.keiger.plugincommon.PCPluginEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/* This class should be split into many smaller classes which each focuses on a specific event instead of
* whatever this is. */
public class PlayerBukkitEventHandler extends GameEntityComponent<GamePlayerEntity>
{
    // Private fields.
    private final PCPluginEvent<GamePlayerEmptyBucketEvent> _emptyBucketEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GamePlayerFireArrowEvent> _fireArrowEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GamePlayerHitByArrowEvent> _hitByArrowEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GamePlayerBlockPlaceEvent> _blockPlaceEvent = new PCPluginEvent<>();


    // Constructors.
    public PlayerBukkitEventHandler(GamePlayerEntity entity)
    {
        super(entity);
    }


    // Methods.
    public PCPluginEvent<GamePlayerEmptyBucketEvent> GetEmptyBucketEvent()
    {
        return _emptyBucketEvent;
    }

    public PCPluginEvent<GamePlayerFireArrowEvent> GetFireArrowEvent()
    {
        return _fireArrowEvent;
    }

    public PCPluginEvent<GamePlayerHitByArrowEvent> GetHitByArrowEvent()
    {
        return _hitByArrowEvent;
    }

    public PCPluginEvent<GamePlayerBlockPlaceEvent> GetBlockPlaceEvent()
    {
        return _blockPlaceEvent;
    }


    // Private methods.
    private void OnEntityShootBowEvent(EntityShootBowEvent event)
    {
        if (!event.getEntity().equals(GetEntity().GetUnderlyingEntity())
                || !(event.getProjectile() instanceof Arrow ArrowProjectile))
        {
            return;
        }

        ExplosiveWeaponBuilder.GetWeaponStats(event.getBow()).ifPresent(stats ->
        {
            GameArrowEntity SpawnedEntity = new GameArrowEntity(
                    GetGameInstance(),
                    ArrowProjectile,
                    GetEntity(),
                    stats.StrengthScale());
            GetGameInstance().AddEntity(SpawnedEntity);

            SpawnedEntity.SetMotion(SpawnedEntity.GetMotion().multiply(GetConfigValues().ArrowSpeedMultiplier));

            _fireArrowEvent.FireEvent(new GamePlayerFireArrowEvent(GetEntity(), SpawnedEntity));
        });
    }

    private void OnEmptyBucketEvent(PlayerBucketEmptyEvent event)
    {
        if (!event.getPlayer().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        _emptyBucketEvent.FireEvent(new GamePlayerEmptyBucketEvent(GetEntity(), event));
    }

    private boolean IsDamageBlocked(DamageCause cause)
    {
        return ((cause == DamageCause.ENTITY_ATTACK) && !GetConfigValues().IsMeleeDamageEnabled)
                || ((cause == DamageCause.PROJECTILE) && !GetConfigValues().IsArrowDamageEnabled);
    }

    private void OnEntityDamageEvent(EntityDamageEvent event)
    {
        if (!event.getEntity().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        if (IsDamageBlocked(event.getCause()))
        {
            event.setCancelled(true);
            return;
        }

        GameEntity SourceEntity = (event instanceof EntityDamageByEntityEvent ByEntityEvent) ?
                GetGameInstance().GetEntity(ByEntityEvent.getDamager()).orElse(null) : null;

        GetEntity().GetDamageEvent().FireEvent(new GamePlayerDamageEvent(GetEntity(), event.getDamage(), SourceEntity));
    }

    private void OnItemConsumeEvent(PlayerItemConsumeEvent event)
    {
        if (!event.getPlayer().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        if ((event.getItem().getType() == Material.CHORUS_FRUIT) && !GetConfigValues().IsChorusFruitEnabled)
        {
            event.setCancelled(true);
        }
    }

    private void OnLaunchProjectileEvent(PlayerLaunchProjectileEvent event)
    {
        if (!event.getPlayer().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        if ((event.getProjectile() instanceof EnderPearl) && !GetConfigValues().IsEnderPearlsEnabled)
        {
            event.setCancelled(true);
        }
    }

    private void OnBlockPlaceEvent(BlockPlaceEvent event)
    {
        if (!event.getPlayer().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        _blockPlaceEvent.FireEvent(new GamePlayerBlockPlaceEvent(GetEntity(), event));
    }





    // Inherited methods.
    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        dispatcher.GetShootBowEvent().Subscribe(this, this::OnEntityShootBowEvent);
        dispatcher.GetPlayerBucketEmptyEvent().Subscribe(this, this::OnEmptyBucketEvent);
        dispatcher.GetEntityDamageEvent().Subscribe(this, this::OnEntityDamageEvent);
        dispatcher.GetItemConsumeEvent().Subscribe(this, this::OnItemConsumeEvent);
        dispatcher.GetLaunchProjectileEvent().Subscribe(this, this::OnLaunchProjectileEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        dispatcher.GetShootBowEvent().Unsubscribe(this);
        dispatcher.GetPlayerBucketEmptyEvent().Unsubscribe(this);
        dispatcher.GetEntityDamageEvent().Unsubscribe(this);
        dispatcher.GetItemConsumeEvent().Unsubscribe(this);
        dispatcher.GetLaunchProjectileEvent().Unsubscribe(this);
    }
}