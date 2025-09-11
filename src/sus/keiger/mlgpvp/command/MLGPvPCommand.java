package sus.keiger.mlgpvp.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import sus.keiger.mlgpvp.config.ConfigException;
import sus.keiger.mlgpvp.config.IConfigManager;
import sus.keiger.mlgpvp.config.JSONConfigManager;
import sus.keiger.mlgpvp.game.*;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.PCString;
import sus.keiger.plugincommon.command.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class MLGPvPCommand
{
    // Static fields.
    public static final String LABEL = "mlgpvp";


    // Private static fields.
    private static final String KEYWORD_START = "start";
    private static final String KEYWORD_CANCEL = "cancel";
    private static final String KEYWORD_SETTING = "setting";
    private static final String KEYWORD_CONFIG = "config";
    private static final String KEYWORD_LOAD = "load";
    private static final String KEYWORD_DELETE = "delete";
    private static final String KEYWORD_SAVE = "save";
    private static final String KEYWORD_REFRESH = "refresh";
    private static final String KEYWORD_LIST = "list";
    private static final String KEYWORD_RESET_SETTINGS = "reset_settings";

    private static final String KEY_VALUE = "value";
    private static final String KEY_CONFIG_NAME = "config_name";



    // Private fields.
    private final IGameSessionExecutor _gameSessionExecutor;
    private final IServerServices _services;
    private final IConfigManager _configManager;
    private List<String> _cachedAvailableConfigs = Collections.emptyList();



    // Constructors.
    private MLGPvPCommand(IGameSessionExecutor gameSessionExecutor, IServerServices services)
    {
        _gameSessionExecutor = Objects.requireNonNull(gameSessionExecutor, "gameSessionExecutor is null");
        _configManager = new JSONConfigManager(services);
        _services = services;
    }



    // Static methods.
    public static ServerCommand CreteCommand(IGameSessionExecutor gameSessionExecutor, IServerServices services)
    {
        GameInstanceValues DefaultValues = new GameInstanceValues();
        MLGPvPCommand Data = new MLGPvPCommand(gameSessionExecutor, services);

        ServerCommand Command = new ServerCommand(LABEL, null, services.GetLogger());

        Command.AddSubNode(new KeywordNode(KEYWORD_START, Data::Start, null));
        Command.AddSubNode(new KeywordNode(KEYWORD_CANCEL, Data::Cancel, null));
        Command.AddSubNode(new KeywordNode(KEYWORD_RESET_SETTINGS, Data::ResetSettings, null));

        CommandNode SettingNode = new KeywordNode(KEYWORD_SETTING, Data::ShowSettings, null);
        Command.AddSubNode(SettingNode);

        for (Field ModifiableField : GameInstanceValues.GetModifiableFields())
        {
            SettingNode.AddSubNode(FieldToNode(Data,
                    ModifiableField,
                    DefaultValues.GetProperties(ModifiableField).Value()));
        }

        Command.AddSubNode(CreateConfigNode(Data));

        return Command;
    }


    // Private static methods.
    private static CommandNode FieldToNode(MLGPvPCommand data, Field field, String defaultValue)
    {
        CommandNode RootNode = new KeywordNode(field.getName(),
                commandData -> data.ShowSingleSetting(commandData, field), null);
        List<String> DefaultValue = List.of(defaultValue);

        GameFieldType FieldType = GameInstanceValues.GetFieldType(field);
        if (FieldType == GameFieldType.BoolField)
        {
            RootNode.AddSubNode(new BooleanNode(commandData -> data.SetField(commandData, field), KEY_VALUE));
        }
        else if (FieldType == GameFieldType.IntField)
        {
            RootNode.AddSubNode(new NumberNode(commandData -> data.SetField(commandData, field),
                    _ -> DefaultValue,
                    KEY_VALUE,
                    NumberNodeType.Integer,
                    false,
                    false));
        }
        else if (FieldType == GameFieldType.DoubleField)
        {
            RootNode.AddSubNode(new NumberNode(commandData -> data.SetField(commandData, field),
                    _ -> DefaultValue,
                    KEY_VALUE,
                    NumberNodeType.Double,
                    false,
                    false));
        }
        else if (FieldType == GameFieldType.StringField)
        {
            RootNode.AddSubNode(new StringNode(commandData -> data.SetField(commandData, field),
                    true,
                    _ -> DefaultValue,
                    KEY_VALUE));
        }
        else
        {
            throw new IllegalArgumentException("Unknown field type: %s".formatted(FieldType.toString()));
        }

        return RootNode;
    }

    private static CommandNode CreateConfigNode(MLGPvPCommand data)
    {
        CommandNode RootNode = new KeywordNode(KEYWORD_CONFIG, null, null);

        CommandNode LoadNode = new KeywordNode(KEYWORD_LOAD, null, null);
        RootNode.AddSubNode(LoadNode);
        CommandNode LoadNameNode = new StringNode(data::LoadConfig, true,
                commandData -> data._cachedAvailableConfigs, KEY_CONFIG_NAME);
        LoadNode.AddSubNode(LoadNameNode);

        CommandNode SaveNode = new KeywordNode(KEYWORD_SAVE, null, null);
        RootNode.AddSubNode(SaveNode);
        CommandNode SaveNameNode = new StringNode(data::SaveConfig, true,
                commandData -> List.of("example_config_name"), KEY_CONFIG_NAME);
        SaveNode.AddSubNode(SaveNameNode);

        CommandNode DeleteNode = new KeywordNode(KEYWORD_DELETE, null, null);
        RootNode.AddSubNode(DeleteNode);
        CommandNode DeleteNameNode = new StringNode(data::DeleteConfig, true,
                commandData -> data._cachedAvailableConfigs, KEY_CONFIG_NAME);
        DeleteNode.AddSubNode(DeleteNameNode);

        CommandNode ListConfigsNode = new KeywordNode(KEYWORD_LIST, data::ListConfigs, null);
        RootNode.AddSubNode(ListConfigsNode);

        CommandNode RefreshConfigsNode = new KeywordNode(KEYWORD_REFRESH, data::RefreshConfigs, null);
        RootNode.AddSubNode(RefreshConfigsNode);

        return RootNode;
    }



    // Private methods.
    private void UpdateCachedConfigs()
    {
        _cachedAvailableConfigs = _configManager.GetConfigs();
    }

    private void ContinueWithConfigName(CommandData data, Consumer<String> function)
    {
        String Name = data.GetParsedData(KEY_CONFIG_NAME);
        ExplainedResult Result = _configManager.VerifyConfigName(Name);
        if (Result.IsSuccessful())
        {
            function.accept(Name);
        }
        else
        {
            data.SetStatus(CommandStatus.Unsuccessful);
            data.SetFeedback("Invalid config name! %s".formatted(Result.GetMessage()));
        }
    }

    private void OnConfigAccessException(CommandData data, String actionName, Exception e)
    {
        data.SetStatus(CommandStatus.Unsuccessful);
        data.SetFeedback("Failed to %s due to an internal error, please check logs.".formatted(actionName));
        _services.GetLogger().severe(e.getMessage());
    }


    private void LoadConfig(CommandData data)
    {
        ContinueWithConfigName(data, name ->
        {
            try
            {
                if (!_configManager.DoesConfigExist(name))
                {
                    data.SetStatus(CommandStatus.Unsuccessful);
                    data.SetFeedback("No config with the name \"%s\" exists.".formatted(name));
                    return;
                }

                _gameSessionExecutor.GetGlobalGameValues().CopyValuesFrom(
                        _configManager.LoadConfig(name));
                UpdateCachedConfigs();
                data.SetFeedback("Config \"%s\" successfully loaded!".formatted(name));
            }
            catch (ConfigException e)
            {
                OnConfigAccessException(data, "load config", e);
            }
        });
    }

    private void SaveConfig(CommandData data)
    {
        ContinueWithConfigName(data, name ->
        {
            try
            {
                ExplainedResult Result = _configManager.SaveConfig(name, _gameSessionExecutor.GetGlobalGameValues());
                UpdateCachedConfigs();

                if (Result.IsSuccessful())
                {
                    data.SetFeedback("Config \"%s\" successfully saved!".formatted(name));
                }
                else
                {
                    data.SetStatus(CommandStatus.Unsuccessful);
                    data.SetFeedback("failed to save config: %s".formatted(Result.GetMessage()));
                }
            }
            catch (ConfigException e)
            {
                OnConfigAccessException(data, "save config", e);
            }
        });
    }

    private String GetConfigsListString(List<String> configs)
    {
        if (configs.isEmpty())
        {
            return "There are no configs available";
        }

        StringBuilder Builder = new StringBuilder();
        Builder.append("%d %s available:".formatted(configs.size(), PCString.Pluralize("config", configs.size())));
        for (String ConfigName : configs)
        {
            Builder.append("\n\"%s\"".formatted(ConfigName));
        }

        return Builder.toString();
    }

    private void ListConfigs(CommandData data)
    {
        try
        {
            List<String> Configs = _configManager.GetConfigs();
            UpdateCachedConfigs();
            data.SetFeedback(GetConfigsListString(Configs));
        }
        catch (ConfigException e)
        {
            OnConfigAccessException(data, "refresh configs", e);
        }
    }

    private void DeleteConfig(CommandData data)
    {
        ContinueWithConfigName(data, name ->
        {
            try
            {
                ExplainedResult Result = _configManager.DeleteConfig(name);
                if (Result.IsSuccessful())
                {
                    data.SetFeedback("Config \"%s\" successfully deleted!".formatted(name));
                }
                else
                {
                    data.SetStatus(CommandStatus.Unsuccessful);
                    data.SetFeedback("Failed to delete config: %s".formatted(Result.GetMessage()));
                }
                UpdateCachedConfigs();
            }
            catch (ConfigException e)
            {
                OnConfigAccessException(data, "delete config", e);
            }
        });
    }

    private void RefreshConfigs(CommandData data)
    {
        try
        {
            UpdateCachedConfigs();
            data.SetFeedback("Refreshed configs!");
        }
        catch (ConfigException e)
        {
            OnConfigAccessException(data, "refresh configs", e);
        }
    }

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