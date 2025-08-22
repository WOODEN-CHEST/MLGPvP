package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.player.IServerPlayer;

import java.util.List;

public interface IGameInstance
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
}