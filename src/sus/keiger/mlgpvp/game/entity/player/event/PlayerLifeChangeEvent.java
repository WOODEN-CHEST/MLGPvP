package sus.keiger.mlgpvp.game.entity.player.event;

import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Objects;

public class PlayerLifeChangeEvent
{
    // Private fields.
    private final GamePlayerEntity _player;
    private final boolean _isAlive;


    // Constructors.
    public PlayerLifeChangeEvent(GamePlayerEntity player, boolean isAlive)
    {
        _player = Objects.requireNonNull(player, "player is null");
        _isAlive = isAlive;
    }


    // Methods.
    public GamePlayerEntity GetPlayer()
    {
        return _player;
    }

    public boolean GetIsAlive()
    {
        return _isAlive;
    }
}