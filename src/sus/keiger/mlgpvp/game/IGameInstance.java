package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.player.IAudienceMemberHolder;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.ITickable;

import java.util.List;

public interface IGameInstance extends IAudienceMemberHolder, ITickable
{
    void AddPlayer(IServerPlayer player);
    void RemovePlayer(IServerPlayer player);

    int GetJoinedPlayerCount();
    List<IServerPlayer> GetJoinedPlayers();

    int GetOnlinePlayerCount();
    List<IServerPlayer> GetOnlinePlayers();

    void AddSpectator(IServerPlayer player);
    void RemoveSpectator(IServerPlayer player);
    int GetJoinedSpectatorCount();
    List<IServerPlayer> GetJoinedSpectators();

    GameInstanceValues GetConfigValues();

    void Start();
    void End();

    GameInstanceState GetState();
}