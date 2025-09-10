package sus.keiger.mlgpvp.game.entity.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.component.*;
import sus.keiger.mlgpvp.game.entity.player.event.*;
import sus.keiger.mlgpvp.player.IAudienceMember;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.util.Objects;
import java.util.Optional;

public class GamePlayerEntity extends GameEntity implements IAudienceMember
{
    // Private fields.
    private final IServerPlayer _serverPlayer;

    private final PlayerLifeTracker _lifeTracker;
    private final PlayerBukkitEventHandler _eventHandler;
    private final PlayerStateInitializer _stateInitializer;
    private final PlayerMLGTracker _mlgTracker;
    private final PlayerYCoordBooster _YPosBooster;
    private final MLGRewarder _mlgRewarder;
    private final AdvancementFilter _advancementFilter;
    private final PlayerStatTracker _statTracker;


    // Constructors.
    public GamePlayerEntity(IGameInstanceExtended gameInstance, IServerPlayer serverPlayer)
    {
        super(gameInstance, Objects.requireNonNull(serverPlayer, "serverPlayer is null").GetUnderlyingPlayer());
        _serverPlayer = serverPlayer;

        _advancementFilter = new AdvancementFilter(this);
        _lifeTracker = new PlayerLifeTracker(this);
        _eventHandler = new PlayerBukkitEventHandler(this);
        _stateInitializer = new PlayerStateInitializer(this);
        _mlgTracker = new PlayerMLGTracker(this);
        _YPosBooster = new PlayerYCoordBooster(this);
        _mlgRewarder = new MLGRewarder(this);
        _statTracker = new PlayerStatTracker(this);

        AddComponent(_lifeTracker);
        AddComponent(_eventHandler);
        AddComponent(_stateInitializer);
        AddComponent(_mlgTracker);
        AddComponent(_YPosBooster);
        AddComponent(_mlgRewarder);
        AddComponent(_advancementFilter);
        AddComponent(_statTracker);
    }


    // Methods.
    public Player GetPlayerEntity()
    {
        return (Player)GetUnderlyingEntity();
    }

    public IServerPlayer GetServerPlayer()
    {
        return _serverPlayer;
    }

    public void SetFood(int food)
    {
        GetPlayerEntity().setFoodLevel(food);
    }

    public void SetSaturation(float saturation)
    {
        GetPlayerEntity().setSaturation(saturation);
    }

    public void ClearPotionEffects()
    {
        GetPlayerEntity().clearActivePotionEffects();
    }

    public void SetGameMode(GameMode mode)
    {
        GetPlayerEntity().setGameMode(mode);
    }

    public void SetIsAlive(boolean isAlive)
    {
        _lifeTracker.SetIsAlive(isAlive);
    }

    public boolean GetIsAlive()
    {
        return _lifeTracker.GetIsAlive();
    }

    public void Damage(double amount)
    {
        _lifeTracker.Damage(amount);
    }

    public void ResetHealth()
    {
        _lifeTracker.ResetHealth();
    }

    public void Spawn()
    {
        _lifeTracker.Spawn();
    }

    public PCPluginEvent<PlayerLifeChangeEvent> GetLifeChangeEvent()
    {
        return _lifeTracker.GetLifeChangeEvent();
    }

    public void MarkClimbStart()
    {
        _mlgTracker.MarkClimbStart();
    }

    public Optional<AttributeInstance> GetAttributeInstance(Attribute attribute)
    {
        return Optional.ofNullable(GetPlayerEntity().getAttribute(attribute));
    }

    public void SetYBoost(double amount)
    {
        _YPosBooster.SetRemainingYBoost(amount);
    }

    public PCPluginEvent<GamePlayerEmptyBucketEvent> GetEmptyBucketEvent()
    {
        return _eventHandler.GetEmptyBucketEvent();
    }

    public void RewardMLGWaterBucket(double fallFactor)
    {
        _mlgRewarder.RewardPlayer(fallFactor);
    }

    public PCPluginEvent<GamePlayerFireArrowEvent> GetFireArrowEvent()
    {
        return _eventHandler.GetFireArrowEvent();
    }

    public PCPluginEvent<GamePlayerClimbEndEvent> GetClimbEndEvent()
    {
        return _mlgTracker.GetClimbEndEvent();
    }

    public PCPluginEvent<GamePlayerLandMLGEvent> GetLandMLGEvent()
    {
        return _mlgTracker.GetLandMLGEvent();
    }

    public PCPluginEvent<GamePlayerFailMLGEvent> GetFailMLGEvent()
    {
        return _mlgTracker.GetFailMLGEvent();
    }

    public PCPluginEvent<GamePlayerTakeDamageEvent> GetDamageTakeEvent()
    {
        return _eventHandler.GetDamageTakeEvent();
    }

    public void SetScoreboard(Scoreboard scoreboard)
    {
        GetPlayerEntity().setScoreboard(scoreboard);
    }



    // Inherited methods.
    @Override
    public void Delete()
    {
        /* Players should never be deleted. */
    }

    @Override
    public void ShowTitle(Title title)
    {
        GetServerPlayer().ShowTitle(title);
    }

    @Override
    public void ClearTitle()
    {
        GetServerPlayer().ClearTitle();
    }

    @Override
    public void SendMessage(Component message)
    {
        GetServerPlayer().SendMessage(message);
    }

    @Override
    public void ShowActionbar(ActionbarMessage message)
    {
        GetServerPlayer().ShowActionbar(message);
    }

    @Override
    public void ClearActionbar()
    {
        GetServerPlayer().ClearActionbar();
    }

    @Override
    public void RemoveActionbar(long id)
    {
        GetServerPlayer().RemoveActionbar(id);
    }

    @Override
    public <T> void SpawnParticle(Particle particle,
                                  Location location,
                                  double deltaX,
                                  double deltaY,
                                  double deltaZ,
                                  int count,
                                  double extra,
                                  T data)
    {
        GetServerPlayer().SpawnParticle(particle, location, deltaX, deltaY, deltaZ, count, extra, data);
    }

    @Override
    public void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch)
    {
        GetServerPlayer().PlaySound(sound, location, category, volume, pitch);
    }
}