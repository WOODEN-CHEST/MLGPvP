package sus.keiger.mlgpvp.event;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sus.keiger.plugincommon.EmptyEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public class DefaultEventDispatcher implements IEventDispatcher
{
    // Private fields.
    private final PCPluginEvent<PlayerJoinEvent> _joinEvent = new PCPluginEvent<>();
    private final PCPluginEvent<PlayerQuitEvent> _quitEvent = new PCPluginEvent<>();
    private final PCPluginEvent<EntityShootBowEvent> _shootBowEvent = new PCPluginEvent<>();
    private final PCPluginEvent<ProjectileHitEvent> _projectileHitEvent = new PCPluginEvent<>();
    private final PCPluginEvent<EntityDamageEvent> _entityDamageEvent = new PCPluginEvent<>();
    private final PCPluginEvent<PlayerDeathEvent> _playerDeathEvent = new PCPluginEvent<>();
    private final PCPluginEvent<EmptyEvent> _tickEvent = new PCPluginEvent<>();
    private final PCPluginEvent<PlayerBucketEmptyEvent> _playerEmptyBucketEvent = new PCPluginEvent<>();
    private final PCPluginEvent<PlayerAdvancementCriterionGrantEvent> _criteriaGrantEvent = new PCPluginEvent<>();
    private final EmptyEvent _emptyEventArgs = new EmptyEvent();


    // Methods.
    @EventHandler
    public void OnPlayerJoinEvent(PlayerJoinEvent event)
    {
        _joinEvent.FireEvent(event);
    }

    @EventHandler
    public void OnPlayerJoinEvent(PlayerQuitEvent event)
    {
        _quitEvent.FireEvent(event);
    }

    @EventHandler
    public void OnEntityShootBowEvent(EntityShootBowEvent event)
    {
        _shootBowEvent.FireEvent(event);
    }

    @EventHandler
    public void OnProjectileHitEvent(ProjectileHitEvent event)
    {
        _projectileHitEvent.FireEvent(event);
    }

    @EventHandler
    public void OnEntityDamageEvent(EntityDamageEvent event)
    {
        _entityDamageEvent.FireEvent(event);
    }

    @EventHandler
    public void OnEntityDeathEvent(PlayerDeathEvent event)
    {
        _playerDeathEvent.FireEvent(event);
    }

    @EventHandler
    public void OnTickStartEvent(ServerTickStartEvent event)
    {
        _tickEvent.FireEvent(_emptyEventArgs);
    }

    @EventHandler
    public void OnPlayerEmptyBucketEvent(PlayerBucketEmptyEvent event)
    {
        _playerEmptyBucketEvent.FireEvent(event);
    }

    @EventHandler
    public void OnAdvancementCriteriaGrantEvent(PlayerAdvancementCriterionGrantEvent event)
    {
        _criteriaGrantEvent.FireEvent(event);
    }



    // Inherited methods.
    @Override
    public PCPluginEvent<PlayerJoinEvent> GetJoinEvent()
    {
        return _joinEvent;
    }

    @Override
    public PCPluginEvent<PlayerQuitEvent> GetQuitEvent()
    {
        return _quitEvent;
    }

    @Override
    public PCPluginEvent<EntityShootBowEvent> GetShootBowEvent()
    {
        return _shootBowEvent;
    }

    @Override
    public PCPluginEvent<ProjectileHitEvent> GetProjectileHitEvent()
    {
        return _projectileHitEvent;
    }

    @Override
    public PCPluginEvent<EntityDamageEvent> GetEntityDamageEvent()
    {
        return _entityDamageEvent;
    }

    @Override
    public PCPluginEvent<PlayerDeathEvent> GetEntityDeathEvent()
    {
        return _playerDeathEvent;
    }

    @Override
    public PCPluginEvent<EmptyEvent> GetTickEvent()
    {
        return _tickEvent;
    }

    @Override
    public PCPluginEvent<PlayerBucketEmptyEvent> GetPlayerBucketEmptyEvent()
    {
        return _playerEmptyBucketEvent;
    }

    @Override
    public PCPluginEvent<PlayerAdvancementCriterionGrantEvent> GetAdvancementCriteriaGrantEvent()
    {
        return _criteriaGrantEvent;
    }
}