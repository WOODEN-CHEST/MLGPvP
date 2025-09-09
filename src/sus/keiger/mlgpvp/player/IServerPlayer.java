package sus.keiger.mlgpvp.player;

import org.bukkit.entity.Player;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.plugincommon.ITickable;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.UUID;

public interface IServerPlayer extends IAudienceMember, ITickable, IMLGPvPEventListener
{
    Player GetUnderlyingPlayer();
    void SetUnderlyingPlayer(Player player);
    UUID GetUUID();
    String GetName();

    void AddReference(Object user);
    void RemoveReference(Object user);
    int GetReferenceCount();

    boolean GetIsOnline();

    PCPluginEvent<PlayerReferenceCountChangeEvent> GetReferenceCountChangeEvent();
    PCPluginEvent<ServerPlayerDisconnectEvent> GetDisconnectEvent();
    PCPluginEvent<ServerPlayerReconnectEvent> GetReconnectEvent();
}