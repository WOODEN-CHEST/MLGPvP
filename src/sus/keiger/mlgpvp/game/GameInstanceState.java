package sus.keiger.mlgpvp.game;

/**
 * The state of a {@link IGameInstance} object.
 */
public enum GameInstanceState
{

    /**
     * In the lobby state, the game is inactive, and players can freely join or quit the game instance.
     */
    Lobby,

    /**
     * In the in game state, the game is active, and players cannot join or quit it, except for spectators.
     */
    InGame,

    /**
     * A complete game should be instantly disposed of, the game has ended, and it no longer has a use.
     */
    Complete;
}