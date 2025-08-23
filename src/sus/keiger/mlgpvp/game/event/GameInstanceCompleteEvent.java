package sus.keiger.mlgpvp.game.event;

import sus.keiger.mlgpvp.game.IGameInstance;

public class GameInstanceCompleteEvent extends GameInstanceEvent
{
    public GameInstanceCompleteEvent(IGameInstance gameInstance)
    {
        super(gameInstance);
    }
}