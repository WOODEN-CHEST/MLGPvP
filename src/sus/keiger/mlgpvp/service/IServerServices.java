package sus.keiger.mlgpvp.service;

import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.player.IServerPlayerCollection;

import java.util.logging.Logger;

/**
 * Server services are useful singletons which can be used to perform actions on the server.
 * <br>Services are meant to be standalone objects which do not require other services to function.
 * <br>This is a very bad program design btw.
 */
public interface IServerServices
{

    /**
     * @return The MLGPvP plugin.
     */
    Plugin GetPlugin();


    /**
     * @return The logger associated with the plugin.
     */
    Logger GetLogger();


    /**
     * @return The collection of players on this server.
     */
    IServerPlayerCollection GetPlayerCollection();


    /**
     * @return The plugin's event dispatcher.
     */
    IEventDispatcher GetEventDispatcher();
}