package sus.keiger.mlgpvp.game.component;

import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.*;

import java.util.List;
import java.util.Objects;

public class GameDisplayBoard
{
    // Private fields.
    private final Scoreboard _scoreboard;
    private final Objective _objective;
    private final String OBJECTIVE_NAME = "game_objective";
    private final int MAX_LINE_COUNT = 10;



    // Constructors.
    public GameDisplayBoard(Scoreboard scoreboard)
    {
        _scoreboard = Objects.requireNonNull(scoreboard, "scoreboard is null");

        _objective = _scoreboard.registerNewObjective(OBJECTIVE_NAME, Criteria.DUMMY, Component.empty());
    }


    // Methods.
    public int GetMaxLineCount()
    {
        return MAX_LINE_COUNT;
    }

    public void SetTitle(Component title)
    {
        _objective.displayName(Objects.requireNonNull(title, "title is null"));
    }

    public void SetTextLines(List<Component> lines)
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

    public void SetIsVisible(boolean isVisible)
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
}