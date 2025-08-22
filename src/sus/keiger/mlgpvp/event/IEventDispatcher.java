package sus.keiger.mlgpvp.event;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public interface IEventDispatcher
{
    PCPluginEvent<PlayerJoinEvent> GetJoinEvent();
    PCPluginEvent<PlayerQuitEvent> GetQuitEvent();
}