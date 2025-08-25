package sus.keiger.mlgpvp.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public class DefaultEventDispatcher implements IEventDispatcher
{
    // Private fields.
    private final PCPluginEvent<PlayerJoinEvent> _joinEvent = new PCPluginEvent<>();
    private final PCPluginEvent<PlayerQuitEvent> _quitEvent = new PCPluginEvent<>();
    private final PCPluginEvent<EntityShootBowEvent> _shootBowEvent = new PCPluginEvent<>();


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
}