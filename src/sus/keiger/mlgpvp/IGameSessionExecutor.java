package sus.keiger.mlgpvp;

import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstance;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.ITickable;

import java.util.Optional;

public interface IGameSessionExecutor extends IMLGPvPEventListener, ITickable
{
    Optional<IGameInstance> GetCurrentGameInstance();
    GameInstanceValues GetGlobalGameValues();
    ExplainedResult StartGame();
    ExplainedResult StopGame();
}