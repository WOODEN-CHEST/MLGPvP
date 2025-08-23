package sus.keiger.mlgpvp.game.component;

import sus.keiger.mlgpvp.game.GameInstanceState;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceEndEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceTickEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public class GameStateController extends GameComponent<IGameInstanceExtended>
{
    // Private fields.
    private GameInstanceState _state = GameInstanceState.Lobby;
    private final PCPluginEvent<GameInstanceStartEvent> _startEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GameInstanceEndEvent> _endEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GameInstanceCompleteEvent> _completeEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GameInstanceTickEvent> _lobbyTickEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GameInstanceTickEvent> _inGameTickEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GameInstanceTickEvent> _postGameTickEvent = new PCPluginEvent<>();


    // Constructors.
    public GameStateController(IGameInstanceExtended gameInstance)
    {
        super(gameInstance);
    }


    // Methods.
    public GameInstanceState GetState()
    {
        return _state;
    }

    public PCPluginEvent<GameInstanceStartEvent> GetStartEvent()
    {
        return _startEvent;
    }

    public PCPluginEvent<GameInstanceEndEvent> GetEndEvent()
    {
        return _endEvent;
    }

    public PCPluginEvent<GameInstanceCompleteEvent> GetCompleteEvent()
    {
        return _completeEvent;
    }

    public PCPluginEvent<GameInstanceTickEvent> GetLobbyTickEvent()
    {
        return _lobbyTickEvent;
    }

    public PCPluginEvent<GameInstanceTickEvent> GetInGameTickEvent()
    {
        return _inGameTickEvent;
    }

    public PCPluginEvent<GameInstanceTickEvent> GetPostGameTickEvent()
    {
        return _postGameTickEvent;
    }

    public void SwitchToInGameState()
    {
       if (_state != GameInstanceState.Lobby)
       {
           throw new IllegalStateException("Cannot switch to in-game state while in the %s state"
                   .formatted(_state.toString()));
       }

        _state = GameInstanceState.InGame;
        _startEvent.FireEvent(new GameInstanceStartEvent(GetGameInstance()));
    }

    public void SwitchToPostGameState()
    {
        if (_state != GameInstanceState.InGame)
        {
            throw new IllegalStateException("Cannot switch to post-game state while in the %s state"
                    .formatted(_state.toString()));
        }

        _state = GameInstanceState.PostGame;
        _endEvent.FireEvent(new GameInstanceEndEvent(GetGameInstance()));
    }

    public void SwitchToCompleteState()
    {
        _state = GameInstanceState.Complete;
        _completeEvent.FireEvent(new GameInstanceCompleteEvent(GetGameInstance()));
    }


    // Private methods.
    private void LobbyTick()
    {
        _lobbyTickEvent.FireEvent(new GameInstanceTickEvent(GetGameInstance()));
    }

    private void InGameTick()
    {
        _inGameTickEvent.FireEvent(new GameInstanceTickEvent(GetGameInstance()));
    }

    private void PostGameTick()
    {
        _postGameTickEvent.FireEvent(new GameInstanceTickEvent(GetGameInstance()));
    }


    // Inherited methods.
    @Override
    public void Tick()
    {
        super.Tick();

        switch (_state)
        {
            case Lobby -> LobbyTick();
            case InGame -> InGameTick();
            case PostGame -> PostGameTick();
            case Complete -> { }
            default -> throw new IllegalStateException("Invalid game state: %s".formatted(_state.toString()));
        }
    }
}