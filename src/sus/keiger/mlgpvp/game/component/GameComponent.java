package sus.keiger.mlgpvp.game.component;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.ITickable;

public abstract class GameComponent<T extends IGameInstanceExtended> implements ITickable, IMLGPvPEventListener
{
    // Private fields.
    private final T _gameInstance;


    // Constructors.
    public GameComponent(T gameInstance)
    {
        _gameInstance = gameInstance;
    }


    // Methods.
    public T GetGameInstance()
    {
        return _gameInstance;
    }

    public GameInstanceValues GetValues()
    {
        return _gameInstance.GetConfigValues();
    }

    public IServerServices GetServices()
    {
        return _gameInstance.GetServices();
    }


    // Inherited methods.

    @Override
    public void Tick() { }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher) { }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher) { }
}