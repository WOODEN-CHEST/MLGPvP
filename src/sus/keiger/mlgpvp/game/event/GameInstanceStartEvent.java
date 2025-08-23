package sus.keiger.mlgpvp.game.event;

import sus.keiger.mlgpvp.game.IGameInstance;

public class GameInstanceStartEvent extends GameInstanceEvent
{
    public GameInstanceStartEvent(IGameInstance gameInstance)
    {
        super(gameInstance);
    }
}