package sus.keiger.mlgpvp.game.component;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.GameInstanceState;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;

import java.util.Objects;
import java.util.Optional;

public class GameWorldStateEnsurer extends GameComponent<MLGPvPGameInstance>
{
    // Private fields.
    private Location _centerLocation = new Location(Bukkit.getServer().getWorlds().getFirst(), 0d, 0d, 0d, 0f, 0f);

    private boolean _savedRuleLocatorBar;



    // Constructors.
    public GameWorldStateEnsurer(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
    }


    // Methods.
    public Location GetCenerLocation()
    {
        return _centerLocation.clone();
    }

    public void SetCenterLocation(Location location)
    {
        if (GetGameInstance().GetState() != GameInstanceState.Lobby)
        {
            throw new IllegalStateException("Cannot change center location while in-game");
        }
        _centerLocation = Objects.requireNonNull(location, "location is null").clone();
    }


    // Private methods.
    private void OnGameStartEvent(GameInstanceStartEvent event)
    {
        InitGameRules(_centerLocation.getWorld());
    }

    private void OnGameCompleteEvent(GameInstanceCompleteEvent event)
    {
        LoadGameRules(_centerLocation.getWorld());
    }

    private void SaveGameRules(World world)
    {
        _savedRuleLocatorBar = Optional.ofNullable(world.getGameRuleValue(GameRule.LOCATOR_BAR)).orElse(false);
    }

    private void LoadGameRules(World world)
    {
        world.setGameRule(GameRule.LOCATOR_BAR, _savedRuleLocatorBar);
    }

    private void InitGameRules(World world)
    {
        SaveGameRules(world);

        world.setGameRule(GameRule.LOCATOR_BAR, false);
    }


    // Inherited methods.

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        GetGameInstance().GetStartEvent().Subscribe(this, this::OnGameStartEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        GetGameInstance().GetStartEvent().Unsubscribe(this);
    }
}