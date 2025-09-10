package sus.keiger.mlgpvp.game.entity.player.component;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import sus.keiger.mlgpvp.MLGPvPPlugin;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.event.GameEntityLandOnGroundEvent;
import sus.keiger.mlgpvp.game.entity.event.GameEntityLiftFromGroundEvent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerClimbEndEvent;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerEmptyBucketEvent;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerFailMLGEvent;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerLandMLGEvent;
import sus.keiger.plugincommon.PCMath;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.text.NumberFormat;
import java.util.Random;

public class PlayerMLGTracker extends GameEntityComponent<GamePlayerEntity>
{
    // Private static fields
    private static final float PITCH_MIN = 0.5f;
    private static final float PITCH_MAX = 2f;
    private static final float SOUND_VOLUME = 2f;

    private static final float CLIMB_HEIGHT_MAX_BLOCKS = 150;
    private static final float FACTOR_MIN = 0f;
    private static final float FACTOR_MAX = 1f;

    private static final int CLIMB_START_GRACE_DURATION_TICKS = PCMath.SecondsToTicks(0.15d);

    private static final int ACTIONBAR_DURATION_TICKS = PCMath.SecondsToTicks(3d);


    // Private fields.
    private final BossBar _climbBossBar;
    private final NumberFormat _blockHeightFormat = MLGPvPPlugin.GetFormat("0.0");

    private MLGPvPClimb _currentClimb = null;
    private final long _actionbarID;
    private MLGPvPFall _currentFall = null;
    private boolean _isOnGround = true;
    private double _highestReachedYCoordSinceLift;

    private final PCPluginEvent<GamePlayerClimbEndEvent> _climbEndEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GamePlayerLandMLGEvent> _landMLGEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GamePlayerFailMLGEvent> _failMLGEvent = new PCPluginEvent<>();




    // Constructors.
    public PlayerMLGTracker(GamePlayerEntity entity)
    {
        super(entity);

        _climbBossBar = BossBar.bossBar(Component.empty(),
                0f,
                BossBar.Color.WHITE,
                BossBar.Overlay.PROGRESS);

        _actionbarID = new Random().nextLong();
        ResetHighestReachedClimbPosition();
    }


    // Methods.
    public void MarkClimbStart()
    {
        if (IsClimbingFromExplosion())
        {
            return;
        }

        _currentClimb = new MLGPvPClimb(GetEntity().GetLocation().getY(), CLIMB_START_GRACE_DURATION_TICKS);
        _climbBossBar.addViewer(GetEntity().GetPlayerEntity());
        UpdateBossBar();
    }
    public PCPluginEvent<GamePlayerClimbEndEvent> GetClimbEndEvent()
    {
        return _climbEndEvent;
    }

    public PCPluginEvent<GamePlayerLandMLGEvent> GetLandMLGEvent()
    {
        return _landMLGEvent;
    }

    public PCPluginEvent<GamePlayerFailMLGEvent> GetFailMLGEvent()
    {
        return _failMLGEvent;
    }


    // Private methods.
    private void ResetHighestReachedClimbPosition()
    {
        _highestReachedYCoordSinceLift = Double.NEGATIVE_INFINITY;
    }

    private double GetClimbedHeight()
    {
        if (!IsClimbingFromExplosion())
        {
            return 0d;
        }
        return Math.max(0d, GetEntity().GetLocation().getY() - _currentClimb.StartYPosition);
    }

    private float GetClimbFactor()
    {
        return GetClimbFactor(GetClimbedHeight());
    }

    private float GetClimbFactor(double currentClimbHeight)
    {
        // Not exactly correct since the climbing height scaling isn't linear, but good enough.
        double MaxClimbHeight = CLIMB_HEIGHT_MAX_BLOCKS * GetConfigValues().ExplosionKnockbackScale;
        return Math.max(FACTOR_MIN, Math.min(FACTOR_MAX, (float)(currentClimbHeight / MaxClimbHeight)));
    }

    private void TickClimb()
    {
        if (!IsClimbingFromExplosion())
        {
            return;
        }

        int CurrentGraceTicks = _currentClimb.GraceTicks;
        if (((GetEntity().GetFallDistance() > 0f) || GetEntity().GetIsInWater() || _isOnGround)
                && (CurrentGraceTicks <= 0))
        {
            MarkClimbEnd();
            return;
        }

        UpdateBossBar();

        Location CurrentLocation = GetEntity().GetLocation();
        int CurrentYPosInt = CurrentLocation.getBlockY();
        if ((_currentClimb.PreviousYPosition != CurrentYPosInt) && GetConfigValues().IsClimbingSoundEnabled)
        {
            GetEntity().PlaySound(Sound.BLOCK_NOTE_BLOCK_HAT,
                    GetEntity().GetLocation(),
                    SoundCategory.PLAYERS,
                    SOUND_VOLUME,
                    PITCH_MIN + ((PITCH_MAX - PITCH_MIN) * GetClimbFactor()));
        }
        _currentClimb.PreviousYPosition = CurrentYPosInt;
        _highestReachedYCoordSinceLift = Math.max(_highestReachedYCoordSinceLift, CurrentLocation.getY());

        _currentClimb.GraceTicks = (CurrentGraceTicks > 0) ? (CurrentGraceTicks - 1) : 0;
    }

    private void TickFall()
    {
        if (!_isOnGround && GetEntity().GetIsInWater())
        {
            EndFall(GetEntity().GetLocation());
        }
    }

    private void MarkClimbEnd()
    {
        GetEntity().ShowActionbar(new ActionbarMessage(ACTIONBAR_DURATION_TICKS,
                Component.text("Climbed %s blocks".formatted(_blockHeightFormat.format(GetClimbedHeight())))
                        .color(NamedTextColor.GREEN),
                _actionbarID));

        _currentFall = new MLGPvPFall(_currentClimb.PreviousYPosition);
        _climbEndEvent.FireEvent(new GamePlayerClimbEndEvent(GetEntity(),
                _currentClimb.StartYPosition,
                _currentFall.StartYPosition));
        _currentClimb = null;


        /* Basically, the bossbar has a 1-tick interpolation animation on the client, so I update it here so
        * that when it pops up again in the future, the animation doesn't show the previous value. */
        UpdateBossBar();
        _climbBossBar.removeViewer(GetEntity().GetPlayerEntity());
    }

    private void UpdateBossBar()
    {
        _climbBossBar.progress(GetClimbFactor());
        _climbBossBar.name(Component.text("Climb Height: %s blocks".formatted(_blockHeightFormat.format(GetClimbedHeight()))));
    }

    private boolean IsClimbingFromExplosion()
    {
        return _currentClimb != null;
    }

    private boolean IsFallingFromExplosion()
    {
        return _currentFall != null;
    }

    private void OnEmptyBucketEvent(GamePlayerEmptyBucketEvent event)
    {
        if (IsFallingFromExplosion())
        {
            _currentFall.PlacedWaterBlock = event.GetBlock();
        }
    }

    private void OnLandOnGroundEvent(GameEntityLandOnGroundEvent event)
    {
        _isOnGround = true;
        EndFall(event.GetLandLocation());
    }

    private double GetFallDistance(Location currentLocation, MLGPvPFall fall, double highestYPosReachedThisFall)
    {
        double StartYPos = GetConfigValues().IsClimbHeightReset ? fall.StartYPosition : highestYPosReachedThisFall;
        return StartYPos - currentLocation.getY();
    }

    private void EndFall(Location currentLocation)
    {
        double HighestReachedYPosThisFall = _highestReachedYCoordSinceLift;
        ResetHighestReachedClimbPosition();

        if (!IsFallingFromExplosion())
        {
            return;
        }

        MLGPvPFall EndedFall = _currentFall;
        _currentFall = null;

        double FallDistance = GetFallDistance(currentLocation, EndedFall, HighestReachedYPosThisFall);
        if (FallDistance <= GetEntity().GetAttributeInstance(Attribute.SAFE_FALL_DISTANCE)
                .map(AttributeInstance::getValue).orElse(0d))
        {
            return;
        }

        if ((EndedFall.PlacedWaterBlock != null) && IsPlayerInSelfPlacedWaterBlock(EndedFall.PlacedWaterBlock))
        {
            OnSuccessfulMLGWaterBucket(FallDistance);
        }
        else if (!GetEntity().GetIsInWater())
        {
            OnFailedMLGWaterBucket(FallDistance);
        }
    }

    private boolean IsPlayerInSelfPlacedWaterBlock(Block waterBlock)
    {
        Location BlockLocation = waterBlock.getLocation();
        BoundingBox BlockBounds = new BoundingBox(BlockLocation.getBlockX(),
                BlockLocation.getBlockY(),
                BlockLocation.getBlockZ(),
                BlockLocation.getBlockX() + 1d,
                BlockLocation.getBlockY() + 1d,
                BlockLocation.getBlockZ() + 1d);

        return BlockBounds.overlaps(GetEntity().GetBounds());
    }

    private void OnLiftFromGroundEvent(GameEntityLiftFromGroundEvent event)
    {
        _isOnGround = false;
    }

    private void OnSuccessfulMLGWaterBucket(double fallDistance)
    {
        GetEntity().ShowActionbar(new ActionbarMessage(ACTIONBAR_DURATION_TICKS,
                Component.text("MLG of %sm landed".formatted(_blockHeightFormat.format(fallDistance)))
                        .color(NamedTextColor.GREEN), _actionbarID));

        final float VOLUME = 0.2f;
        final float PITCH = 2f;
        GetEntity().PlaySound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                GetEntity().GetLocation(),
                SoundCategory.PLAYERS,
                VOLUME,
                PITCH);

        if (GetConfigValues().IsMLGRewardingEnabled)
        {
            GetEntity().RewardMLGWaterBucket(GetClimbFactor(fallDistance));
        }

        _landMLGEvent.FireEvent(new GamePlayerLandMLGEvent(GetEntity(), fallDistance));
    }

    private void OnFailedMLGWaterBucket(double fallDistance)
    {
        GetEntity().ShowActionbar(new ActionbarMessage(ACTIONBAR_DURATION_TICKS,
                Component.text("MLG failed").color(NamedTextColor.RED),
                _actionbarID));

        _failMLGEvent.FireEvent(new GamePlayerFailMLGEvent(GetEntity(), fallDistance));
    }


    // Inherited methods.
    @Override
    public void Tick()
    {
        super.Tick();
        TickClimb();
        TickFall();
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        GetEntity().GetEmptyBucketEvent().Subscribe(this, this::OnEmptyBucketEvent);
        GetEntity().GetLandOnGroundEvent().Subscribe(this, this::OnLandOnGroundEvent);
        GetEntity().GetLiftFromGroundEvent().Subscribe(this, this::OnLiftFromGroundEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        GetEntity().GetEmptyBucketEvent().Unsubscribe(this);
        GetEntity().GetLandOnGroundEvent().Unsubscribe(this);
        GetEntity().GetLiftFromGroundEvent().Unsubscribe(this);
    }


    // Types.
    private static class MLGPvPClimb
    {
        // Fields.
        public final double StartYPosition;
        public int GraceTicks;
        public double PreviousYPosition;


        // Constructors.
        public MLGPvPClimb(double startYPosition, int graceTicks)
        {
            StartYPosition = startYPosition;
            PreviousYPosition = startYPosition;
            GraceTicks = graceTicks;
        }
    }

    private static class MLGPvPFall
    {
        // Fields.
        public final double StartYPosition;
        public Block PlacedWaterBlock = null;


        // Constructors.
        private MLGPvPFall(double startYPosition)
        {
            StartYPosition = startYPosition;
        }
    }
}