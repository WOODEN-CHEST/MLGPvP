package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.game.entity.GameEntity;

import java.util.List;

public interface IGameInstanceExtended extends IGameInstance
{
    void AddEntity(GameEntity entity);
    void RemoveEntity(GameEntity entity);
    List<GameEntity> GetEntities();
    int GetEntityCount();
    void SetState(GameInstanceState state);
}