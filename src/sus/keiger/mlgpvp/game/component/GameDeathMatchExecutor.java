package sus.keiger.mlgpvp.game.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.plugincommon.PCMath;
import sus.keiger.plugincommon.TickClock;

public class GameDeathMatchExecutor extends GameComponent<MLGPvPGameInstance>
{
    // Private static fields.
    private static final double DAMAGE_PER_INSTANCE = 1d;
    private static final int TICKS_PER_INSTANCE = PCMath.SecondsToTicks(0.5d);


    // Private fields.
    private final TickClock _deathmatchTimer = new TickClock();
    private final TickClock _damageClock = new TickClock();


    // Constructors.
    public GameDeathMatchExecutor(MLGPvPGameInstance entity)
    {
        super(entity);

        _damageClock.SetHandler(this::OnDamage);
        _deathmatchTimer.SetHandler(this::OnDeathmatchStart);

        _damageClock.SetIsRunning(false);
        _deathmatchTimer.SetIsRunning(true);
    }


    // Methods.
    public int GetTicksUntilDeathmatch()
    {
        return _deathmatchTimer.GetTicksLeft();
    }


    // Private methods.
    private void OnDamage(TickClock clock)
    {
        _damageClock.SetTicksLeft(TICKS_PER_INSTANCE);

        GetGameInstance()
                .GetEntities()
                .stream()
                .filter(entity -> entity instanceof GamePlayerEntity)
                .map(entity -> (GamePlayerEntity)entity)
                .forEach(entity -> entity.Damage(DAMAGE_PER_INSTANCE));
    }

    private void OnGameStartEvent(GameInstanceStartEvent event)
    {
        _deathmatchTimer.SetTicksLeft(PCMath.SecondsToTicks(GetValues().MaxGameDurationSeconds));
        _deathmatchTimer.SetIsRunning(true);
    }

    private void OnDeathmatchStart(TickClock clock)
    {
        GetGameInstance().SendMessage(Component.text("DEATHMATCH STARTED!").color(NamedTextColor.DARK_RED));
        _damageClock.SetIsRunning(true);
        _damageClock.SetTicksLeft(TICKS_PER_INSTANCE);
    }


    // Inherited methods.

    @Override
    public void Tick()
    {
        super.Tick();
        _damageClock.Tick();
        _deathmatchTimer.Tick();
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