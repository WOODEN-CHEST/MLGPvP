package sus.keiger.mlgpvp.game.entity.player.event;

import org.bukkit.event.Cancellable;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Optional;

public class GamePlayerDamageEvent extends GamePlayerEntityEvent
{
    // Private fields.
    private final double _amount;
    private final GameEntity _source;


    // Constructors.
    public GamePlayerDamageEvent(GamePlayerEntity entity, double amount, GameEntity source)
    {
        super(entity, null);
        _amount = amount;
        _source = source;
    }


    // Methods.
    public double GetAmount()
    {
        return _amount;
    }

    public Optional<GameEntity> GetSource()
    {
        return Optional.of(_source);
    }
}