package sus.keiger.mlgpvp.game.entity.player.component;

import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.PlayerGameEntity;

public class PlayerLifeTracker extends GameEntityComponent<PlayerGameEntity>
{
    // Private fields.
    private boolean _isAlive = true;


    // Constructors.
    public PlayerLifeTracker(PlayerGameEntity entity)
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
}