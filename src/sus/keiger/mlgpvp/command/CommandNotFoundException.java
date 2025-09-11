package sus.keiger.mlgpvp.command;

/**
 * Exception thrown when the <code>CommandInitializer</code> attempts to init a command but cannot find it.
 */
public class CommandNotFoundException extends RuntimeException
{
    public CommandNotFoundException(String message)
    {
        super(message);
    }
}