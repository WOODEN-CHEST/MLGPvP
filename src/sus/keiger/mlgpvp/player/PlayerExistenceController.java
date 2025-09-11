package sus.keiger.mlgpvp.player;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.mlgpvp.service.IServerServices;

import java.util.Objects;
import java.util.Optional;

/**
 * This class ensures that for each player which joins the server, an <code>IServerPlayer</code> wrapper is created.
 */
public class PlayerExistenceController implements IMLGPvPEventListener
{
    // Private fields.
    private final IServerServices _services;



    // Constructors.
    public PlayerExistenceController(IServerServices services)
    {
        _services = Objects.requireNonNull(services, "services is null");
    }


    // Private methods.
    private void OnPlayerJoinEvent(PlayerJoinEvent event)
    {
        IServerPlayer TargetPlayer = _services.GetPlayerCollection().GetPlayer(event.getPlayer())
                .orElseGet(() ->
                {
                    IServerPlayer Player = new MLGPvPPlayer(event.getPlayer(), _services.GetPlayerCollection());
                    Player.GetReferenceCountChangeEvent().Subscribe(this, this::OnPlayerReferenceCountChangeEvent);
                    Player.SubscribeToEvents(_services.GetEventDispatcher());
                    return Player;
                });
        _services.GetPlayerCollection().AddPlayer(TargetPlayer);
    }

    private void OnPlayerQuitEvent(PlayerQuitEvent event)
    {
        _services.GetPlayerCollection().GetPlayer(event.getPlayer()).ifPresent(this::TryRemovePlayer);
    }

    private void OnPlayerReferenceCountChangeEvent(PlayerReferenceCountChangeEvent event)
    {
        if (!event.GetPlayer().GetIsOnline())
        {
            TryRemovePlayer(event.GetPlayer());
        }
    }

    private void TryRemovePlayer(IServerPlayer player)
    {
        if (player.GetReferenceCount() == 0)
        {
            _services.GetPlayerCollection().RemovePlayer(player);
            player.GetReferenceCountChangeEvent().Unsubscribe(this);
            player.UnsubscribeFromEvents(_services.GetEventDispatcher());
        }
    }


    // Inherited methods.
    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        dispatcher.GetJoinEvent().Subscribe(this, this::OnPlayerJoinEvent);
        dispatcher.GetQuitEvent().Subscribe(this, this::OnPlayerQuitEvent, Integer.MIN_VALUE);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        dispatcher.GetJoinEvent().Unsubscribe(this);
        dispatcher.GetQuitEvent().Unsubscribe(this);
    }
}