package sus.keiger.mlgpvp.game.component;

import org.bukkit.entity.Entity;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.PlayerGameEntity;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.ITickable;

import java.util.*;

public class GameEntityCollection extends GameComponent<IGameInstanceExtended>
{
    // Private fields.
    private final Map<Entity, GameEntity> _entities = new HashMap<>();
    private List<GameEntity> _entitiesCopy = Collections.emptyList();
    private final Map<IServerPlayer, PlayerGameEntity> _playerEntities = new HashMap<>();


    // Constructors.
    public GameEntityCollection(IGameInstanceExtended gameInstance)
    {
        super(gameInstance);
    }


    // Methods.
    public void AddEntity(GameEntity entity)
    {
        Objects.requireNonNull(entity, "entity is null");
        _entities.put(entity.GetUnderlyingEntity(), entity);
        entity.Initialize();

        if (entity instanceof PlayerGameEntity PlayerEntity)
        {
            _playerEntities.put(PlayerEntity.GetServerPlayer(), PlayerEntity);
        }

        UpdateEntityList();
    }

    public void RemoveEntity(GameEntity entity)
    {
        _entities.remove(Objects.requireNonNull(entity, "entity is null").GetUnderlyingEntity());
        entity.Delete();
        entity.RemoveCleanup();
        UpdateEntityList();
    }

    public List<GameEntity> GetEntities()
    {
        return _entitiesCopy;
    }

    public int GetEntityCount()
    {
        return _entities.size();
    }

    public boolean TryReAddPlayer(IServerPlayer player)
    {
        PlayerGameEntity PlayerEntity = _playerEntities.get(Objects.requireNonNull(player, "player is null"));
        if (PlayerEntity == null)
        {
            return false;
        }
        AddEntity(PlayerEntity);
        return true;
    }


    // Private methods.
    private void UpdateEntityList()
    {
        _entitiesCopy = List.copyOf(_entities.values());
    }



    // Inherited methods.
    @Override
    public void Tick()
    {
        super.Tick();

        _entitiesCopy.forEach(ITickable::Tick);
    }
}
