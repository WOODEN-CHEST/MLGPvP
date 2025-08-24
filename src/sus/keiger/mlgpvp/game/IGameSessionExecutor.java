package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.ITickable;

public interface IGameSessionExecutor extends IMLGPvPEventListener, ITickable
{
    IGameInstance GetCurrentGameInstance();
    GameInstanceValues GetGlobalGameValues();
    ExplainedResult StartGame();
    ExplainedResult CancelGame();
}