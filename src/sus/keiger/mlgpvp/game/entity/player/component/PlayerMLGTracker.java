package sus.keiger.mlgpvp.game.entity.player.component;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import sus.keiger.mlgpvp.MLGPvPPlugin;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.plugincommon.PCMath;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.text.NumberFormat;

public class PlayerMLGTracker extends GameEntityComponent<GamePlayerEntity>
{
    // Private static fields
    private static final float PITCH_MIN = 0.5f;
    private static final float PITCH_MAX = 2f;
    private static final float SOUND_VOLUME = 2f;


    private static final float CLIMB_HEIGHT_MAX_BLOCKS = 80;
    private static final float FACTOR_MIN = 0f;
    private static final float FACTOR_MAX = 1f;

    private static final int CLIMB_START_GRACE_DURATION_TICKS = PCMath.SecondsToTicks(0.15d);

    private static final int ACTIONBAR_DURATION_TICKS = PCMath.SecondsToTicks(3d);

    private final long _climbDistanceActionbarID = 123818692L; // Human-random value.

    /* So I don't know if this is a Purpur, PaperMC or another fork thing, but motion added by setVelocity
    * on players does not reflect their true motion (it's lower than indicated by getVelocity),
    * so I created this custom vertical motion tracker.
    * Without this, the highest blast climb is around 60 blocks, and MLGPvP is more fun with higher blasts. */
    private double _remainingBlastUpMotion = 0d;


    // Private fields.
    private final BossBar _climbBossBar;
    private final NumberFormat _format = MLGPvPPlugin.GetFormat("0.0");

    private Double _currentClimbStartYPos = null;
    private int _previousYPos = 0;
    private double _previousClimbedHeight = 0d;
    private int _graceTicks = 0;



    // Constructors.
    public PlayerMLGTracker(GamePlayerEntity entity)
    {
        super(entity);

        _climbBossBar = BossBar.bossBar(Component.empty(),
                0f,
                BossBar.Color.WHITE,
                BossBar.Overlay.PROGRESS);
    }


    // Methods.
    public void MarkClimbStart()
    {
        _currentClimbStartYPos = GetEntity().GetLocation().getY();
        _climbBossBar.addViewer(GetEntity().GetPlayerEntity());
        UpdateBossBar();
        _graceTicks = CLIMB_START_GRACE_DURATION_TICKS;
    }


    // Private methods.
    private double GetClimbedHeight()
    {
        if (_currentClimbStartYPos == null)
        {
            return 0d;
        }
        return Math.max(0d, GetEntity().GetLocation().getY() - _currentClimbStartYPos);
    }

    private float GetClimbFactor()
    {
        return Math.max(FACTOR_MIN, Math.min(FACTOR_MAX, (float)(GetClimbedHeight() / CLIMB_HEIGHT_MAX_BLOCKS)));
    }

    private void TickClimb()
    {
        if (_currentClimbStartYPos == null)
        {
            return;
        }

        if ((GetEntity().GetFallDistance() > 0f) && (_graceTicks <= 0))
        {
            MarkClimbEnd();
            return;
        }

        UpdateBossBar();

        int CurrentYPos = GetEntity().GetLocation().getBlockY();
        if ((_previousYPos != CurrentYPos) && GetConfigValues().IsClimbingSoundEnabled)
        {
            GetEntity().PlaySound(Sound.BLOCK_NOTE_BLOCK_HAT,
                    GetEntity().GetLocation(),
                    SoundCategory.PLAYERS,
                    SOUND_VOLUME,
                    PITCH_MIN + ((PITCH_MAX - PITCH_MIN) * GetClimbFactor()));
        }
        _previousYPos = CurrentYPos;

        _graceTicks = (_graceTicks > 0) ? (_graceTicks - 1) : 0;
    }

    private void MarkClimbEnd()
    {
        _previousClimbedHeight = _currentClimbStartYPos;

        GetEntity().ShowActionbar(new ActionbarMessage(ACTIONBAR_DURATION_TICKS,
                Component.text("Climbed %s blocks".formatted(_format.format(GetClimbedHeight())))
                        .color(NamedTextColor.GREEN),
                _climbDistanceActionbarID));

        _climbBossBar.removeViewer(GetEntity().GetPlayerEntity());

        _currentClimbStartYPos = null;

        /* Basically, the bossbar has a 1-tick interpolation animation on the client, so I update it here so
        * that when it pops up again in the future, the animation doesn't show the previous value. */
        UpdateBossBar();
    }

    private void UpdateBossBar()
    {
        _climbBossBar.progress(GetClimbFactor());
        _climbBossBar.name(Component.text("Climb Height: %s blocks".formatted(_format.format(GetClimbedHeight()))));
    }



    // Inherited methods.
    @Override
    public void Tick()
    {
        super.Tick();
        TickClimb();
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);
    }
}