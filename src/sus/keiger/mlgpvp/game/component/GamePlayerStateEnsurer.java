package sus.keiger.mlgpvp.game.component;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;

import java.util.Random;

public class GamePlayerStateEnsurer extends GameComponent<MLGPvPGameInstance>
{
    // Private fields.
    private final Random _rng = new Random();


    // Constructors.
    public GamePlayerStateEnsurer(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
    }


    // Private methods.
    private void OnGameStartEvent(GameInstanceStartEvent event)
    {
        GetGameInstance().GetOnlinePlayers().forEach(player ->
        {
            GamePlayerEntity PlayerEntity = new GamePlayerEntity(GetGameInstance(), player);
            GetGameInstance().AddEntity(PlayerEntity);
            PlayerEntity.Teleport(GetRandomInBoundsLocation());
        });
    }

    private Location GetRandomInBoundsLocation()
    {
        Location TargetLocation = GetGameInstance().GetCenterLocation();

        double Diameter = GetValues().BorderDiameterMax;
        double XOffset = (_rng.nextDouble() - 0.5d) * Diameter;
        double ZOffset = (_rng.nextDouble() - 0.5d) * Diameter;

        TargetLocation.add(new Vector(XOffset, 0d, ZOffset));
        TargetLocation.setY(TargetLocation.getWorld().getHighestBlockYAt(TargetLocation) + 1d);

        return TargetLocation;
    }



    // Inherited methods.

    @Override
    public void Tick()
    {
        super.Tick();
    }

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