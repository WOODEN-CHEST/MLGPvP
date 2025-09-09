package sus.keiger.mlgpvp.game.event;

import sus.keiger.mlgpvp.game.IGameInstance;

public class GameBorderBeginShrinkEvent extends GameInstanceEvent
{
    // Constructors.
    public GameBorderBeginShrinkEvent(IGameInstance gameInstance)
    {
        super(gameInstance);
    }
}