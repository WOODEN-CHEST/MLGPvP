package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.ITickable;

/**
 * Helps ensure that the MLGPvP minigame is in their correct state and are being executed.
 * <br>Only 1 minigame may be active at a time.
 */
public interface IGameSessionExecutor extends IMLGPvPEventListener, ITickable
{
    /**
     * Gets the currently active game instance. This always returns a game instance, event when no game is active.
     * If no game is active, the returned game is simply in it's {@link GameInstanceState#Lobby} state.
     * <br>A game instance returned by this method will never be in the {@link GameInstanceState#Complete} state.
     * @return The current game instance.
     */
    IGameInstance GetCurrentGameInstance();

    /**
     * Gets the currently active game configuration. This config is used in all game instances.
     * @return The current game config.
     */
    GameInstanceValues GetGlobalGameValues();

    /**
     * Starts the current MLGPvP game instance.
     * @return Success if no other game instance was in progress and this method started the current game instance,
     * failure otherwise.
     */
    ExplainedResult StartGame();

    /**
     * Cancels the currently active game instance.
     * @return Success if a game was in progress and got cancelled, failure if no game was in progress or
     * something else prevented the game from being cancelled.
     */
    ExplainedResult CancelGame();
}