package sus.keiger.mlgpvp.game;

import org.bukkit.Location;
import sus.keiger.mlgpvp.player.IAudienceMember;

/**
 * An object which creates custom explosions for the MLGPvP minigame.
 */
public interface IExplosionCreator
{
    /**
     * Creates an explosion with the given options. The explosion is created only for the game instance that
     * this object operates on.
     * @param options The options for the explosion.
     */
    void CreateExplosion(ExplosionCreateOptions options);
}