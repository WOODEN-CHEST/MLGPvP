package sus.keiger.mlgpvp.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.player.actionbar.ActionbarContainer;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MLGPvPPlayer implements IServerPlayer
{
    // Private fields.
    private Player _underlyingPlayer;
    private final Set<Object> _references = new HashSet<>();
    private final PCPluginEvent<PlayerReferenceCountChangeEvent> _referenceChangeEvent = new PCPluginEvent<>();
    private final ActionbarContainer _actionbar = new ActionbarContainer();


    // Constructors.
    public MLGPvPPlayer(Player underlyingPlayer)
    {
        SetUnderlyingPlayer(underlyingPlayer);
    }


    @Override
    public Player GetUnderlyingPlayer()
    {
        return _underlyingPlayer;
    }

    @Override
    public void SetUnderlyingPlayer(Player player)
    {
        _underlyingPlayer = Objects.requireNonNull(player, "player is null");
    }

    @Override
    public UUID GetUUID()
    {
        return _underlyingPlayer.getUniqueId();
    }

    @Override
    public String GetName()
    {
        return _underlyingPlayer.getName();
    }

    @Override
    public void AddReference(Object user)
    {
        Objects.requireNonNull(user, "user is null");
        if (_references.add(user))
        {
            _referenceChangeEvent.FireEvent(new PlayerReferenceCountChangeEvent(this));
        }
    }

    @Override
    public void RemoveReference(Object user)
    {
        Objects.requireNonNull(user, "user is null");
        if (_references.remove(user))
        {
            _referenceChangeEvent.FireEvent(new PlayerReferenceCountChangeEvent(this));
        }
    }

    @Override
    public int GetReferenceCount()
    {
        return _references.size();
    }

    @Override
    public boolean GetIsOnline()
    {
        return _underlyingPlayer.isConnected();
    }

    @Override
    public PCPluginEvent<PlayerReferenceCountChangeEvent> GetReferenceCountChangeEvent()
    {
        return _referenceChangeEvent;
    }

    @Override
    public void ShowTitle(Title title)
    {
        _underlyingPlayer.showTitle(title);
    }

    @Override
    public void ClearTitle()
    {
        _underlyingPlayer.clearTitle();
    }

    @Override
    public void SendMessage(Component message)
    {
        _underlyingPlayer.sendMessage(message);
    }

    @Override
    public void ShowActionbar(ActionbarMessage message)
    {
        _actionbar.AddMessage(message);
    }

    @Override
    public void ClearActionbar()
    {
        _actionbar.ClearMessages();
    }

    @Override
    public void RemoveActionbar(long id)
    {
        _actionbar.RemoveMessage(id);
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
        _underlyingPlayer.spawnParticle(particle, location, count, deltaX, deltaY, deltaZ, extra, data);
    }

    @Override
    public void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch)
    {
        _underlyingPlayer.playSound(location, sound, category, volume, pitch);
    }

    @Override
    public int hashCode()
    {
        return _underlyingPlayer.getUniqueId().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof IServerPlayer TargetPlayer)
        {
            return TargetPlayer.GetUUID().equals(GetUUID());
        }
        return false;
    }

    @Override
    public void Tick()
    {
        _actionbar.Tick(_underlyingPlayer); // This may mess with other plugins which use the actionbar :/
    }
}
