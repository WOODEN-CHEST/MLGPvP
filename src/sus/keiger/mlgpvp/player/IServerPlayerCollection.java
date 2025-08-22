package sus.keiger.mlgpvp.player;

import sus.keiger.plugincommon.PCPluginEvent;

import java.util.List;

public interface IServerPlayerCollection extends IAudienceMemberHolder
{
    void AddPlayer(IServerPlayer player);
    void RemovePlayer(IServerPlayer player);
    List<IServerPlayer> GetPlayers();
    PCPluginEvent<PlayerCollectionAddEvent> GetAddEvent();
    PCPluginEvent<PlayerCollectionRemoveEvent> GetRemoveEvent();
}