package sus.keiger.mlgpvp.config;

import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.plugincommon.ExplainedResult;

import java.util.List;

/**
 * A config manager handles saving, loading, deleting and querying game configurations by name.
 * <br>An object of this class may retrieve the configurations from anywhere
 * (such as the network, file system, memory, etc.).
 */
public interface IConfigManager
{

    /**
     * Loads a configuration with the given name.
     * @param name The name of the configuration.
     * @return The loaded configration.
     * @throws ConfigException If the given config name is invalid, or a config with such a name doesn't exist,
     * or if there were other issues loading the config.
     */
    GameInstanceValues LoadConfig(String name) throws ConfigException;


    /**
     * Saves the given configuration.
     * <br>If a config with the same name already exists (and isn't a built-in config), it gets overwritten.
     * @param name The name of the configuration.
     * @param config The config o save.
     * @return Success if the config was saved, failure with a reason message if it couldn't be saved. This fails if
     * a built-in config with the same name exists.
     * @throws ConfigException
     */
    ExplainedResult SaveConfig(String name, GameInstanceValues config) throws ConfigException;


    /**
     * @param name
     * @return
     * @throws ConfigException
     */
    boolean DoesConfigExist(String name) throws ConfigException;


    /**
     * @param name
     * @return
     */
    ExplainedResult VerifyConfigName(String name);


    /**
     * @param name
     * @return
     * @throws ConfigException
     */
    ExplainedResult DeleteConfig(String name) throws ConfigException;


    /**
     * @return
     * @throws ConfigException
     */
    List<String> GetConfigs() throws ConfigException;
}