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
     * @return Success if the config was saved, failure with a reason message if it couldn't be saved due
     * to a non-fatal error. A non-fatal error includes built-in config with the same name existing.
     * @throws ConfigException If the config name is invalid, or an exception occurred saving the given config.
     */
    ExplainedResult SaveConfig(String name, GameInstanceValues config) throws ConfigException;


    /**
     * Gets whether a config with the given name exists.
     * <br>This check includes built-in configs.
     * @param name The name of the config to check.
     * @return <code>true</code> if a config with this name exists, <code>false</code> otherwise.
     * @throws ConfigException If the name is invalid or an exception occurred while checking if the config exists.
     */
    boolean DoesConfigExist(String name) throws ConfigException;


    /**
     * Verifies that the given config name is valid.
     * @param name The name to check.
     * @return Success if the name is valid, or failure with an explanation if it's invalid.
     */
    ExplainedResult VerifyConfigName(String name);


    /**
     * Deletes the given config.
     * @param name The name of the config to delete.
     * @return Success if the config was deleted, failure with an explanation if a non-fatal error occurred while
     * deleting the config. Non-fatal errors include a built-in config with the same name existing, as built-in
     * configs cannot be deleted.
     * @throws ConfigException If the name is invalid or an exception occurred while deleting the config.
     */
    ExplainedResult DeleteConfig(String name) throws ConfigException;


    /**
     * Gets a list of saved config names.
     * <br>This includes built-in configs.
     * @return The list of all saved dconfig names.
     * @throws ConfigException If an exception occurred while retrieving the config names.
     */
    List<String> GetConfigs() throws ConfigException;
}