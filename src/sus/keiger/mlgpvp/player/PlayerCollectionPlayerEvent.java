package sus.keiger.mlgpvp.player;

import java.util.Objects;

public class PlayerCollectionPlayerEvent
{
    // Private fields.
    private final IServerPlayer _player;


    // Constructors.
    public PlayerCollectionPlayerEvent(IServerPlayer player)
    {
        _player = Objects.requireNonNull(player, "player is null");
    }


    // Methods.
    public IServerPlayer GetPlayer()
    {
        return _player;
    }
}
