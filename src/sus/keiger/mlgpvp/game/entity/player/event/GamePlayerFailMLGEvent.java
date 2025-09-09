package sus.keiger.mlgpvp.game.entity.player.event;

import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

public class GamePlayerFailMLGEvent extends GamePlayerMLGEvent
{
    // Constructors.
    public GamePlayerFailMLGEvent(GamePlayerEntity entity, double fallDistance)
    {
        super(entity, fallDistance);
    }
}