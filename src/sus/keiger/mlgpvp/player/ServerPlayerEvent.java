package sus.keiger.mlgpvp.player;

import java.util.Objects;

public class ServerPlayerEvent
{
    // Private fields.
    private final IServerPlayer _player;


    // Constructors.
    public ServerPlayerEvent(IServerPlayer player)
    {
        _player = Objects.requireNonNull(player, "player is null");
    }


    // Methods.
    public IServerPlayer GetPlayer()
    {
        return _player;
    }
}