package sus.keiger.mlgpvp.game.entity.component;

import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.GameEntity;

import java.util.Objects;

public class GameEntityComponent<T extends GameEntity>
{
    // Private fields.
    private final T _entity;


    // Constructors.
    public GameEntityComponent(T entity)
    {
        _entity = Objects.requireNonNull(entity, "entity is null");
    }


    // Methods.
    public T GetEntity()
    {
        return _entity;
    }

    public IGameInstanceExtended GetGameInstance()
    {
        return _entity.GetGameInstance();
    }

    public GameInstanceValues GetConfigValues()
    {
        return _entity.GetConfigValues();
    }
}