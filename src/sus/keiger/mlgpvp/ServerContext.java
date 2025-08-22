package sus.keiger.mlgpvp;

import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.mlgpvp.service.ServiceInitializer;
import sus.keiger.plugincommon.PCString;

import java.util.Objects;

public class ServerContext
{
    // Private fields.
    private final Plugin _plugin;

    private boolean _isInitialized = false;
    private IServerServices _services;


    // Constructors.
    public ServerContext(Plugin plugin)
    {

        _plugin = Objects.requireNonNull(plugin, "plugin is null");
    }


    // Methods.
    public void Initialize() throws ServerContextException
    {
        if (_isInitialized)
        {
            throw new IllegalStateException("Server context already is initialized.");
        }

        try
        {
            _services = new ServiceInitializer().CreateServices(_plugin);
        }
        catch (Exception e)
        {
            throw new ServerContextException("Failed to initialize server context: %s"
                    .formatted(PCString.ExceptionToString(e)));
        }
    }

    public void Deinitialize() throws ServerContextException
    {
        if (!_isInitialized)
        {
            throw new IllegalStateException("Server context hasn't been initialized yet.");
        }

        try
        {

        }
        catch (Exception e)
        {
            throw new ServerContextException("Failed to deinitialize server context: %s"
                    .formatted(PCString.ExceptionToString(e)));
        }
    }
}