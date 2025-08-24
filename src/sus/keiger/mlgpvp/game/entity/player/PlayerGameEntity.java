package sus.keiger.mlgpvp.game.entity.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.player.IServerPlayer;

import java.util.Objects;

public class PlayerGameEntity extends GameEntity
{
    // Private fields.
    private final IServerPlayer _serverPlayer;


    // Constructors.
    public PlayerGameEntity(IGameInstanceExtended gameInstance, IServerPlayer serverPlayer)
    {
        super(gameInstance, Objects.requireNonNull(serverPlayer, "serverPlayer is null").GetUnderlyingPlayer());
        _serverPlayer = serverPlayer;
    }


    // Methods.
    public Player GetPlayerEntity()
    {
        return (Player)GetUnderlyingEntity();
    }

    public IServerPlayer GetServerPlayer()
    {
        return _serverPlayer;
    }


    // Inherited methods.

    @Override
    public void Delete()
    {
        /* Player's should never be deleted. */
    }
}