package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Objects;

public class GamePlayerHitByArrowEvent extends GamePlayerEntityEvent
{
    // Private fields.
    private final GameArrowEntity _arrow;


    // COnstructors.
    public GamePlayerHitByArrowEvent(GamePlayerEntity entity, GameArrowEntity arrow)
    {
        super(entity, null);
        _arrow = Objects.requireNonNull(arrow, "arrow is null");
    }


    // Methods.
    public GameArrowEntity GetArrow()
    {
        return _arrow;
    }
}
