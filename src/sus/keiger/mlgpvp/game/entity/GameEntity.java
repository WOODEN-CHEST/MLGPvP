package sus.keiger.mlgpvp.game.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.component.GameEntityGroundRelativityController;
import sus.keiger.mlgpvp.game.entity.event.GameEntityLandOnGroundEvent;
import sus.keiger.mlgpvp.game.entity.event.GameEntityLiftFromGroundEvent;
import sus.keiger.plugincommon.ITickable;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GameEntity implements ITickable, IMLGPvPEventListener
{
    // Private fields.
    private final IGameInstanceExtended _gameInstance;
    private final List<GameEntityComponent<?>> _components = new ArrayList<>();
    private final GameEntityGroundRelativityController _groundRelativityController;

    private Entity _wrappedEntity;
    private boolean _isInitialized = false;



    // Constructors.
    public GameEntity(IGameInstanceExtended gameInstance, Entity wrappedEntity)
    {
        _gameInstance = Objects.requireNonNull(gameInstance, "gameInstance is null");
        SetUnderlyingEntity(wrappedEntity);

        _groundRelativityController = new GameEntityGroundRelativityController(this);
        AddComponent(_groundRelativityController);
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

    public void SetLocation(Location location)
    {
        _wrappedEntity.teleport(Objects.requireNonNull(location, "location is null"));
    }

    public Location GetLocation()
    {
        return _wrappedEntity.getLocation();
    }

    public Vector GetMotion()
    {
        return _wrappedEntity.getVelocity();
    }

    public void SetMotion(Vector motion)
    {
        _wrappedEntity.setVelocity(Objects.requireNonNull(motion, "motion is null"));
    }

    public void AddMotion(Vector motion)
    {
        SetMotion(GetMotion().add(Objects.requireNonNull(motion, "motion is null")));
    }

    public void SetIsGlowing(boolean isGlowing)
    {
        GetUnderlyingEntity().setGlowing(isGlowing);
    }

    public Location GetCenter()
    {
        Vector Center = GetUnderlyingEntity().getBoundingBox().getCenter();
        return GetLocation().set(Center.getX(), Center.getY(), Center.getZ());
    }

    public float GetFallDistance()
    {
        return _wrappedEntity.getFallDistance();
    }

    public boolean GetIsInWater()
    {
        return _wrappedEntity.isInWater();
    }

    public PCPluginEvent<GameEntityLandOnGroundEvent> GetLandOnGroundEvent()
    {
        return  _groundRelativityController.GetLandEvent();
    }

    public PCPluginEvent<GameEntityLiftFromGroundEvent> GetLiftFromGroundEvent()
    {
        return _groundRelativityController.GetLiftEvent();
    }

    public BoundingBox GetBounds()
    {
        return _wrappedEntity.getBoundingBox();
    }


    // Inherited methods.
    @Override
    public void Tick()
    {
        _components.forEach(ITickable::Tick);
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        _components.forEach(component -> component.SubscribeToEvents(dispatcher));
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        _components.forEach(component -> component.UnsubscribeFromEvents(dispatcher));
    }
}