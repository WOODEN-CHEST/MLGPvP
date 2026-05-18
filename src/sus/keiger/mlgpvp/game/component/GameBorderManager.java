package sus.keiger.mlgpvp.game.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.event.GameBorderBeginShrinkEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.plugincommon.PCMath;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.TickClock;

public class GameBorderManager extends GameComponent<MLGPvPGameInstance>
{
    // Private static fields.
    private static final double DAMAGE_AMOUNT = 2d;
    private static final double DAMAGE_BUFFER = 5d;


    // Private fields.
    private final PCPluginEvent<GameBorderBeginShrinkEvent> _beginShrinkEvent = new PCPluginEvent<>();
    private final TickClock _borderShrinkClock = new TickClock();

    private double _restoredBorderSize;
    private Location _restoredBorderCenter;
    private double _restoredBorderDamageAmount;
    private double _restoredBorderBufferAmount;


    // Constructors.
    public GameBorderManager(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
        _borderShrinkClock.SetHandler(this::OnBorderShrinkBeginEvent);
        _borderShrinkClock.SetIsRunning(true);
    }


    // Methods.
    public PCPluginEvent<GameBorderBeginShrinkEvent> GetBeginShrinkEvent()
    {
        return _beginShrinkEvent;
    }

    public int GetTicksUntilShrink()
    {
        return _borderShrinkClock.GetTicksLeft();
    }


    // Private methods.
    private void OnBorderShrinkBeginEvent(TickClock clock)
    {
        WorldBorder Border = GetGameInstance().GetCenterLocation().getWorld().getWorldBorder();
        Border.changeSize(GetValues().BorderDiameterMin, GetGameInstance().GetTicksRemainingUntilDeathmatch());
        _beginShrinkEvent.FireEvent(new GameBorderBeginShrinkEvent(GetGameInstance()));
        GetGameInstance().SendMessage(Component.text("The border is now shrinking!").color(NamedTextColor.RED));
    }

    private void OnGameStartEvent(GameInstanceStartEvent event)
    {
        Location CenterLocation = GetGameInstance().GetCenterLocation();
        World TargetWorld = CenterLocation.getWorld();
        WorldBorder Border = TargetWorld.getWorldBorder();

        _restoredBorderCenter = Border.getCenter();
        _restoredBorderBufferAmount = Border.getDamageBuffer();
        _restoredBorderDamageAmount = Border.getDamageAmount();
        _restoredBorderSize = Border.getSize();

        Border.setCenter(CenterLocation);
        Border.changeSize(GetValues().BorderDiameterMax, 0L);
        Border.setDamageAmount(DAMAGE_AMOUNT);
        Border.setDamageBuffer(DAMAGE_BUFFER);

        _borderShrinkClock.SetTicksLeft(PCMath.SecondsToTicks(GetValues().BorderShrinkStartTimeSeconds));
    }

    private void OnGameCompleteEvent(GameInstanceCompleteEvent event)
    {
        Location CenterLocation = GetGameInstance().GetCenterLocation();
        World TargetWorld = CenterLocation.getWorld();
        WorldBorder Border = TargetWorld.getWorldBorder();

        Border.setCenter(_restoredBorderCenter);
        Border.changeSize(_restoredBorderSize, 0L);
        Border.setDamageAmount(_restoredBorderDamageAmount);
        Border.setDamageBuffer(_restoredBorderBufferAmount);
    }


    // Inherited methods.

    @Override
    public void Tick()
    {
        super.Tick();
        _borderShrinkClock.Tick();
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        GetGameInstance().GetStartEvent().Subscribe(this, this::OnGameStartEvent);
        GetGameInstance().GetCompleteEvent().Subscribe(this, this::OnGameCompleteEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        GetGameInstance().GetStartEvent().Unsubscribe(this);
        GetGameInstance().GetCompleteEvent().Unsubscribe(this);
    }
}