package sus.keiger.mlgpvp.service;

import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.player.IServerPlayerCollection;

import java.util.logging.Logger;

public class DefaultServerServices implements IServerServices
{
    // Private fields.
    private final Plugin _plugin;
    private final IServerPlayerCollection _players;



    // Constructors.
    public DefaultServerServices(Plugin plugin, IServerPlayerCollection players)
    {
        _plugin = plugin;
        _players = players;
    }


    // Inherited methods.
    @Override
    public Plugin GetPlugin()
    {
        return _plugin;
    }

    @Override
    public Logger GetLogger()
    {
        return _plugin.getLogger();
    }

    @Override
    public IServerPlayerCollection GetPlayerCollection()
    {
        return _players;
    }
}