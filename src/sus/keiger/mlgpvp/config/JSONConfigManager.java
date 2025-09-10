package sus.keiger.mlgpvp.config;

import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.ExplainedResult;
import sus.keiger.plugincommon.PCString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JSONConfigManager implements IConfigManager
{
    // Private static fields.
    private static final String EXTENSION = ".json";
    private static final String DIR_NAME_CONFIGS = "configs";
    private static final char EXTENSION_INDICATOR = '.';


    // Private fields.
    private final IServerServices _services;
    private final Set<Character> _allowedChars = new HashSet<>();
    private final JSONConfigSerializer _serializer = new JSONConfigSerializer();



    // Constructors.
    public JSONConfigManager(IServerServices services)
    {
        _services = Objects.requireNonNull(services, "services is null");
        PopulateAllowedCharSet();
    }


    // Private methods.
    private void PopulateAllowedCharSet()
    {
        _allowedChars.clear();
        for (char i = 'a'; i <= 'z'; i++) _allowedChars.add(i);
        for (char i = 'A'; i <= 'Z'; i++) _allowedChars.add(i);
        for (char i = '0'; i <= '9'; i++) _allowedChars.add(i);
        _allowedChars.add('_');
    }

    private Path GetConfigDirectoryPath()
    {
        return Path.of(_services.GetPlugin().getDataPath().toString(), DIR_NAME_CONFIGS);
    }

    private Path GetConfigFilePath(String configName)
    {
        return Path.of(GetConfigDirectoryPath().toString(), configName + EXTENSION);
    }

    private boolean EnsureParentDirectory(Path configPath)
    {
        File ParentDir = configPath.getParent().toFile();
        return ParentDir.exists() ||  configPath.getParent().toFile().mkdirs();
    }

    private void VerifyNameOrThrow(String name)
    {
        ExplainedResult NameVerifyResult = VerifyConfigName(name);
        if (!NameVerifyResult.IsSuccessful())
        {
            throw new ConfigException(NameVerifyResult.GetMessage());
        }
    }


    // Inherited methods.
    @Override
    public GameInstanceValues LoadConfig(String name)
    {
        Objects.requireNonNull(name, "name is null");

        VerifyNameOrThrow(name);

        try
        {
            Path TargetPath = GetConfigFilePath(name);
            try (FileInputStream InStream = new FileInputStream(TargetPath.toFile()))
            {
                return _serializer.Deserialize(new String(InStream.readAllBytes(), StandardCharsets.UTF_8));
            }
        }
        catch (SecurityException | IOException e)
        {
            throw new ConfigException("Failed to load config \"%s\": %s"
                    .formatted(name, PCString.ExceptionToString(e)));
        }
    }

    @Override
    public void SaveConfig(String name, GameInstanceValues config)
    {
        Objects.requireNonNull(name, "name is null");
        Objects.requireNonNull(config, "config is null");

        VerifyNameOrThrow(name);

        try
        {
            Path TargetPath = GetConfigFilePath(name);
            if (!EnsureParentDirectory(TargetPath))
            {
                throw new ConfigException("Failed to ensure parent directory for config \"%s\".".formatted(name));
            }

            File TargetFile = TargetPath.toFile();
            try (FileOutputStream OutStream = new FileOutputStream(TargetFile))
            {
                OutStream.write(_serializer.Serialize(config).getBytes(StandardCharsets.UTF_8));
            }
        }
        catch (SecurityException | IOException e)
        {
            throw new ConfigException("Failed to save config \"%s\": %s"
                    .formatted(name, PCString.ExceptionToString(e)));
        }
    }

    @Override
    public boolean DoesConfigExist(String name)
    {
        Objects.requireNonNull(name, "name is null");

        VerifyNameOrThrow(name);

        try
        {
            File ConfigFile = GetConfigFilePath(name).toFile();
            return ConfigFile.exists() && ConfigFile.isFile();
        }
        catch (SecurityException e)
        {
            _services.GetLogger().severe("Failed to check if config \"%s\" exists: %s"
                    .formatted(name, PCString.ExceptionToString(e)));
        }
        return false;
    }

    @Override
    public ExplainedResult VerifyConfigName(String name)
    {
        Objects.requireNonNull(name, "name is null");
        /* This won't check if the filename is valid on the given file system, idk how to do that. */
        for (int i = 0; i < name.length(); i++)
        {
            char CurChar = name.charAt(i);
            if (!_allowedChars.contains(CurChar))
            {
                return ExplainedResult.Error("Configs may only contain the letters a-z, A-Z 0-9 and '_'");
            }
        }
        return ExplainedResult.Success();
    }

    @Override
    public ExplainedResult DeleteConfig(String name)
    {
        Objects.requireNonNull(name, "name is null");

        VerifyNameOrThrow(name);

        if (!DoesConfigExist(name))
        {
            return ExplainedResult.Error("Config \"%s\" does not exist.".formatted(name));
        }

        try
        {
            File TargetFile = GetConfigFilePath(name).toFile();
            if (!TargetFile.exists())
            {
                return ExplainedResult.Error("The config file \"%s\" does not exist.".formatted(name));
            }

            return (TargetFile.isFile() && TargetFile.delete()) ? ExplainedResult.Success()
                    : ExplainedResult.Error("Failed to delete config file due to an unknown reason.");
        }
        catch (SecurityException e)
        {
            throw new ConfigException("Failed to delete config \"%s\": %s"
                    .formatted(name, PCString.ExceptionToString(e)));
        }
    }

    @Override
    public List<String> GetConfigs()
    {
        try
        {
            return Optional.ofNullable(GetConfigDirectoryPath().toFile().listFiles())
                    .map(files -> Arrays.stream(files)
                            .map(file ->
                            {
                                String FileName = file.getName();
                                int LastSeparatorIndex = FileName.lastIndexOf(EXTENSION_INDICATOR);
                                return LastSeparatorIndex == -1 ? FileName : FileName.substring(0, LastSeparatorIndex);
                            })
                            .filter(name -> VerifyConfigName(name).IsSuccessful())
                            .toList())
                    .orElse(Collections.emptyList());
        }
        catch (SecurityException e)
        {
            throw new ConfigException("Failed to list config files: %s".formatted(PCString.ExceptionToString(e)));
        }
    }
}