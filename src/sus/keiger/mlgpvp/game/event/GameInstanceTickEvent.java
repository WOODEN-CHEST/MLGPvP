package sus.keiger.mlgpvp.game.event;

import sus.keiger.mlgpvp.game.IGameInstance;

public class GameInstanceTickEvent extends GameInstanceEvent
{
    public GameInstanceTickEvent(IGameInstance gameInstance)
    {
        super(gameInstance);
    }
}