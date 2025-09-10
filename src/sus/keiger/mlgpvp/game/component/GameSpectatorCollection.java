package sus.keiger.mlgpvp.game.component;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.player.IServerPlayer;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GameSpectatorCollection extends GameComponent<MLGPvPGameInstance>
{
    // Private fields.
    private final Set<IServerPlayer> _spectators = new HashSet<>();



    // Constructors.
    public GameSpectatorCollection(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
    }


    // Private methods.
    private void SetPlayerIntoSpectatorMode(IServerPlayer player)
    {
        Player MCPlayer = player.GetUnderlyingPlayer();
        MCPlayer.setGameMode(GameMode.SPECTATOR);
        MCPlayer.clearActivePotionEffects();
    }

    private void OnGameCompleteEven(GameInstanceCompleteEvent event)
    {
        GetSpectators().forEach(this::RemoveSpectator);
    }


    // Methods.
    public void AddSpectator(IServerPlayer player)
    {
        Objects.requireNonNull(player, "player is null");
        if (!_spectators.contains(player))
        {
            _spectators.add(player);
            SetPlayerIntoSpectatorMode(player);
        }
    }

    public void RemoveSpectator(IServerPlayer player)
    {
        Objects.requireNonNull(player, "player is null");
        _spectators.remove(player);
    }

    public int GetSpectatorCount()
    {
        return _spectators.size();
    }

    public List<IServerPlayer> GetSpectators()
    {
        return List.copyOf(_spectators);
    }


    // Inherited methods.

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        GetGameInstance().GetCompleteEvent().Subscribe(this, this::OnGameCompleteEven);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        GetGameInstance().GetCompleteEvent().Unsubscribe(this);
    }
}
