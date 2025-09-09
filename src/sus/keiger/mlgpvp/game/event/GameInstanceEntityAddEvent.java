package sus.keiger.mlgpvp.game.event;

import sus.keiger.mlgpvp.game.IGameInstance;
import sus.keiger.mlgpvp.game.entity.GameEntity;

import java.util.Objects;

public class GameInstanceEntityAddEvent extends GameInstanceEntityEvent
{
    // Constructors.
    public GameInstanceEntityAddEvent(IGameInstance gameInstance, GameEntity entity)
    {
        super(gameInstance, entity);
    }
}
