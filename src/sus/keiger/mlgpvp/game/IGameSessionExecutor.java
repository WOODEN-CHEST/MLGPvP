package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.ITickable;
import sus.keiger.plugincommon.PCPluginEvent;

public interface IGameSessionExecutor extends IMLGPvPEventListener, ITickable
{
    IGameInstance GetCurrentGameInstance();
    GameInstanceValues GetGlobalGameValues();
    ExplainedResult StartGame();
    ExplainedResult CancelGame();
    PCPluginEvent<GameInstanceCompleteEvent> GetGameCompleteEvent();
}