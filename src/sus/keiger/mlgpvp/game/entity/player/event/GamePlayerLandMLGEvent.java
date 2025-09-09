package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

public class GamePlayerLandMLGEvent extends GamePlayerMLGEvent
{
    // Constructors.
    public GamePlayerLandMLGEvent(GamePlayerEntity entity, double fallDistance)
    {
        super(entity, fallDistance);
    }
}