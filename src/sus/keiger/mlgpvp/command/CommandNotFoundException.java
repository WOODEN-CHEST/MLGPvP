package sus.keiger.mlgpvp.command;

public class CommandNotFoundException extends RuntimeException
{
    public CommandNotFoundException(String message)
    {
        super(message);
    }
}