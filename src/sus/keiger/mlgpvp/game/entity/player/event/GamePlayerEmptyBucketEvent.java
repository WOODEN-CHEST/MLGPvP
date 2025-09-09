package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Objects;

public class GamePlayerEmptyBucketEvent extends GamePlayerEntityEvent
{
    // Private fields.
    private final PlayerBucketEmptyEvent _event;


    // Constructors.
    public GamePlayerEmptyBucketEvent(GamePlayerEntity entity, PlayerBucketEmptyEvent event)
    {
        super(entity, event);
        _event = Objects.requireNonNull(event, "event is null");
    }


    // Methods.
    public Block GetBlock()
    {
        return _event.getBlock();
    }

    public Block GetBlockClicked()
    {
        return _event.getBlockClicked();
    }

    public ItemStack GetItemStack()
    {
        return _event.getItemStack();
    }

    public EquipmentSlot GetHand()
    {
        return _event.getHand();
    }
}