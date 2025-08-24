package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.player.IAudienceMemberHolder;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.ITickable;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.List;

public interface IGameInstance extends IAudienceMemberHolder, ITickable
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

    void Start();
    void Cancel();

    GameInstanceState GetState();

    PCPluginEvent<GameInstanceStartEvent> GetStartEvent();
    PCPluginEvent<GameInstanceCompleteEvent> GetCompleteEvent();
}