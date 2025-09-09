package sus.keiger.mlgpvp.game.component;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;

public class GamePlayerStateEnsurer extends GameComponent<MLGPvPGameInstance>
{
    // Constructors.
    public GamePlayerStateEnsurer(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
    }


    // Private methods.
    private void OnGameStartEvent(GameInstanceStartEvent event)
    {
        GetGameInstance().GetOnlinePlayers().forEach(player ->
        {
            GetGameInstance().AddEntity(new GamePlayerEntity(GetGameInstance(), player));
        });
    }



    // Inherited methods.

    @Override
    public void Tick()
    {
        super.Tick();
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        GetGameInstance().GetStartEvent().Subscribe(this, this::OnGameStartEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        GetGameInstance().GetStartEvent().Unsubscribe(this);
    }
}