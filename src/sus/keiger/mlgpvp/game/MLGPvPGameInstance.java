package sus.keiger.mlgpvp.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.component.*;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceTickEvent;
import sus.keiger.mlgpvp.player.IAudienceMember;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MLGPvPGameInstance implements IGameInstanceExtended
{
    // Private fields.
    private final List<GameComponent<?>> _components = new ArrayList<>();
    private final IServerServices _services;
    private final GameInstanceValues _values;

    private final GameStateController _stateController;
    private final GameEntityCollection _entities;
    private final GamePlayerCollection _players;
    private final GameLocation _location;
    private final GamePlayerStateEnsurer _playerStateEnsurer;


    // Constructors.
    public MLGPvPGameInstance(GameInstanceCreationOptions options)
    {
        Objects.requireNonNull(options, "options is null");
        _services = options.Services();
        _values =options.Values();

        _stateController = new GameStateController(this);
        _entities = new GameEntityCollection(this);
        _players = new GamePlayerCollection(this);
        _location = new GameLocation(this);
        _playerStateEnsurer = new GamePlayerStateEnsurer(this);

        AddComponent(_stateController);
        AddComponent(_entities);
        AddComponent(_players);
        AddComponent(_location);
        AddComponent(_playerStateEnsurer);
    }



    // Private methods.



    // Methods.
    public void AddComponent(GameComponent<?> component)
    {
        Objects.requireNonNull(component, "component is null");
        if (_components.contains(component))
        {
            return;
        }
        _components.add(component);
    }



    // Inherited methods.
    @Override
    public void AddEntity(GameEntity entity)
    {
        _entities.AddEntity(entity);
    }

    @Override
    public void RemoveEntity(GameEntity entity)
    {
        _entities.RemoveEntity(entity);
    }

    @Override
    public Optional<GameEntity> GetEntity(Entity bukkitEntity)
    {
        return _entities.GetEntity(bukkitEntity);
    }

    @Override
    public List<GameEntity> GetEntities()
    {
        return _entities.GetEntities();
    }

    @Override
    public int GetEntityCount()
    {
        return _entities.GetEntityCount();
    }

    @Override
    public IServerServices GetServices()
    {
        return _services;
    }

    @Override
    public void SwitchToInGameState()
    {
        _stateController.SwitchToInGameState();
    }

    @Override
    public void SwitchToCompleteState()
    {
        _stateController.SwitchToCompleteState();
    }

    @Override
    public boolean TryReAddPlayer(IServerPlayer player)
    {
        return _entities.TryReAddPlayer(player);
    }

    @Override
    public int GetStartingPlayerCount()
    {
        return 0;
    }

    @Override
    public Optional<PlayerGameStats> GetPlayerStats(IServerPlayer player)
    {
        return _players.GetPlayerStats(player);
    }

    @Override
    public PCPluginEvent<GameInstanceTickEvent> GetLobbyTickEvent()
    {
        return _stateController.GetLobbyTickEvent();
    }

    @Override
    public PCPluginEvent<GameInstanceTickEvent> GetInGameTickEvent()
    {
        return _stateController.GetInGameTickEvent();
    }

    @Override
    public void AddPlayer(IServerPlayer player)
    {
        _players.AddPlayer(player);
    }

    @Override
    public void RemovePlayer(IServerPlayer player)
    {
        _players.RemovePlayer(player);
    }

    @Override
    public int GetJoinedPlayerCount()
    {
        return _players.GetJoinedPlayerCount();
    }

    @Override
    public List<IServerPlayer> GetJoinedPlayers()
    {
        return _players.GetJoinedPlayers();
    }

    @Override
    public boolean ContainsJoinedPlayer(IServerPlayer player)
    {
        return _players.ContainsJoinedPlayer(player);
    }

    @Override
    public int GetOnlinePlayerCount()
    {
        return _players.GetOnlinePlayerCount();
    }

    @Override
    public List<IServerPlayer> GetOnlinePlayers()
    {
        return _players.GetOnlinePlayers();
    }

    @Override
    public boolean ContainsOnlinePlayer(IServerPlayer player)
    {
        return _players.ContainsOnlinePlayer(player);
    }

    @Override
    public void AddSpectator(IServerPlayer player)
    {

    }

    @Override
    public void RemoveSpectator(IServerPlayer player)
    {

    }

    @Override
    public int GetSpectatorCount()
    {
        return 0;
    }

    @Override
    public List<IServerPlayer> GetSpectators()
    {
        return List.of();
    }

    @Override
    public GameInstanceValues GetConfigValues()
    {
        return _values;
    }

    @Override
    public ExplainedResult Start()
    {
        if (_stateController.GetState() != GameInstanceState.Lobby)
        {
            return ExplainedResult.Error("Games can only be started while in the lobby state.");
        }

        return _stateController.SwitchToInGameState();
    }

    @Override
    public ExplainedResult Cancel()
    {
        return _stateController.SwitchToCompleteState();
    }

    @Override
    public GameInstanceState GetState()
    {
        return _stateController.GetState();
    }

    @Override
    public void SetCenterLocation(Location location)
    {
        _location.SetCenterLocation(location);
    }

    @Override
    public Location GetCenterLocation()
    {
        return _location.GetCenterLocation();
    }

    @Override
    public PCPluginEvent<GameInstanceStartEvent> GetStartEvent()
    {
        return _stateController.GetStartEvent();
    }

    @Override
    public PCPluginEvent<GameInstanceCompleteEvent> GetCompleteEvent()
    {
        return _stateController.GetCompleteEvent();
    }

    @Override
    public List<? extends IAudienceMember> GetAudienceMembers()
    {
        return _players.GetJoinedPlayers();
    }

    @Override
    public void ShowTitle(Title title)
    {
        GetAudienceMembers().forEach(player -> player.ShowTitle(title));
    }

    @Override
    public void ClearTitle()
    {
        GetAudienceMembers().forEach(IAudienceMember::ClearTitle);
    }

    @Override
    public void SendMessage(Component message)
    {
        GetAudienceMembers().forEach(player -> player.SendMessage(message));
    }

    @Override
    public void ShowActionbar(ActionbarMessage message)
    {
        GetAudienceMembers().forEach(player -> player.ShowActionbar(message));
    }

    @Override
    public void ClearActionbar()
    {
        GetAudienceMembers().forEach(IAudienceMember::ClearActionbar);
    }

    @Override
    public void RemoveActionbar(long id)
    {
        GetAudienceMembers().forEach(player -> player.RemoveActionbar(id));
    }

    @Override
    public <T> void SpawnParticle(Particle particle,
                                  Location location,
                                  double deltaX,
                                  double deltaY,
                                  double deltaZ,
                                  int count,
                                  double extra,
                                  T data)
    {
        GetAudienceMembers().forEach(player -> player.SpawnParticle(
                particle, location, deltaX, deltaY, deltaZ, count, extra, data));
    }

    @Override
    public void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch)
    {
        GetAudienceMembers().forEach(player -> player.PlaySound(sound, location, category, volume, pitch));
    }

    @Override
    public void Tick()
    {
        _components.forEach(GameComponent::Tick);
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