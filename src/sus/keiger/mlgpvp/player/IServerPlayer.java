package sus.keiger.mlgpvp.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IServerPlayer extends IAudienceMember
{
    Player GetUnderlyingPlayer();
    UUID GetUUID();
    String GetName();
}