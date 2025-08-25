package sus.keiger.mlgpvp.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public class DefaultEventDispatcher implements IEventDispatcher
{
    // Private fields.
    private final PCPluginEvent<PlayerJoinEvent> _joinEvent = new PCPluginEvent<>();
    private final PCPluginEvent<PlayerQuitEvent> _quitEvent = new PCPluginEvent<>();
    private final PCPluginEvent<EntityShootBowEvent> _shootBowEvent = new PCPluginEvent<>();
    private final PCPluginEvent<ProjectileHitEvent> _projectileHitEvent = new PCPluginEvent<>();
    private final PCPluginEvent<EntityDamageEvent> _entityDamageEvent = new PCPluginEvent<>();
    private final PCPluginEvent<EntityDeathEvent> _entityDeathEvent = new PCPluginEvent<>();


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
    public void OnEntityDeathEvent(EntityDeathEvent event)
    {
        _entityDeathEvent.FireEvent(event);
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
    public PCPluginEvent<EntityDeathEvent> GetEntityDeathEvent()
    {
        return _entityDeathEvent;
    }
}