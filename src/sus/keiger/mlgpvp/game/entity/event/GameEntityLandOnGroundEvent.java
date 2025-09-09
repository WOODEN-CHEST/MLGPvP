package sus.keiger.mlgpvp.game.entity.event;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.GameEntity;

import java.util.Objects;

public class GameEntityLandOnGroundEvent extends GameEntityEvent
{
    // Private fields.
    private final Location _landLocation;


    // Constructors.
    public GameEntityLandOnGroundEvent(GameEntity entity, Location landLocation)
    {
        super(entity, null);
        _landLocation = Objects.requireNonNull(landLocation, "landLocation is null").clone();
    }


    // Methods.
    public Location GetLandLocation()
    {
        return _landLocation.clone();
    }
}