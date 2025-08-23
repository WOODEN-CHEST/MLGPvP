package sus.keiger.mlgpvp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.player.PlayerExistenceController;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.mlgpvp.service.ServiceCreator;
import sus.keiger.plugincommon.PCString;

import java.util.Objects;

public class ServerContext
{
    // Private fields.
    private final Plugin _plugin;

    private boolean _isInitialized = false;
    private IServerServices _services;
    private PlayerExistenceController _playerExistenceController;


    // Constructors.
    public ServerContext(Plugin plugin)
    {
        _plugin = Objects.requireNonNull(plugin, "plugin is null");
    }


    // Methods.
    public void Initialize() throws ServerContextException
    {
        if (_isInitialized)
        {
            throw new IllegalStateException("Server context already is initialized.");
        }

        try
        {
            ConstructObjects();
            InitializeObjects();
        }
        catch (Exception e)
        {
            throw new ServerContextException("Failed to initialize server context: %s"
                    .formatted(PCString.ExceptionToString(e)));
        }
    }

    public void Deinitialize() throws ServerContextException
    {
        if (!_isInitialized)
        {
            throw new IllegalStateException("Server context hasn't been initialized yet.");
        }

        try
        {
            DeinitializeObjects();
        }
        catch (Exception e)
        {
            throw new ServerContextException("Failed to deinitialize server context: %s"
                    .formatted(PCString.ExceptionToString(e)));
        }
    }


    // Private methods.
    private void ConstructObjects()
    {
        _services = new ServiceCreator().CreateServices(_plugin);
        _playerExistenceController = new PlayerExistenceController(_services);
    }

    private void InitializeObjects()
    {
        _playerExistenceController.SubscribeToEvents(_services.GetEventDispatcher());
    }

    private void DeinitializeObjects()
    {
        _playerExistenceController.UnsubscribeFromEvents(_services.GetEventDispatcher());
    }
}