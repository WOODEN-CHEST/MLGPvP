package sus.keiger.mlgpvp.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.util.*;

public class DefaultPlayerCollection implements IServerPlayerCollection
{
    // Private fields.
    private final Map<UUID, IServerPlayer> _players = new HashMap<>();
    private List<IServerPlayer> _playersCopy = Collections.emptyList();
    private final PCPluginEvent<PlayerCollectionAddEvent> _addEvent = new PCPluginEvent<>();
    private final PCPluginEvent<PlayerCollectionRemoveEvent> _removeEvent = new PCPluginEvent<>();


    // Private methods.
    private void UpdatePlayersCopy()
    {
        _playersCopy = List.copyOf(_players.values());
    }


    // Inherited methods.
    @Override
    public void AddPlayer(IServerPlayer player)
    {
        Objects.requireNonNull(player, "player is null");
        _players.put(player.GetUUID(), player);
        UpdatePlayersCopy();
        _addEvent.FireEvent(new PlayerCollectionAddEvent(player));
    }

    @Override
    public void RemovePlayer(IServerPlayer player)
    {
        Objects.requireNonNull(player, "player is null");
        if (_players.remove(player.GetUUID(), player))
        {
            UpdatePlayersCopy();
            _addEvent.FireEvent(new PlayerCollectionAddEvent(player));
        }
    }

    @Override
    public List<IServerPlayer> GetPlayers()
    {
        return _playersCopy;
    }

    @Override
    public PCPluginEvent<PlayerCollectionAddEvent> GetAddEvent()
    {
        return _addEvent;
    }

    @Override
    public PCPluginEvent<PlayerCollectionRemoveEvent> GetRemoveEvent()
    {
        return _removeEvent;
    }

    @Override
    public List<? extends IAudienceMember> GetAudienceMembers()
    {
        return _playersCopy;
    }

    @Override
    public void ShowTitle(Title title)
    {
        _playersCopy.forEach(player -> player.ShowTitle(title));
    }

    @Override
    public void ClearTitle()
    {
        _playersCopy.forEach(IAudienceMember::ClearTitle);
    }

    @Override
    public void SendMessage(Component message)
    {
        _playersCopy.forEach(player -> player.SendMessage(message));
    }

    @Override
    public void ShowActionbar(ActionbarMessage message)
    {
        _playersCopy.forEach(player -> player.ShowActionbar(message));
    }

    @Override
    public void ClearActionbar()
    {
        _playersCopy.forEach(IAudienceMember::ClearActionbar);
    }

    @Override
    public void RemoveActionbar(long id)
    {
        _playersCopy.forEach(player -> player.RemoveActionbar(id));
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
        _playersCopy.forEach(player -> player.SpawnParticle(
                particle, location, deltaX, deltaY, deltaZ, count, extra, data));
    }

    @Override
    public void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch)
    {
        _playersCopy.forEach(player -> player.PlaySound(sound, location, category, volume, pitch));
    }
}
