package sus.keiger.mlgpvp.config;

/**
 * Exception thrown when there is some issue reading, writing, deleting or otherwise interacting with a config.
 */
public class ConfigException extends RuntimeException
{
    // Constructors.
    public ConfigException(String message)
    {
        super(message);
    }
}