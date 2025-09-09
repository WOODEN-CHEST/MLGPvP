package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

public class GamePlayerMLGEvent extends GamePlayerEntityEvent
{
    // Private fields.
    private final double _fallDistance;


    // Constructors.
    public GamePlayerMLGEvent(GamePlayerEntity entity, double fallDistance)
    {
        super(entity, null);
        _fallDistance = fallDistance;
    }


    // Methods.
    public double GetFallDistance()
    {
        return _fallDistance;
    }
}
