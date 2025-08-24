package sus.keiger.mlgpvp.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import sus.keiger.mlgpvp.game.GameFieldProperties;
import sus.keiger.mlgpvp.game.GameFieldType;
import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameSessionExecutor;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.command.*;

import java.lang.reflect.Field;
import java.util.Objects;

public class MLGPvPCommand extends ServerCommand
{
    // Static fields.
    public static final String LABEL = "mlgpvp";


    // Private static fields.
    private static final String KEYWORD_START = "start";
    private static final String KEYWORD_CANCEL = "cancel";
    private static final String KEYWORD_SETTING = "setting";

    private static final String KEY_VALUE = "value";



    // Private fields.
    private final IGameSessionExecutor _gameSessionExecutor;



    // Constructors.
    public MLGPvPCommand(IGameSessionExecutor gameSessionExecutor)
    {
        super(LABEL, null);
        _gameSessionExecutor = Objects.requireNonNull(gameSessionExecutor, "gameSessionExecutor is null");
    }



    // Static methods.
    public static ServerCommand CreteCommand(IGameSessionExecutor gameSessionExecutor)
    {
        MLGPvPCommand Data = new MLGPvPCommand(gameSessionExecutor);

        Data.AddSubNode(new KeywordNode(KEYWORD_START, Data::Start, null));
        Data.AddSubNode(new KeywordNode(KEYWORD_CANCEL, Data::Cancel, null));

        CommandNode SettingNode = new KeywordNode(KEYWORD_SETTING, Data::ShowSettings, null);
        Data.AddSubNode(SettingNode);

        for (Field ModifiableField : GameInstanceValues.GetModifiableFields())
        {
            SettingNode.AddSubNode(FieldToNode(Data, ModifiableField));
        }

        return Data;
    }


    // Private static methods.
    private static CommandNode FieldToNode(MLGPvPCommand data, Field field)
    {
        CommandNode RootNode = new KeywordNode(field.getName(),
                commandData -> data.ShowSingleSetting(commandData, field), null);

        GameFieldType FieldType = GameInstanceValues.GetFieldType(field);
        if (FieldType == GameFieldType.BoolField)
        {
            RootNode.AddSubNode(new BooleanNode(commandData -> data.SetField(commandData, field), KEY_VALUE));
        }
        else if (FieldType == GameFieldType.IntField)
        {
            RootNode.AddSubNode(new NumberNode(commandData -> data.SetField(commandData, field),
                    null,
                    KEY_VALUE,
                    NumberNodeType.Integer));
        }
        else if (FieldType == GameFieldType.DoubleField)
        {
            RootNode.AddSubNode(new NumberNode(commandData -> data.SetField(commandData, field),
                    null,
                    KEY_VALUE,
                    NumberNodeType.Double));
        }
        else if (FieldType == GameFieldType.StringField)
        {
            RootNode.AddSubNode(new StringNode(commandData -> data.SetField(commandData, field),
                    true,
                    null,
                    KEY_VALUE));
        }
        else
        {
            throw new IllegalArgumentException("Unknown field type: %s".formatted(FieldType.toString()));
        }

        return RootNode;
    }



    // Private methods.
    private void Start(CommandData data)
    {
        ExplainedResult StartResult = _gameSessionExecutor.StartGame();
        if (StartResult.IsSuccessful())
        {
            data.SetFeedback("Started game");
        }
        else
        {
            data.SetStatus(CommandStatus.Unsuccessful);
            data.SetFeedback("Couldn't start game, reason: %s".formatted(StartResult.GetMessage()));
        }
    }

    private void Cancel(CommandData data)
    {
        ExplainedResult CancelResult = _gameSessionExecutor.CancelGame();
        if (CancelResult.IsSuccessful())
        {
            data.SetFeedback("Cancelled game");
        }
        else
        {
            data.SetStatus(CommandStatus.Unsuccessful);
            data.SetFeedback("Couldn't cancel game, reason: %s".formatted(CancelResult.GetMessage()));
        }
    }

    private void ShowSettings(CommandData data)
    {
        TextComponent.Builder Builder = Component.text();
        Builder.append(Component.text("Game settings:").color(NamedTextColor.GREEN));

        TextColor[] Colors = new TextColor[] {NamedTextColor.AQUA, NamedTextColor.LIGHT_PURPLE};
        int ColorIndex = 0;
        for (Field ModifiableField : GameInstanceValues.GetModifiableFields())
        {
            GameFieldProperties Properties = _gameSessionExecutor.GetGlobalGameValues().GetProperties(ModifiableField);
            Builder.append(Component.text("%s: %s".formatted(ModifiableField.getName(), Properties.Value())))
                    .color(Colors[ColorIndex]);

            ColorIndex = (ColorIndex + 1) % Colors.length;
        }

        data.SetFeedback(Builder.build());
    }

    private void ShowSingleSetting(CommandData data, Field field)
    {
        GameFieldProperties Properties = _gameSessionExecutor.GetGlobalGameValues().GetProperties(field);
        TextComponent.Builder Builder = Component.text();

        Builder.append(Component.text("Setting \"%s\"".formatted(field.getName()))
                .color(NamedTextColor.GREEN));

        Builder.append(Component.text("Description: %s".formatted(Properties.Description()))
                .color(NamedTextColor.AQUA));

        Builder.append(Component.text("Value: \"%s\"".formatted(Properties.Value()))
                .color(NamedTextColor.LIGHT_PURPLE));

        if (Properties.MinValue() != null)
        {
            Builder.append(Component.text("Min Value: \"%s\"".formatted(Properties.MinValue()))
                    .color(NamedTextColor.GOLD));
        }

        if (Properties.MaxValue() != null)
        {
            Builder.append(Component.text("Max Value: \"%s\"".formatted(Properties.MaxValue()))
                    .color(NamedTextColor.GOLD));
        }

        data.SetFeedback(Builder.build());
    }

    private void SetField(CommandData data, Field field)
    {
        _gameSessionExecutor.GetGlobalGameValues().SetField(field, data.GetParsedData(KEY_VALUE));
    }
}