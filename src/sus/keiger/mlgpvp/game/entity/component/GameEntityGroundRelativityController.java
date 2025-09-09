package sus.keiger.mlgpvp.game.entity.component;

import org.bukkit.Location;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.event.GameEntityLandOnGroundEvent;
import sus.keiger.mlgpvp.game.entity.event.GameEntityLiftFromGroundEvent;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.entity.EntityFunctions;

public class GameEntityGroundRelativityController extends GameEntityComponent<GameEntity>
{
    // Private fields.
    private final PCPluginEvent<GameEntityLandOnGroundEvent> _landEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GameEntityLiftFromGroundEvent> _liftEvent = new PCPluginEvent<>();

    private boolean _wasEntityOnGround = true;
    private Location _latestOnGroundLocation;


    // Constructors.
    public GameEntityGroundRelativityController(GameEntity entity)
    {
        super(entity);
    }


    // Methods.
    public PCPluginEvent<GameEntityLandOnGroundEvent> GetLandEvent()
    {
        return _landEvent;
    }

    public PCPluginEvent<GameEntityLiftFromGroundEvent> GetLiftEvent()
    {
        return _liftEvent;
    }


    // Private methods.
    private void EventTick()
    {
        boolean IsOnGround = EntityFunctions.IsEntityOnGround(GetEntity().GetUnderlyingEntity());

        TryFireEvents(IsOnGround);
        UpdateProperties(IsOnGround);

        _wasEntityOnGround = IsOnGround;
    }

    private void TryFireEvents(boolean isOnGround)
    {
        if (isOnGround && !_wasEntityOnGround)
        {
            _landEvent.FireEvent(new GameEntityLandOnGroundEvent(GetEntity(), GetEntity().GetLocation()));
        }
        else if (!isOnGround && _wasEntityOnGround)
        {
            _liftEvent.FireEvent(new GameEntityLiftFromGroundEvent(GetEntity(), _latestOnGroundLocation));
        }
    }

    private void UpdateProperties(boolean isOnGround)
    {
        if (isOnGround)
        {
            _latestOnGroundLocation = GetEntity().GetLocation();
        }
    }


    // Inherited methods.

    @Override
    public void Tick()
    {
        super.Tick();
        EventTick();
    }
}