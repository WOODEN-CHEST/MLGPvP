package sus.keiger.mlgpvp;

import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstance;

import java.util.Optional;

public interface IGameSessionExecutor extends IMLGPvPEventListener
{
    Optional<IGameInstance> GetCurrentGameInstance();
    GameInstanceValues GetGlobalGameValues();
    void StartGame();
    void StopGame();
}