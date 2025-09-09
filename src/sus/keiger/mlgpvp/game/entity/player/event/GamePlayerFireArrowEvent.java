package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Objects;

public class GamePlayerFireArrowEvent extends GamePlayerEntityEvent
{
    // Prviate fields.
    private final GameArrowEntity _firedArrow;


    // Constructors.
    public GamePlayerFireArrowEvent(GamePlayerEntity entity, GameArrowEntity firedArrow)
    {
        super(entity, null);
        _firedArrow = Objects.requireNonNull(firedArrow, "firedArrow is null");
    }


    // Methods.
    public GameArrowEntity GetFiredArrow()
    {
        return _firedArrow;
    }
}