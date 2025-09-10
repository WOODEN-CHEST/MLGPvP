package sus.keiger.mlgpvp.game.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import sus.keiger.mlgpvp.MLGPvPPlugin;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.MLGPvPGameInstance;
import sus.keiger.mlgpvp.game.PlayerGameStats;
import sus.keiger.mlgpvp.game.event.GameInstanceCompleteEvent;
import sus.keiger.mlgpvp.player.IServerPlayer;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class GameEndContentDisplayer extends GameComponent<MLGPvPGameInstance>
{
    // Private static fields.
    private static final int DIRECTION_HIGHER_BETTER = 1;
    private static final int DIRECTION_LOWER_BETTER = -1;


    // Private fields.
    private final PlayerScoreCalculator _scoreCalculator = new PlayerScoreCalculator();
    private final NumberFormat _format = MLGPvPPlugin.GetFormat("0.0");


    // Constructors.
    public GameEndContentDisplayer(MLGPvPGameInstance gameInstance)
    {
        super(gameInstance);
    }


    // Methods.
    public void DisplayEndContent(IServerPlayer winner)
    {
        ShowTitles(winner);
        ShowChatMessages(winner);
    }


    // Private methods.
    private List<PlayerEndData> GetPlayersSortedByScoreAscending()
    {
        List<PlayerEndData> Players = new ArrayList<>(GetGameInstance()
                .GetJoinedPlayers()
                .stream()
                .map(this::PlayerToEndData)
                .filter(Objects::nonNull)
                .toList());

        Players.sort(Comparator.comparingDouble(data -> data.Score));

        return Players;
    }

    private PlayerEndData PlayerToEndData(IServerPlayer player)
    {
        return GetGameInstance().GetPlayerStats(player)
                .map(stats -> new PlayerEndData(player, stats, _scoreCalculator.CalculateScore(stats)))
                .orElse(null);
    }

    private int GetPlayerRankingInStat(PlayerEndData rankedPlayerData,
                                       List<PlayerEndData> allPlayerData,
                                       Function<PlayerEndData, Double> statValueSupplier,
                                       int direction)
    {
        int Ranking = 1;

        double SelfScore = statValueSupplier.apply(rankedPlayerData) * direction;
        for (PlayerEndData CompetitorData : allPlayerData)
        {
            if (CompetitorData == rankedPlayerData)
            {
                continue;
            }

            double CompetitorScore = statValueSupplier.apply(CompetitorData) * direction;
            if (SelfScore < CompetitorScore)
            {
                Ranking++;
            }
        }

        return Ranking;
    }

    private void ShowChatMessages(IServerPlayer winner)
    {
        TextComponent.Builder Builder = Component.text();

        Builder.append(Component.text("Player stats:").color(NamedTextColor.GOLD));
        Builder.append(Component.newline());

        List<PlayerEndData> AllPlayerData = GetPlayersSortedByScoreAscending();
        for (PlayerEndData PlayerData : AllPlayerData)
        {
            Builder.append(Component.text("%s:".formatted(PlayerData.Player.GetName())).color(NamedTextColor.WHITE));
            AppendPlayerStats(AllPlayerData, PlayerData, Builder);
            Builder.append(Component.newline());
        }

        Builder.append(Component.newline());
        Builder.append(Component.newline());
        Builder.append(Component.text("The winner is %s!".formatted(winner.GetName())).color(NamedTextColor.GREEN));

        GetGameInstance().SendMessage(Builder.build());
    }

    private void AppendPlayerStats(List<PlayerEndData> allPlayerData,
                                   PlayerEndData specificPlayerData,
                                   TextComponent.Builder builder)
    {
        TextColorIndex Colors = new TextColorIndex(new TextColor[] { NamedTextColor.AQUA, NamedTextColor.LIGHT_PURPLE });

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetDamageTaken(),
                DIRECTION_LOWER_BETTER, "Taken Damage", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetDamageDealt(),
                DIRECTION_HIGHER_BETTER, "Dealt Damage", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetDistanceClimbed(),
                DIRECTION_HIGHER_BETTER, "Distance Climbed", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetDistanceFallen(),
                DIRECTION_HIGHER_BETTER, "Distance Fallen", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetHighestClimb(),
                DIRECTION_HIGHER_BETTER, "Highest Single Climb", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetArrowsFired(),
                DIRECTION_HIGHER_BETTER, "Arrows Fired", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetDirectHits(),
                DIRECTION_HIGHER_BETTER, "Direct Arrow Hits", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetWaterBucketsLanded(),
                DIRECTION_HIGHER_BETTER, "MLGs Landed", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Stats.GetWaterBucketsFailed(),
                DIRECTION_LOWER_BETTER, "MLGs Failed", Colors.GetColor());

        AppendSingleStat(allPlayerData, specificPlayerData, builder, data -> data.Score,
                DIRECTION_HIGHER_BETTER, "Final Score", NamedTextColor.BLUE);
    }

    private void AppendSingleStat(List<PlayerEndData> allPlayerData,
                                  PlayerEndData specificPlayerData,
                                  TextComponent.Builder builder,
                                  Function<PlayerEndData, Object> valueSupplier,
                                  int statDirection,
                                  String statName,
                                  TextColor color)
    {
        Object Value = valueSupplier.apply(specificPlayerData);
        String StringValue;
        double DoubleValue;

        if (Value instanceof Number NumberValue)
        {
            DoubleValue = NumberValue.doubleValue();
            StringValue = Value instanceof Double ? _format.format(NumberValue.doubleValue()) : NumberValue.toString();
        }
        else
        {
            throw new IllegalArgumentException("Player stat value must be a number.");
        }

        int Rank = GetPlayerRankingInStat(specificPlayerData,
                allPlayerData,
                data -> DoubleValue,
                statDirection);

        builder.append(Component.newline());
        builder.append(Component.text("    %s: %s (#%d)".formatted(statName, StringValue, Rank)).color(color));
    }

    private void ShowTitles(IServerPlayer winner)
    {
        for (IServerPlayer Player : GetGameInstance().GetOnlinePlayers())
        {
            if (Player == winner)
            {
                Player.ShowTitle(Title.title(Component.text("Victory!").color(NamedTextColor.GREEN), Component.empty(),
                        Title.Times.times(Duration.ZERO, Duration.ofSeconds(5), Duration.ofSeconds(1))));
            }
            else
            {
                Player.ShowTitle(Title.title(Component.text("Defeat!").color(NamedTextColor.RED), Component.empty(),
                        Title.Times.times(Duration.ZERO, Duration.ofSeconds(5), Duration.ofSeconds(1))));
            }
        }
    }


    // Types.
    private record PlayerEndData(IServerPlayer Player,
                                 PlayerGameStats Stats,
                                 double Score) {}

    private static class TextColorIndex
    {
        // Fields.
        public int Index = 0;
        public final TextColor[] Colors;


        // Constructors.
        private TextColorIndex(TextColor[] colors)
        {
            Colors = colors;
        }


        // Methods.
        public TextColor GetColor()
        {
            int CurrentIndex = Index;
            Index = (Index + 1) % Colors.length;
            return Colors[CurrentIndex];
        }
    }
}