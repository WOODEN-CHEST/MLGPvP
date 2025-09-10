package sus.keiger.mlgpvp.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import sus.keiger.mlgpvp.game.*;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.command.*;

import java.lang.reflect.Field;
import java.util.Objects;

public class MLGPvPCommand
{
    // Static fields.
    public static final String LABEL = "mlgpvp";


    // Private static fields.
    private static final String KEYWORD_START = "start";
    private static final String KEYWORD_CANCEL = "cancel";
    private static final String KEYWORD_SETTING = "setting";
    private static final String KEYWORD_RESET_SETTINGS = "reset_settings";

    private static final String KEY_VALUE = "value";



    // Private fields.
    private final IGameSessionExecutor _gameSessionExecutor;



    // Constructors.
    private MLGPvPCommand(IGameSessionExecutor gameSessionExecutor)
    {
        _gameSessionExecutor = Objects.requireNonNull(gameSessionExecutor, "gameSessionExecutor is null");
    }



    // Static methods.
    public static ServerCommand CreteCommand(IGameSessionExecutor gameSessionExecutor, IServerServices services)
    {
        MLGPvPCommand Data = new MLGPvPCommand(gameSessionExecutor);

        ServerCommand Command = new ServerCommand(LABEL, null, services.GetLogger());

        Command.AddSubNode(new KeywordNode(KEYWORD_START, Data::Start, null));
        Command.AddSubNode(new KeywordNode(KEYWORD_CANCEL, Data::Cancel, null));
        Command.AddSubNode(new KeywordNode(KEYWORD_RESET_SETTINGS, Data::ResetSettings, null));

        CommandNode SettingNode = new KeywordNode(KEYWORD_SETTING, Data::ShowSettings, null);
        Command.AddSubNode(SettingNode);

        for (Field ModifiableField : GameInstanceValues.GetModifiableFields())
        {
            SettingNode.AddSubNode(FieldToNode(Data, ModifiableField));
        }

        return Command;
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
                    NumberNodeType.Integer,
                    false,
                    false));
        }
        else if (FieldType == GameFieldType.DoubleField)
        {
            RootNode.AddSubNode(new NumberNode(commandData -> data.SetField(commandData, field),
                    null,
                    KEY_VALUE,
                    NumberNodeType.Double,
                    false,
                    false));
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
        if (_gameSessionExecutor.GetCurrentGameInstance().GetState() != GameInstanceState.Lobby)
        {
            data.SetFeedback("Can't start game while another game is already active!");
            data.SetStatus(CommandStatus.Unsuccessful);
            return;
        }

        _gameSessionExecutor.GetCurrentGameInstance().SetCenterLocation(data.GetLocation());
        ExplainedResult StartResult = _gameSessionExecutor.StartGame();
        if (!StartResult.IsSuccessful())
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

    private void ResetSettings(CommandData data)
    {
        _gameSessionExecutor.GetGlobalGameValues().Reset();
        data.SetFeedback("Reset all game settings to their default values.");
    }

    private void ShowSettings(CommandData data)
    {
        TextComponent.Builder Builder = Component.text();
        Builder.append(Component.text("Game settings:").color(NamedTextColor.GREEN));

        TextColor[] Colors = new TextColor[] { NamedTextColor.AQUA, NamedTextColor.LIGHT_PURPLE };
        int ColorIndex = 0;
        for (Field ModifiableField : GameInstanceValues.GetModifiableFields())
        {
            GameFieldProperties Properties = _gameSessionExecutor.GetGlobalGameValues().GetProperties(ModifiableField);
            Builder.append(Component.text("\n%s: %s".formatted(ModifiableField.getName(), Properties.Value()))
                    .color(Colors[ColorIndex]));

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

        Builder.append(Component.text("\nDescription: %s".formatted(Properties.Description()))
                .color(NamedTextColor.AQUA));

        Builder.append(Component.text("\nValue: \"%s\"".formatted(Properties.Value()))
                .color(NamedTextColor.LIGHT_PURPLE));

        if (Properties.MinValue() != null)
        {
            Builder.append(Component.text("\nMin Value: \"%s\"".formatted(Properties.MinValue()))
                    .color(NamedTextColor.GOLD));
        }

        if (Properties.MaxValue() != null)
        {
            Builder.append(Component.text("\nMax Value: \"%s\"".formatted(Properties.MaxValue()))
                    .color(NamedTextColor.GOLD));
        }

        data.SetFeedback(Builder.build());
    }

    private void SetField(CommandData data, Field field)
    {
        Object Value = data.GetParsedData(KEY_VALUE);
        _gameSessionExecutor.GetGlobalGameValues().SetField(field, Value);
        data.SetFeedback("Set field \"%s\" to \"%s\""
                .formatted(field.getName(), Value.toString()));
    }
}