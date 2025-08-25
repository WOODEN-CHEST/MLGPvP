package sus.keiger.mlgpvp.game.component;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;

import java.util.Objects;

public class GameLocation extends GameComponent<MLGPvPGameInstance>
{
    // Private fields.
    private Location _centerLocation = new Location(Bukkit.getWorlds().getFirst(), 0d, 0d, 0d, 0f, 0f);


    // Constructors.
    public GameLocation(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
    }


    // Methods.
    public void SetCenterLocation(Location location)
    {
        _centerLocation = Objects.requireNonNull(location, "location is null").clone();
    }

    public Location GetCenterLocation()
    {
        return _centerLocation.clone();
    }
}