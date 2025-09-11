package sus.keiger.mlgpvp.player;

import org.bukkit.entity.Player;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.plugincommon.ITickable;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.UUID;

/**
 * A wrapper player used everywhere in this server in place of a Bukkit player.
 * <br>This player has a "reference" counter which doesn't count object references but rather the count of
 * plugin components which use this player instance. For as long as there is at least 1 reference, an instance
 * of this interface will NOT be disposed of regardless of whether the underlying player is online or not.
 * <br>An example of an object which references the player is a MLGPvP minigame instance.
 */
public interface IServerPlayer extends IAudienceMember, ITickable, IMLGPvPEventListener
{

    /**
     * @return The player this object wraps.
     */
    Player GetUnderlyingPlayer();


    /**
     * Sets the wrapped player. This should only be used if the currently wrapped player instance is already disposed.
     * @param player The new player this object should wrap.
     */
    void SetUnderlyingPlayer(Player player);


    /**
     * @return The UUID of the wrapped player.
     */
    UUID GetUUID();

    /**
     * @return The name of the wrapped player.
     */
    String GetName();


    /**
     * Tracks that the given object is using this player.
     * <br>If the user already registered for this player, this does nothing.
     * @param user The object which is using the player.
     */
    void AddReference(Object user);


    /**
     * Tracks that the given object is no longer using this player.
     * <br>If the user is already not registered for this player, this does nothing.
     * @param user The object which stopped using this player.
     */
    void RemoveReference(Object user);


    /**
     * @return The amount of references to this player.
     */
    int GetReferenceCount();


    /**
     * @return <code>true</code> if the player is online, <code>false</code> otherwise.
     */
    boolean GetIsOnline();

    /**
     * @return The event raised after the reference count changes.
     */
    PCPluginEvent<PlayerReferenceCountChangeEvent> GetReferenceCountChangeEvent();


    /**
     * @return The event raised after the player disconnects.
     */
    PCPluginEvent<ServerPlayerDisconnectEvent> GetDisconnectEvent();


    /**
     * This event is raised only if the player has at least 1 reference, because a player with no
     * references is disposed of and thus never sees this event happen when they reconnect
     * (a new player is created instead).
     * @return The event raised when this player reconnects.
     */
    PCPluginEvent<ServerPlayerReconnectEvent> GetReconnectEvent();
}