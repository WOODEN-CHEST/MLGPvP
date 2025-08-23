package sus.keiger.mlgpvp.game.entity;

import org.bukkit.entity.Entity;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;

import java.util.Objects;

public class GameEntity
{
    // Private fields.
    private final IGameInstanceExtended _gameInstance;
    private final Entity _wrappedEntity;



    // Constructors.
    public GameEntity(IGameInstanceExtended gameInstance, Entity wrappedEntity)
    {
        _gameInstance = Objects.requireNonNull(gameInstance, "gameInstance is null");
        _wrappedEntity = Objects.requireNonNull(wrappedEntity, "wrappedEntity is null");
    }


    // Methods.
    public Entity GetUnderlyingEntity()
    {
        return _wrappedEntity;
    }

    public IGameInstanceExtended GetGameInstance()
    {
        return _gameInstance;
    }

    public GameInstanceValues GetConfigValues()
    {
        return _gameInstance.GetConfigValues();
    }
}