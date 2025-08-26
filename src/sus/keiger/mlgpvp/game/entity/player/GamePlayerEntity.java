package sus.keiger.mlgpvp.game.entity.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.component.PlayerBukkitEventHandler;
import sus.keiger.mlgpvp.game.entity.player.component.PlayerInitializer;
import sus.keiger.mlgpvp.game.entity.player.component.PlayerLifeTracker;
import sus.keiger.mlgpvp.player.IAudienceMember;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.util.Objects;

public class GamePlayerEntity extends GameEntity implements IAudienceMember
{
    // Private fields.
    private final IServerPlayer _serverPlayer;

    private final PlayerInitializer _initializer;
    private final PlayerLifeTracker _lifeTracker;
    private final PlayerBukkitEventHandler _eventHandler;


    // Constructors.
    public GamePlayerEntity(IGameInstanceExtended gameInstance, IServerPlayer serverPlayer)
    {
        super(gameInstance, Objects.requireNonNull(serverPlayer, "serverPlayer is null").GetUnderlyingPlayer());
        _serverPlayer = serverPlayer;

        _initializer = new PlayerInitializer(this);
        _lifeTracker = new PlayerLifeTracker(this);
        _eventHandler = new PlayerBukkitEventHandler(this);

        AddComponent(_initializer);
        AddComponent(_lifeTracker);
        AddComponent(_eventHandler);
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
        AttributeInstance Health = GetPlayerEntity().getAttribute(Attribute.MAX_HEALTH);
        if (Health != null)
        {
            GetPlayerEntity().setHealth(Health.getValue());
        }
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