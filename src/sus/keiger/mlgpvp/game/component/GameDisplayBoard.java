package sus.keiger.mlgpvp.game.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceEntityAddEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceEntityRemoveEvent;
import sus.keiger.mlgpvp.game.event.GameInstanceStartEvent;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.PCMath;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.TickClock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameDisplayBoard extends GameComponent<MLGPvPGameInstance>
{
    // Private fields.
    private final Scoreboard _scoreboard;
    private final Objective _objective;
    private final String OBJECTIVE_NAME = "game_objective";
    private final int MAX_LINE_COUNT = 10;
    private final TickClock _updateClock = new TickClock();



    // Constructors.
    public GameDisplayBoard(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
        _scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        _objective = _scoreboard.registerNewObjective(OBJECTIVE_NAME, Criteria.DUMMY, Component.empty());
        SetTitle(Component.text("Game Info").color(NamedTextColor.GOLD));
        ResetTickClock();
        _updateClock.SetIsRunning(true);
        _updateClock.SetHandler(this::OnTickClickEvent);
        SetIsVisible(true);
    }


    // Private methods..
    private int GetMaxLineCount()
    {
        return MAX_LINE_COUNT;
    }

    private void SetTitle(Component title)
    {
        _objective.displayName(Objects.requireNonNull(title, "title is null"));
    }

    private void SetTextLines(List<Component> lines)
    {
        for (int i = 0; i < MAX_LINE_COUNT; i++)
        {
            _objective.getScore(Integer.toString(i)).resetScore();
        }

        for (int i = 0 ; (i < lines.size()) && (i < MAX_LINE_COUNT); i++)
        {
            Score TargetScore = _objective.getScore(Integer.toString(i));
            TargetScore.setScore(lines.size() - i - 1);
            TargetScore.customName(lines.get(i));
        }
    }

    private void SetIsVisible(boolean isVisible)
    {
        if (isVisible)
        {
            _objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        else
        {
            _objective.setDisplaySlot(null);
        }
    }

    private String FormatTime(int ticks)
    {
        int SecondsTotal = ticks / PCMath.TICKS_IN_SECOND;
        int MinutesTotal = SecondsTotal / 60;
        int HoursTotal = MinutesTotal / 60;

        int SecondsInCounter = SecondsTotal % 60;
        int MinutesInCounter = MinutesTotal % 60;
        int HoursInCounter = HoursTotal;
        final char Separator = ':';

        StringBuilder Builder = new StringBuilder();

        AppendCounter(Builder, HoursInCounter);
        Builder.append(Separator);
        AppendCounter(Builder, MinutesInCounter);
        Builder.append(Separator);
        AppendCounter(Builder, SecondsInCounter);

        return Builder.toString();
    }

    private void AppendCounter(StringBuilder builder, int value)
    {
        if (value <= 9)
        {
            builder.append('0');
        }
        builder.append(value);
    }

    private void UpdateDisplayBoard()
    {
        List<Component> Lines = new ArrayList<>();

        int TicksUntilDeathmatch = GetGameInstance().GetTicksRemainingUntilDeathmatch();
        int TicksUntilBorderShrink = GetGameInstance().GetTicksRemainingUntilBorderShrink();
        TextColor DeathmatchColor = NamedTextColor.GREEN;
        TextColor BorderColor = NamedTextColor.LIGHT_PURPLE;

        if (TicksUntilDeathmatch > 0)
        {
            Lines.add(Component.text("Until Deathmatch:").color(DeathmatchColor));
            Lines.add(Component.text(FormatTime(TicksUntilDeathmatch)).color(DeathmatchColor));
        }
        if (GetGameInstance().GetTicksRemainingUntilBorderShrink() > 0)
        {
            if (!Lines.isEmpty())
            {
                Lines.add(Component.empty());
            }
            Lines.add(Component.text("Until Border Shrink:").color(BorderColor));
            Lines.add(Component.text(FormatTime(TicksUntilBorderShrink)).color(BorderColor));
        }
        if (Lines.isEmpty())
        {
            Lines.add(Component.text("DEATHMATCH").color(NamedTextColor.DARK_RED));
        }

        SetTextLines(Lines);
    }

    private void OnEntityAddEvent(GameInstanceEntityAddEvent event)
    {
        if (event.GetEntity() instanceof GamePlayerEntity PlayerEntity)
        {
            PlayerEntity.SetScoreboard(_scoreboard);
        }
    }

    private void OnEntityRemoveEvent(GameInstanceEntityRemoveEvent event)
    {
        if (event.GetEntity() instanceof GamePlayerEntity PlayerEntity)
        {
            PlayerEntity.SetScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

    private void ResetTickClock()
    {
        _updateClock.SetTicksLeft(PCMath.TICKS_IN_SECOND);
    }

    private void OnTickClickEvent(TickClock clock)
    {
        UpdateDisplayBoard();
        ResetTickClock();
    }

    private void OnGameStartEvent(GameInstanceStartEvent event)
    {
        UpdateDisplayBoard();
    }

    private void OnCompleteEvent(GameInstanceCompleteEvent event)
    {
        _scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        for (IServerPlayer Player : GetGameInstance().GetJoinedPlayers())
        {
            Player.GetUnderlyingPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }



    // Inherited methods.

    @Override
    public void Tick()
    {
        super.Tick();
        _updateClock.Tick();
    }

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        GetGameInstance().GetEntityAddEvent().Subscribe(this, this::OnEntityAddEvent);
        GetGameInstance().GetEntityRemoveEvent().Subscribe(this, this::OnEntityRemoveEvent);
        GetGameInstance().GetStartEvent().Subscribe(this, this::OnGameStartEvent, -5);
        GetGameInstance().GetCompleteEvent().Subscribe(this, this::OnCompleteEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        GetGameInstance().GetEntityAddEvent().Unsubscribe(this);
        GetGameInstance().GetStartEvent().Unsubscribe(this);
        GetGameInstance().GetEntityRemoveEvent().Unsubscribe(this);
        GetGameInstance().GetCompleteEvent().Unsubscribe(this);
    }
}