package sus.keiger.mlgpvp.game.entity.event;

import org.bukkit.Location;
import sus.keiger.mlgpvp.game.entity.GameEntity;

import java.util.Objects;
import java.util.Optional;

public class GameEntityLiftFromGroundEvent extends GameEntityEvent
{
    // Private fields.
    private final Location _liftStartLocation;


    // Constructors.
    public GameEntityLiftFromGroundEvent(GameEntity entity, Location liftStartLocation)
    {
        super(entity, null);
        _liftStartLocation =liftStartLocation;
    }


    // Methods.
    public Optional<Location> GetLiftStartLocation()
    {
        return Optional.ofNullable(_liftStartLocation).map(Location::clone);
    }
}