package sus.keiger.mlgpvp.game.entity.event;

import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Objects;

public class GameEntityEvent
{
    // Private fields.
    private final GameEntity _entity;
    private final Cancellable _event;


    // Constructors.
    public GameEntityEvent(GameEntity entity, Cancellable event)
    {
        _entity = Objects.requireNonNull(entity, "entity is null");
        _event = event;
    }


    // Methods.
    public GameEntity GetEntity()
    {
        return _entity;
    }

    public void SetIsUnderlyingEventCancelled(boolean value)
    {
        if (_event != null)
        {
            _event.setCancelled(value);
        }
    }
}