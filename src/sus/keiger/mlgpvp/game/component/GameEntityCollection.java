package sus.keiger.mlgpvp.game.component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceEntityAddEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceEntityRemoveEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.mlgpvp.player.ServerPlayerDisconnectEvent;
import sus.keiger.mlgpvp.player.ServerPlayerReconnectEvent;
import sus.keiger.plugincommon.ITickable;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.*;

public class GameEntityCollection extends GameComponent<IGameInstanceExtended>
{
    // Private fields.
    private final Map<Entity, GameEntity> _entities = new HashMap<>();
    private List<GameEntity> _entitiesCopy = Collections.emptyList();
    private final Map<IServerPlayer, GamePlayerEntity> _playerEntities = new HashMap<>();
    private final PCPluginEvent<GameInstanceEntityAddEvent> _entityAddEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GameInstanceEntityRemoveEvent> _entityRemoveEvent = new PCPluginEvent<>();


    // Constructors.
    public GameEntityCollection(IGameInstanceExtended gameInstance)
    {
        super(gameInstance);
    }


    // Methods.
    public void AddEntity(GameEntity entity)
    {
        Objects.requireNonNull(entity, "entity is null");

        if (_entities.containsKey(entity.GetUnderlyingEntity()))
        {
            return;
        }

        _entities.put(entity.GetUnderlyingEntity(), entity);
        entity.Initialize();

        if (entity instanceof GamePlayerEntity PlayerEntity)
        {
            _playerEntities.put(PlayerEntity.GetServerPlayer(), PlayerEntity);
        }

        UpdateEntityList();
        entity.SubscribeToEvents(GetServices().GetEventDispatcher());

        GetGameInstance().GetEntityAddEvent().FireEvent(new GameInstanceEntityAddEvent(GetGameInstance(), entity));
    }

    public void RemoveEntity(GameEntity entity)
    {
        GameEntity Entity = Objects.requireNonNull(entity, "entity is null");
        _entities.remove(Entity.GetUnderlyingEntity());
        UpdateEntityList();

        GetGameInstance().GetEntityRemoveEvent().FireEvent(new GameInstanceEntityRemoveEvent(GetGameInstance(), Entity));

        Entity.RemoveCleanup();
        Entity.UnsubscribeFromEvents(GetServices().GetEventDispatcher());
        Entity.Delete();
    }

    public Optional<GameEntity> GetEntity(Entity bukkitEntity)
    {
        return Optional.ofNullable(_entities.get(bukkitEntity));
    }

    public List<GameEntity> GetEntities()
    {
        return _entitiesCopy;
    }

    public int GetEntityCount()
    {
        return _entities.size();
    }

    public boolean TryReAddPlayer(IServerPlayer player)
    {
        GamePlayerEntity PlayerEntity = _playerEntities.get(Objects.requireNonNull(player, "player is null"));
        if (PlayerEntity == null)
        {
            return false;
        }
        PlayerEntity.SetUnderlyingEntity(player.GetUnderlyingPlayer());
        AddEntity(PlayerEntity);
        return true;
    }


    // Private methods.
    private void UpdateEntityList()
    {
        _entitiesCopy = List.copyOf(_entities.values());
    }

    private void OnGameStartEvent(GameInstanceStartEvent event)
    {
        GetGameInstance().GetJoinedPlayers().forEach(player ->
        {
            player.GetReconnectEvent().Subscribe(this, this::OnPlayerReconnectEvent);
            player.GetDisconnectEvent().Subscribe(this, this::OnPlayerDisconnectEvent);
        });
    }

    private void OnGameCompleteEvent(GameInstanceCompleteEvent event)
    {
        _entitiesCopy.forEach(this::RemoveEntity);
    }

    private void OnPlayerDisconnectEvent(ServerPlayerDisconnectEvent event)
    {
        GetEntity(event.GetPlayer().GetUnderlyingPlayer()).ifPresent(this::RemoveEntity);
    }

    private void OnPlayerReconnectEvent(ServerPlayerReconnectEvent event)
    {
        TryReAddPlayer(event.GetPlayer());
    }

    public PCPluginEvent<GameInstanceEntityAddEvent> GetEntityAddEvent()
    {
        return _entityAddEvent;
    }

    public PCPluginEvent<GameInstanceEntityRemoveEvent> GetEntityRemoveEvent()
    {
        return _entityRemoveEvent;
    }



    // Inherited methods.
    @Override
    public void Tick()
    {
        super.Tick();

        _entitiesCopy.forEach(ITickable::Tick);
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        GetGameInstance().GetCompleteEvent().Subscribe(this, this::OnGameCompleteEvent);
        GetGameInstance().GetStartEvent().Subscribe(this, this::OnGameStartEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        GetGameInstance().GetCompleteEvent().Unsubscribe(this);
        GetGameInstance().GetStartEvent().Unsubscribe(this);

        GetGameInstance().GetJoinedPlayers().forEach(player ->
        {
            player.GetDisconnectEvent().Unsubscribe(this);
            player.GetReconnectEvent().Unsubscribe(this);
        });
    }
}
