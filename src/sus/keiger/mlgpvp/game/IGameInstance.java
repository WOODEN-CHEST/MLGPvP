package sus.keiger.mlgpvp.game;

import org.bukkit.Location;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.player.IAudienceMemberHolder;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.ITickable;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.List;

/**
 * Represents an instance of the MLGPvP minigame.
 * <br>This interface only contains methods which outsider classes should see (classes which do not participate
 * in the functionality of the minigame).
 */
public interface IGameInstance extends IAudienceMemberHolder, ITickable, IMLGPvPEventListener
{
    void AddPlayer(IServerPlayer player);

    void RemovePlayer(IServerPlayer player);

    int GetJoinedPlayerCount();

    List<IServerPlayer> GetJoinedPlayers();

    boolean ContainsJoinedPlayer(IServerPlayer player);

    int GetOnlinePlayerCount();

    List<IServerPlayer> GetOnlinePlayers();

    boolean ContainsOnlinePlayer(IServerPlayer player);

    void AddSpectator(IServerPlayer player);

    void RemoveSpectator(IServerPlayer player);

    int GetSpectatorCount();

    List<IServerPlayer> GetSpectators();

    GameInstanceValues GetConfigValues();

    ExplainedResult Start();

    ExplainedResult Cancel();

    GameInstanceState GetState();

    void SetCenterLocation(Location location);

    Location GetCenterLocation();


    PCPluginEvent<GameInstanceStartEvent> GetStartEvent();

    PCPluginEvent<GameInstanceCompleteEvent> GetCompleteEvent();
}