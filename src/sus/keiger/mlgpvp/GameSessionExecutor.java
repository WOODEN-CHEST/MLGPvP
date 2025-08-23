package sus.keiger.mlgpvp;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstance;

import java.util.Optional;

public class GameSessionExecutor implements IGameSessionExecutor
{



    // Inherited methods.
    @Override
    public Optional<IGameInstance> GetCurrentGameInstance()
    {
        return Optional.empty();
    }

    @Override
    public GameInstanceValues GetGlobalGameValues()
    {
        return null;
    }

    @Override
    public void StartGame()
    {

    }

    @Override
    public void StopGame()
    {

    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {

    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {

    }
}