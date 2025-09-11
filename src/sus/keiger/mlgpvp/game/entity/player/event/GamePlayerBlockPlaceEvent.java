package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockPlaceEvent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Objects;

public class GamePlayerBlockPlaceEvent extends GamePlayerEntityEvent
{
    // Private fields.
    private final BlockPlaceEvent _blockPlaceEvent;


    // Constructors.
    public GamePlayerBlockPlaceEvent(GamePlayerEntity entity, BlockPlaceEvent event)
    {
        super(entity, event);
        _blockPlaceEvent = Objects.requireNonNull(event, "event is null");
    }


    // Methods.
    public Block GetPlacedBlock()
    {
        return _blockPlaceEvent.getBlockPlaced();
    }
}