package sus.keiger.mlgpvp.player;

import java.util.Objects;

public class PlayerReferenceCountChangeEvent extends ServerPlayerEvent
{
    // Constructors.
    public PlayerReferenceCountChangeEvent(IServerPlayer player)
    {
        super(player);
    }
}