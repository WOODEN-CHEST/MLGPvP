package sus.keiger.mlgpvp.game.component;

import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.mlgpvp.game.IGameInstanceExtended;
import sus.keiger.plugincommon.ITickable;

public abstract class GameComponent<T extends IGameInstanceExtended> implements ITickable
{
    // Private fields.
    private final T _gameInstance;


    // Constructors.
    public GameComponent(T gameInstance)
    {
        _gameInstance = gameInstance;
    }


    // Methods.
    public T GetGameInstance()
    {
        return _gameInstance;
    }

    public GameInstanceValues GetValues()
    {
        return _gameInstance.GetConfigValues();
    }


    // Inherited methods.

    @Override
    public void Tick() { }
}