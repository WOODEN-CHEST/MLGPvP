package sus.keiger.mlgpvp.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

public interface IAudienceMember
{
    void ShowTitle(Title title);
    void ClearTitle();
    void SendMessage(Component message);
    void ShowActionbar(ActionbarMessage message);
    void ClearActionbar();
    void RemoveActionbar(long id);
    <T> void SpawnParticle(Particle particle,
                           Location location,
                           double deltaX,
                           double deltaY,
                           double deltaZ,
                           int count,
                           double extra,
                           T data);
    void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch);
}