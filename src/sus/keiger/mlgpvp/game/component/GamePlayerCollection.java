package sus.keiger.mlgpvp.game.component;

import sus.keiger.mlgpvp.game.GameInstanceState;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.PlayerGameStats;
import sus.keiger.mlgpvp.player.IServerPlayer;

import java.awt.color.ICC_ProfileGray;
import java.util.*;

public class GamePlayerCollection extends GameComponent<IGameInstanceExtended>
{
    // Private fields.
    private final Map<IServerPlayer, GamePlayer> _players = new HashMap<>();
    private List<IServerPlayer> _activePlayers = Collections.emptyList();


    // Constructors.
    public GamePlayerCollection(IGameInstanceExtended gameInstance)
    {
        super(gameInstance);
    }



    // Methods.
    public void AddPlayer(IServerPlayer player)
    {
        if (_players.containsKey(player))
        {
            return;
        }

        _players.put(player, new GamePlayer(player));

        if (GetGameInstance().GetState() != GameInstanceState.Lobby)
        {
            GetGameInstance().TryReAddPlayer(player);
        }

        UpdateActivePlayerList();
    }

    public void RemovePlayer(IServerPlayer player)
    {
        _players.remove(player);
        UpdateActivePlayerList();
    }

    public List<IServerPlayer> GetJoinedPlayers()
    {
        return List.copyOf(_players.keySet());
    }

    public int GetJoinedPlayerCount()
    {
        return _players.size();
    }

    boolean ContainsJoinedPlayer(IServerPlayer player)
    {
        return _players.containsKey(Objects.requireNonNull(player, "player is null"));
    }

    public List<IServerPlayer> GetActivePlayers()
    {
        return _activePlayers;
    }

    public int GetActivePlayerCount()
    {
        return _activePlayers.size();
    }

    public boolean ContainsActivePlayer(IServerPlayer player)
    {
        GamePlayer TargetPlayer = _players.get(Objects.requireNonNull(player, "player is null"));
        return (TargetPlayer != null) && TargetPlayer.Player.GetIsOnline();
    }

    public Optional<PlayerGameStats> GetPlayerStats(IServerPlayer player)
    {
        return Optional.ofNullable(_players.get(Objects.requireNonNull(player, "player is null")))
                .map(gamePlayer -> gamePlayer.Stats);
    }


    // Private methods.
    private void UpdateActivePlayerList()
    {
         _activePlayers = _players.keySet().stream().filter(IServerPlayer::GetIsOnline).toList();
    }


    // Classes
    private static class GamePlayer
    {
        // Fields.
        public final PlayerGameStats Stats = new PlayerGameStats();
        public final IServerPlayer Player;


        // Constructors.
        private GamePlayer(IServerPlayer player)
        {
            Player = player;
        }
    }
}