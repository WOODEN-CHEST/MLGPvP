package sus.keiger.mlgpvp.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.util.Objects;
import java.util.UUID;

public class MLGPvPPlayer implements IServerPlayer
{
    // Private fields.
    private Player _underlyingPlayer;


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
        return null;
    }

    @Override
    public String GetName()
    {
        return "";
    }

    @Override
    public void AddReference(Object user)
    {

    }

    @Override
    public void RemoveReference(Object user)
    {

    }

    @Override
    public int GetReferenceCount()
    {
        return 0;
    }

    @Override
    public void ShowTitle(Title title)
    {

    }

    @Override
    public void ClearTitle()
    {

    }

    @Override
    public void SendMessage(Component message)
    {

    }

    @Override
    public void ShowActionbar(ActionbarMessage message)
    {

    }

    @Override
    public void ClearActionbar()
    {

    }

    @Override
    public void RemoveActionbar(long id)
    {

    }

    @Override
    public <T> void SpawnParticle(Particle particle, Location location, double deltaX, double deltaY, double deltaZ, int count, double extra, T data)
    {

    }

    @Override
    public void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch)
    {

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
}
