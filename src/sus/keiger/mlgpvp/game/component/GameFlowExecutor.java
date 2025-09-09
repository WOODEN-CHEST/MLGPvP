package sus.keiger.mlgpvp.game.component;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.PlayerLifeChangeEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.player.IServerPlayer;

import java.util.Optional;

public class GameFlowExecutor extends GameComponent<MLGPvPGameInstance>
{
    // Private fields.
    private int _alivePlayerCount = 0;
    private final GameEndContentDisplayer _endContentDisplayer;



    // Constructors.
    public GameFlowExecutor(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
        _endContentDisplayer = new GameEndContentDisplayer(gameInstance);
    }


    // Private methods.
    private void UpdateAlivePlayerCount()
    {
        _alivePlayerCount = (int)
                GetGameInstance()
                .GetEntities()
                .stream()
                .filter(entity -> (entity instanceof GamePlayerEntity Player) && Player.GetIsAlive())
                .count();
    }

    private void TryGameEndByPlayerCount()
    {
        if ((_alivePlayerCount <= 1) && (_alivePlayerCount != GetGameInstance().GetStartingPlayerCount()))
        {
            EndGame();
        }
    }

    private Optional<IServerPlayer> GetWinner()
    {
        if (_alivePlayerCount <= 0)
        {
            return Optional.empty();
        }

        for (IServerPlayer Player : GetGameInstance().GetOnlinePlayers())
        {
            Optional<GameEntity> Entity = GetGameInstance().GetEntity(Player.GetUnderlyingPlayer());
            if (Entity.isPresent()
                    && (Entity.get() instanceof GamePlayerEntity PlayerEntity)
                    && PlayerEntity.GetIsAlive())
            {
                return Optional.of(PlayerEntity.GetServerPlayer());
            }
        }

        return Optional.empty();
    }

    private void EndGame()
    {
        Optional<IServerPlayer> Winner = GetWinner();
        Winner.ifPresent(winner -> _endContentDisplayer.DisplayEndContent(winner));
    }


    // Inherited methods.

    @Override
    public void Tick()
    {
        super.Tick();
        TryGameEndByPlayerCount();
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);
    }
}