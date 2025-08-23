package sus.keiger.mlgpvp.game.event;

import sus.keiger.mlgpvp.game.IGameInstance;

import java.util.Objects;

public abstract class GameInstanceEvent
{
    // Private fields.
    private final IGameInstance _gameInstance;


    // Constructors.
    public GameInstanceEvent(IGameInstance gameInstance)
    {
        _gameInstance = Objects.requireNonNull(gameInstance, "gameInstance is null");
    }


    // Methods.
    public IGameInstance GetGameInstance()
    {
        return _gameInstance;
    }
}