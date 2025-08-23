package sus.keiger.mlgpvp;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstance;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.PCString;

import java.util.Objects;
import java.util.Optional;

public class GameSessionExecutor implements IGameSessionExecutor
{
    // Private fields.
    private IGameInstance _gameInstance = null; // For now, a singleton.
    private GameInstanceValues _values = new GameInstanceValues();
    private final IServerServices _services;


    // Constructors.
    public GameSessionExecutor(IServerServices services)
    {
        _services = Objects.requireNonNull(services, "services is null");
    }


    // Inherited methods.
    @Override
    public Optional<IGameInstance> GetCurrentGameInstance()
    {
        return Optional.ofNullable(_gameInstance);
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
            _gameInstance = new MLGPvPGameInstance();


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
    public ExplainedResult StopGame()
    {
        return ExplainedResult.Success();
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {

    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {

    }

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
                StopGame();
            }
        }
    }
}