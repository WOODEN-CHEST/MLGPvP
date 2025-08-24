package sus.keiger.mlgpvp.player;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.GameInstanceState;
import sus.keiger.mlgpvp.game.IGameInstance;
import sus.keiger.mlgpvp.game.IGameSessionExecutor;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.service.IServerServices;

import java.util.Objects;

public class DefaultPlayerStateController implements IPlayerStateController
{
    // Private fields.
    private final IGameSessionExecutor _sessionExecutor;
    private final IServerServices _services;



    // Constructors.
    public DefaultPlayerStateController(IGameSessionExecutor sessionExecutor, IServerServices services)
    {
        _sessionExecutor = Objects.requireNonNull(sessionExecutor, "sessionExecutor is null");
        _services = Objects.requireNonNull(services, "services is null");;
    }


    // Private methods.
    private void OnPlayerAddEvent(PlayerCollectionAddEvent event)
    {
        IGameInstance CurrentGame = _sessionExecutor.GetCurrentGameInstance();
        if ((CurrentGame.GetState() != GameInstanceState.Lobby))
        {
            if (!CurrentGame.ContainsJoinedPlayer(event.GetPlayer()))
            {
                _sessionExecutor.GetCurrentGameInstance().AddSpectator(event.GetPlayer());
            }
            else
            {
                _sessionExecutor.GetCurrentGameInstance().AddPlayer(event.GetPlayer());
            }
        }
    }

    private void OnPlayerRemoveEvent(PlayerCollectionRemoveEvent event) { }

    private void OnGameCompleteEvent(GameInstanceCompleteEvent event)
    {

    }


    // Inherited methods.
    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        _services.GetPlayerCollection().GetAddEvent().Subscribe(this, this::OnPlayerAddEvent);
        _services.GetPlayerCollection().GetRemoveEvent().Subscribe(this, this::OnPlayerRemoveEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        _services.GetPlayerCollection().GetAddEvent().Unsubscribe(this);
        _services.GetPlayerCollection().GetRemoveEvent().Unsubscribe(this);
    }

    @Override
    public void Tick()
    {

    }
}