package sus.keiger.mlgpvp.game;

/**
 * Exception thrown when an operation on a {@link GameInstanceValues} object fails.
 */
public class GameValuesException extends RuntimeException
{
    public GameValuesException(String message)
    {
        super(message);
    }
}