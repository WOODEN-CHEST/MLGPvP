package sus.keiger.mlgpvp;

import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.command.CommandInitializer;
import sus.keiger.mlgpvp.game.DeafultGameSessionExecutor;
import sus.keiger.mlgpvp.game.IGameSessionExecutor;
import sus.keiger.mlgpvp.player.DefaultPlayerStateController;
import sus.keiger.mlgpvp.player.IPlayerStateController;
import sus.keiger.mlgpvp.player.PlayerExistenceController;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.mlgpvp.service.ServiceCreator;
import sus.keiger.plugincommon.DefaultTickExecutor;
import sus.keiger.plugincommon.ITickExecutor;
import sus.keiger.plugincommon.PCString;

import java.util.Objects;

/**
 * The server context just for the MLGPvP plugin. Supposed to be used as a singleton.
 */
public class ServerContext
{
    // Private fields.
    private final Plugin _plugin;

    private boolean _isInitialized = false;
    private IServerServices _services;
    private PlayerExistenceController _playerExistenceController;
    private IGameSessionExecutor _gameSessionExecutor;
    private IPlayerStateController _playerStateController;

    private final ITickExecutor _tickExecutor = new DefaultTickExecutor();


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
            throw new ServerContextException("Server context already is initialized.");
        }

        try
        {
            ConstructObjects();
            InitializeObjects();
            _isInitialized = true;
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
            throw new ServerContextException("Server context hasn't been initialized yet.");
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
        _gameSessionExecutor = new DeafultGameSessionExecutor(_services);
        _playerStateController = new DefaultPlayerStateController(_gameSessionExecutor, _services);
    }

    private void InitializeObjects()
    {
        _playerExistenceController.SubscribeToEvents(_services.GetEventDispatcher());
        _gameSessionExecutor.SubscribeToEvents(_services.GetEventDispatcher());
        new CommandInitializer().InitializeCommands(_plugin, _services, _gameSessionExecutor);
        _playerStateController.SubscribeToEvents(_services.GetEventDispatcher());

        _services.GetEventDispatcher().GetTickEvent().Subscribe(this, (event) -> _tickExecutor.Tick());

        _tickExecutor.AddTickable(_playerStateController);
        _tickExecutor.AddTickable(_gameSessionExecutor);
    }

    private void DeinitializeObjects()
    {
        _gameSessionExecutor.CancelGame();
        _playerExistenceController.UnsubscribeFromEvents(_services.GetEventDispatcher());
        _gameSessionExecutor.UnsubscribeFromEvents(_services.GetEventDispatcher());
        _playerStateController.UnsubscribeFromEvents(_services.GetEventDispatcher());

        _services.GetEventDispatcher().GetTickEvent().Unsubscribe(this);
        _tickExecutor.ClearTickables();
    }
}