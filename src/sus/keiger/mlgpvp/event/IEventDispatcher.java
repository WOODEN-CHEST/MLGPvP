package sus.keiger.mlgpvp.event;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public interface IEventDispatcher extends Listener
{
    PCPluginEvent<PlayerJoinEvent> GetJoinEvent();
    PCPluginEvent<PlayerQuitEvent> GetQuitEvent();
}