package sus.keiger.mlgpvp.game.entity;

import org.bukkit.entity.Entity;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.plugincommon.ITickable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GameEntity implements ITickable
{
    // Private fields.
    private final IGameInstanceExtended _gameInstance;
    private Entity _wrappedEntity;
    private boolean _isInitialized = false;
    private final List<GameEntityComponent<?>> _components = new ArrayList<>();



    // Constructors.
    public GameEntity(IGameInstanceExtended gameInstance, Entity wrappedEntity)
    {
        _gameInstance = Objects.requireNonNull(gameInstance, "gameInstance is null");
        SetUnderlyingEntity(wrappedEntity);
    }


    // Methods.
    public Entity GetUnderlyingEntity()
    {
        return _wrappedEntity;
    }

    public void SetUnderlyingEntity(Entity entity)
    {
        _wrappedEntity = Objects.requireNonNull(entity, "entity is null");
    }

    public IGameInstanceExtended GetGameInstance()
    {
        return _gameInstance;
    }

    public GameInstanceValues GetConfigValues()
    {
        return _gameInstance.GetConfigValues();
    }

    public void Initialize()
    {
        if (_isInitialized)
        {
            return;
        }

        _components.forEach(GameEntityComponent::Initialize);
    }

    public void RemoveCleanup() { }

    public void Delete()
    {
        GetUnderlyingEntity().remove();
    }

    public void AddComponent(GameEntityComponent<?> component)
    {
        Objects.requireNonNull(component, "component is null");
        if (_components.contains(component))
        {
            return;
        }
        _components.add(component);
    }

    @Override
    public void Tick()
    {
        _components.forEach(ITickable::Tick);
    }
}