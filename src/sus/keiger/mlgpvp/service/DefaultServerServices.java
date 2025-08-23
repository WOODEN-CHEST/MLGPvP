package sus.keiger.mlgpvp.service;

import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.player.IServerPlayerCollection;

import java.util.Objects;
import java.util.logging.Logger;

public class DefaultServerServices implements IServerServices
{
    // Private fields.
    private final Plugin _plugin;
    private final IServerPlayerCollection _players;
    private final IEventDispatcher _eventDispatcher;



    // Constructors.
    public DefaultServerServices(Plugin plugin,
                                 IServerPlayerCollection players,
                                 IEventDispatcher eventDispatcher)
    {
        _plugin = Objects.requireNonNull(plugin, "plugin is null");
        _players = Objects.requireNonNull(players, "players is null");
        _eventDispatcher = Objects.requireNonNull(eventDispatcher, "eventDispatcher is null");
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

    @Override
    public IEventDispatcher GetEventDispatcher()
    {
        return _eventDispatcher;
    }
}