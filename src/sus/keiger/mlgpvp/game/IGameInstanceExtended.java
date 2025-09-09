package sus.keiger.mlgpvp.game;

import org.bukkit.entity.Entity;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceTickEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceEntityAddEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceEntityRemoveEvent;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.List;
import java.util.Optional;

public interface IGameInstanceExtended extends IGameInstance
{
    void AddEntity(GameEntity entity);
    void RemoveEntity(GameEntity entity);
    Optional<GameEntity> GetEntity(Entity bukkitEntity);
    List<GameEntity> GetEntities();
    int GetEntityCount();

    IServerServices GetServices();

    void SwitchToInGameState();
    void SwitchToCompleteState();

    boolean TryReAddPlayer(IServerPlayer player);
    int GetStartingPlayerCount();

    int GetTicksRemainingUntilDeathmatch();
    int GetTicksRemainingUntilBorderShrink();

    Optional<PlayerGameStats> GetPlayerStats(IServerPlayer player);

    PCPluginEvent<GameInstanceTickEvent> GetLobbyTickEvent();
    PCPluginEvent<GameInstanceTickEvent> GetInGameTickEvent();

    PCPluginEvent<GameInstanceEntityAddEvent> GetEntityAddEvent();
    PCPluginEvent<GameInstanceEntityRemoveEvent> GetEntityRemoveEvent();
}