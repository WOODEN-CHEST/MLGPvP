package sus.keiger.mlgpvp.game;

import org.bukkit.Location;
import sus.keiger.mlgpvp.player.IAudienceMember;

public interface IExplosionCreator
{
    void CreateExplosion(ExplosionCreateOptions options);
}