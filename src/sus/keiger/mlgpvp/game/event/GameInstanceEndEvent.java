package sus.keiger.mlgpvp.game.event;

import sus.keiger.mlgpvp.game.IGameInstance;

public class GameInstanceEndEvent extends GameInstanceEvent
{
    public GameInstanceEndEvent(IGameInstance gameInstance)
    {
        super(gameInstance);
    }
}