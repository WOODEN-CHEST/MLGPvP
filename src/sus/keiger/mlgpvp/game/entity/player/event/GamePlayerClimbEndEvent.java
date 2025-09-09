package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

public class GamePlayerClimbEndEvent extends GamePlayerEntityEvent
{
    // Private fields.
    private final double _climbStartYPos;
    private final double _climbEndYPos;


    // Constructors.
    public GamePlayerClimbEndEvent(GamePlayerEntity entity, double climbStartYPos, double climbEndYPos)
    {
        super(entity, null);
        _climbStartYPos = climbStartYPos;
        _climbEndYPos = climbEndYPos;
    }


    // Methods.
    public double GetStartYPos()
    {
        return _climbStartYPos;
    }

    public double GetEndYPos()
    {
        return _climbEndYPos;
    }

    public double GetClimbHeight()
    {
        return _climbEndYPos - _climbStartYPos;
    }
}