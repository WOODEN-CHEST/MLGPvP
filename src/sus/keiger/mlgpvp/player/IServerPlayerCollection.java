package sus.keiger.mlgpvp.player;

import org.bukkit.entity.Player;
import sus.keiger.plugincommon.PCPluginEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface IServerPlayerCollection extends IAudienceMemberHolder
{

    /**
     * Adds a player to this collection.
     * <br>If a player with the same UUID already exists, then their player instance in this map
     * is replaced with the new one supplied to this method.
     * <br>This method <b>always</b> raises the <code>PlayerCollectionAddEvent</code> event after the player is
     * added regardless of whether they existed in this collection before or not.
     * @param player The player to add.
     *               @throws NullPointerException if <code>player</code> is <code>null</code>.
     */
    void AddPlayer(IServerPlayer player);


    /**
     * Removes a player from this collection.
     * <br>This raised the <code>PlayerCollectionRemoveEvent</code> event after the removal
     * if the player was part of the collection.
     * @param player The player to remove.
     * @throws NullPointerException if <code>player</code> is <code>null</code>.
     */
    void RemovePlayer(IServerPlayer player);


    /**
     * Gets a player by their UUID.
     * @param playerUUID The player's UUID.
     * @return The player with the matching UUID, or empty if no such player was found.
     * @throws NullPointerException if <code>playerUUID</code> is <code>null</code>.
     */
    Optional<IServerPlayer> GetPlayer(UUID playerUUID);

    /**
     * Gets a player by their wrapped player entity.
     * @param player The wrapped player.
     * @return The player with the matching wrapped player entity, or empty if no such player was found.
     * @throws NullPointerException if <code>player</code> is <code>null</code>.
     */
    Optional<IServerPlayer> GetPlayer(Player player);


    /**
     * Gets an immutable view of the players int this collection.
     * <br>This method is optimised so that repeated calls to this method do not create new lists (unless the
     * player count changes).
     * @return An immutable list of the players in this collection.
     */
    List<IServerPlayer> GetPlayers();


    /**
     * @return The event fired after a player is added to this collection.
     */
    PCPluginEvent<PlayerCollectionAddEvent> GetAddEvent();


    /**
     * @return The event fired after a player is removed from this collection.
     */
    PCPluginEvent<PlayerCollectionRemoveEvent> GetRemoveEvent();
}