package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.Bukkit;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

public class PlayerLifeTracker extends GameEntityComponent<GamePlayerEntity>
{
    // Private fields.
    private boolean _isAlive = true;


    // Constructors.
    public PlayerLifeTracker(GamePlayerEntity entity)
    {
        super(entity);
    }


    // Constructors.
    public void SetIsAlive(boolean isAlive)
    {
        _isAlive = isAlive;
    }

    public boolean GetIsAlive()
    {
        return _isAlive;
    }

    public void Damage(double amount)
    {
        if (!GetIsAlive())
        {
            return;
        }

        GetEntity().GetPlayerEntity().damage(amount);
    }
}