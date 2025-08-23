package sus.keiger.mlgpvp.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IServerPlayer extends IAudienceMember
{
    Player GetUnderlyingPlayer();
    void SetUnderlyingPlayer(Player player);
    UUID GetUUID();
    String GetName();

    void AddReference(Object user);
    void RemoveReference(Object user);
    int GetReferenceCount();
}