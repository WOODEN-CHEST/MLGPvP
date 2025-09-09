package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.event.GameEntityEvent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

public class GamePlayerEntityEvent extends GameEntityEvent
{
    // Constructors.
    public GamePlayerEntityEvent(GamePlayerEntity entity, Cancellable event)
    {
        super(entity, event);
    }


    // Methods.
    public GamePlayerEntity GetPlayer()
    {
        return (GamePlayerEntity)GetEntity();
    }
}