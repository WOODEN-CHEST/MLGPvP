package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.PCString;

import java.util.Objects;

public class DeafultGameSessionExecutor implements IGameSessionExecutor
{
    // Private fields.
    private IGameInstance _gameInstance; // For now, a singleton.
    private final GameInstanceValues _values = new GameInstanceValues();
    private final IServerServices _services;


    // Constructors.
    public DeafultGameSessionExecutor(IServerServices services)
    {
        _services = Objects.requireNonNull(services, "services is null");
        CreateNewGameInstance();
    }


    // Private methods.
    private void CreateNewGameInstance()
    {
        _gameInstance = new MLGPvPGameInstance(new GameInstanceCreationOptions(_services, _values));
        _gameInstance.GetCompleteEvent().Subscribe(this, this::OnGameInstanceCompleteEvent);
    }

    private void OnGameInstanceCompleteEvent(GameInstanceCompleteEvent event)
    {
        event.GetGameInstance().GetCompleteEvent().Unsubscribe(this);
        CreateNewGameInstance();
    }


    // Inherited methods.
    @Override
    public IGameInstance GetCurrentGameInstance()
    {
        return _gameInstance;
    }

    @Override
    public GameInstanceValues GetGlobalGameValues()
    {
        return _values;
    }

    @Override
    public ExplainedResult StartGame()
    {
        try
        {
            if (_gameInstance.GetState() != GameInstanceState.Lobby)
            {
                return ExplainedResult.Error("Cannot start new game while the current game is still in progress.");
            }

            _services.GetPlayerCollection().GetPlayers().forEach(_gameInstance::AddPlayer);

            return ExplainedResult.Success();
        }
        catch (Exception e)
        {
            _services.GetLogger().severe("Exception starting game: %s"
                    .formatted(PCString.ExceptionToString(e)));
            return ExplainedResult.Error("Failed to start game due to an internal error: %s"
                    .formatted(e.getMessage()));
        }
    }

    @Override
    public ExplainedResult CancelGame()
    {
        if (_gameInstance.GetState() == GameInstanceState.Lobby)
        {
            return ExplainedResult.Error("No game in progress to cancel.");
        }

        try
        {
            _gameInstance.Cancel();
            return ExplainedResult.Success();
        }
        catch (Exception e)
        {
            _services.GetLogger().severe("Exception canceling game: %s"
                    .formatted(PCString.ExceptionToString(e)));
            return ExplainedResult.Error("Failed to canceling game due to an internal error: %s"
                    .formatted(e.getMessage()));
        }
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher) { }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher) { }

    @Override
    public void Tick()
    {
        if (_gameInstance != null)
        {
            try
            {
                _gameInstance.Tick();
            }
            catch (Exception e)
            {
                CancelGame();
            }
        }
    }
}