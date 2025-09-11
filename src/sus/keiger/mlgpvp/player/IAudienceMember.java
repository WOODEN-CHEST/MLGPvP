package sus.keiger.mlgpvp.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

/**
 * An audience member is something which can receive visual and auditory effects. An audience member is most commonly
 * a player, though it can also be a game instance or a player collection (in which case, the effects are forwarded
 * to all sub audience members).
 * <br>A method call on an audience member will create the effect only for that audience member, it won't affect
 * other audience members. If the audience member is a game, for instance, then only the game's audience members
 * will experience the effect.
 */
public interface IAudienceMember
{

    /**
     * Shows a title.
     * @param title The title to show.
     * @throws NullPointerException if <code>title</code> is <code>null</code>.
     */
    void ShowTitle(Title title);


    /**
     * Clears any existing titles.
     */
    void ClearTitle();


    /**
     * Sends a chat message.
     * @param message The message to send.
     * @throws NullPointerException if <code>message</code> is <code>null</code>.
     */
    void SendMessage(Component message);


    /**
     * Shows the given actionbar message.
     * @param message The message to show.
     * @throws NullPointerException if <code>message</code> is <code>null</code>.
     */
    void ShowActionbar(ActionbarMessage message);


    /**
     * Clears any actionbar messages.
     */
    void ClearActionbar();


    /**
     * Removes the actionbar with the given ID.
     * <br>This does nothing if no actionbar with the given ID exists.
     * @param id The ID of the actionbar.
     */
    void RemoveActionbar(long id);


    /**
     * Spawns particle(s) in the given bounding box.
     * @param particle The type of particle to spawn.
     * @param location The center location for the particle bounding box.
     * @param deltaX The width of the bounding bow on the X axis.
     * @param deltaY The height of the bounding bow on the Y axis.
     * @param deltaZ The width of the bounding bow on the Z axis.
     * @param count The count of particles to spawn.
     * @param extra Extra data for the particle, usually animation speed.
     * @param data Even more extra data for the particle, usually null for nothing.
     *             See <a href="https://jd.papermc.io/paper/1.21.8/org/bukkit/Particle.html">Particle</a>
     * @param <T> The type of the extra data.
     */
    <T> void SpawnParticle(Particle particle,
                           Location location,
                           double deltaX,
                           double deltaY,
                           double deltaZ,
                           int count,
                           double extra,
                           T data);


    /**
     * Plays a sound.
     * @param sound The type of sound to play.
     * @param location The location at which to play it.
     * @param category The category of the sound.
     * @param volume The volume, 1.0 being normal, 0.0 being none and > 1.0 being loud.
     * @param pitch The pitch, in the range [0.5;2.0]
     */
    void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch);
}