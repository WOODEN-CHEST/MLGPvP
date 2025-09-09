package sus.keiger.mlgpvp.game.entity.player.event;

import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Objects;

public class PlayerLifeChangeEvent extends GamePlayerEntityEvent
{
    // Private fields.
    private final boolean _isAlive;


    // Constructors.
    public PlayerLifeChangeEvent(GamePlayerEntity player, boolean isAlive)
    {
        super(player, null);
        _isAlive = isAlive;
    }


    // Methods.
    public boolean GetIsAlive()
    {
        return _isAlive;
    }
}