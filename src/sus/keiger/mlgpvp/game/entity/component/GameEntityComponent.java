package sus.keiger.mlgpvp.game.entity.component;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.plugincommon.ITickable;

import java.util.Objects;

public abstract class GameEntityComponent<T extends GameEntity> implements ITickable, IMLGPvPEventListener
{
    // Private fields.
    private final T _entity;


    // Constructors.
    public GameEntityComponent(T entity)
    {
        _entity = Objects.requireNonNull(entity, "entity is null");
    }


    // Methods.
    public T GetEntity()
    {
        return _entity;
    }

    public IGameInstanceExtended GetGameInstance()
    {
        return _entity.GetGameInstance();
    }

    public GameInstanceValues GetConfigValues()
    {
        return _entity.GetConfigValues();
    }

    public void Initialize() { }


    // Inherited methods.
    @Override
    public void Tick() { }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher) { }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher) { }
}