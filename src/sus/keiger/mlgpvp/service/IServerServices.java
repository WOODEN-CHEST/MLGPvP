package sus.keiger.mlgpvp.service;

import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.player.IServerPlayerCollection;

import java.util.logging.Logger;

public interface IServerServices
{
    Plugin GetPlugin();
    Logger GetLogger();
    IServerPlayerCollection GetPlayerCollection();
    IEventDispatcher GetEventDispatcher();
}