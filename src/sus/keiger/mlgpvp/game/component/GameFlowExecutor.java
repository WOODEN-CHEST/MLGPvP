package sus.keiger.mlgpvp.game.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.TickClock;

import java.util.Optional;

public class GameFlowExecutor extends GameComponent<MLGPvPGameInstance>
{
    // Private fields.
    private final GameEndContentDisplayer _endContentDisplayer;

    private int _alivePlayerCount = 0;



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
        if (Winner.isPresent())
        {
            Winner.ifPresent(winner -> _endContentDisplayer.DisplayEndContent(winner));
        }
        else
        {
            GetGameInstance().SendMessage(Component.text("Game ended with no winner!").color(NamedTextColor.RED));
        }

        GetGameInstance().SwitchToCompleteState();
    }

    private void OnGameStartEvent(GameInstanceStartEvent event)
    {
        GetGameInstance().SendMessage(Component.text("Game started!").color(NamedTextColor.GREEN));
    }


    // Inherited methods.
    @Override
    public void Tick()
    {
        super.Tick();
        UpdateAlivePlayerCount(); // Updating this every tick is godly inefficient, but I gave up trying to
        // make it work with events, too many bugs.
        TryGameEndByPlayerCount();
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