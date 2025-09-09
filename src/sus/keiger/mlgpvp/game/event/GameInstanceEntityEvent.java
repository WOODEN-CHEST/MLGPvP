package sus.keiger.mlgpvp.game.event;

import sus.keiger.mlgpvp.game.IGameInstance;
import sus.keiger.mlgpvp.game.entity.GameEntity;

import java.util.Objects;

public class GameInstanceEntityEvent extends GameInstanceEvent
{
    // Private fields.
    private final GameEntity _entity;


    // Constructors.
    public GameInstanceEntityEvent(IGameInstance gameInstance, GameEntity entity)
    {
        super(gameInstance);
        _entity = Objects.requireNonNull(entity, "entity is null");
    }


    // Methods.
    public GameEntity GetEntity()
    {
        return _entity;
    }
}
