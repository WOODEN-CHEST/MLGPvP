package sus.keiger.mlgpvp.game.entity.arrow;

import org.bukkit.entity.Arrow;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.arrow.component.ArrowBukkitEventHandler;
import sus.keiger.mlgpvp.game.entity.arrow.component.ArrowVisualsExecutor;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Optional;

public class GameArrowEntity extends GameEntity
{
    // Private fields.
    private final double _explosionStrengthScale;
    private final GamePlayerEntity _shooter;

    private final ArrowBukkitEventHandler _eventHandler;
    private final ArrowVisualsExecutor _visualsExecutor;


    // Constructors.
    public GameArrowEntity(IGameInstanceExtended gameInstance,
                           Arrow wrappedEntity,
                           GamePlayerEntity shooter,
                           double explosionStrengthScale)
    {
        super(gameInstance, wrappedEntity);
        _explosionStrengthScale = explosionStrengthScale;
        _shooter = shooter;

        _eventHandler = new ArrowBukkitEventHandler(this);
        _visualsExecutor = new ArrowVisualsExecutor(this);

        AddComponent(_eventHandler);
        AddComponent(_visualsExecutor);
    }


    // Methods.
    public Arrow GetArrowEntity()
    {
        return (Arrow)GetUnderlyingEntity();
    }

    public double GetExplosionStrengthScale()
    {
        return _explosionStrengthScale;
    }

    public Optional<GamePlayerEntity> GetShooter()
    {
        return Optional.ofNullable(_shooter);
    }
}
