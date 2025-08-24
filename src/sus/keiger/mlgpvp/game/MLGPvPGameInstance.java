package sus.keiger.mlgpvp.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import sus.keiger.mlgpvp.game.component.GameComponent;
import sus.keiger.mlgpvp.game.component.GameEntityCollection;
import sus.keiger.mlgpvp.game.component.GameStateController;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceEndEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceTickEvent;
import sus.keiger.mlgpvp.player.IAudienceMember;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.mlgpvp.service.IServerServices;
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


    // Constructors.
    public MLGPvPGameInstance(GameInstanceCreationOptions options)
    {
        Objects.requireNonNull(options, "options is null");
        _services = options.Services();
        _values =options.Values();

        _stateController = new GameStateController(this);
        _entities = new GameEntityCollection(this);

        AddComponent(_stateController);
        AddComponent(_entities);
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
    public void SwitchToPostGameState()
    {
        _stateController.SwitchToPostGameState();
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
    public Optional<PlayerGameStats> GetPlayerStats(IServerPlayer player)
    {
        return Optional.empty();
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
    public PCPluginEvent<GameInstanceTickEvent> GetPostGameTickEvent()
    {
        return _stateController.GetPostGameTickEvent();
    }

    @Override
    public void AddPlayer(IServerPlayer player)
    {


    }

    @Override
    public void RemovePlayer(IServerPlayer player)
    {

    }

    @Override
    public int GetJoinedPlayerCount()
    {
        return 0;
    }

    @Override
    public List<IServerPlayer> GetJoinedPlayers()
    {
        return List.of();
    }

    @Override
    public boolean ContainsJoinedPlayer(IServerPlayer player)
    {
        return false;
    }

    @Override
    public int GetOnlinePlayerCount()
    {
        return 0;
    }

    @Override
    public List<IServerPlayer> GetOnlinePlayers()
    {
        return List.of();
    }

    @Override
    public boolean ContainsOnlinePlayer(IServerPlayer player)
    {
        return false;
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
        return null;
    }

    @Override
    public void Start()
    {

    }

    @Override
    public void Cancel()
    {

    }

    @Override
    public GameInstanceState GetState()
    {
        return null;
    }

    @Override
    public PCPluginEvent<GameInstanceStartEvent> GetStartEvent()
    {
        return null;
    }

    @Override
    public PCPluginEvent<GameInstanceEndEvent> GetEndEvent()
    {
        return null;
    }

    @Override
    public PCPluginEvent<GameInstanceCompleteEvent> GetCompleteEvent()
    {
        return null;
    }

    @Override
    public List<? extends IAudienceMember> GetAudienceMembers()
    {
        return List.of();
    }

    @Override
    public void ShowTitle(Title title)
    {

    }

    @Override
    public void ClearTitle()
    {

    }

    @Override
    public void SendMessage(Component message)
    {

    }

    @Override
    public void ShowActionbar(ActionbarMessage message)
    {

    }

    @Override
    public void ClearActionbar()
    {

    }

    @Override
    public void RemoveActionbar(long id)
    {

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

    }

    @Override
    public void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch)
    {

    }

    @Override
    public void Tick()
    {

    }
}