package sus.keiger.mlgpvp.player;

import org.bukkit.entity.Player;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IServerPlayerCollection extends IAudienceMemberHolder
{
    void AddPlayer(IServerPlayer player);
    void RemovePlayer(IServerPlayer player);
    Optional<IServerPlayer> GetPlayer(UUID playerUUID);
    Optional<IServerPlayer> GetPlayer(Player player);
    List<IServerPlayer> GetPlayers();
    PCPluginEvent<PlayerCollectionAddEvent> GetAddEvent();
    PCPluginEvent<PlayerCollectionRemoveEvent> GetRemoveEvent();
}