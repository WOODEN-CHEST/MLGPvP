package sus.keiger.mlgpvp.service;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.event.DefaultEventDispatcher;
import sus.keiger.mlgpvp.player.DefaultPlayerCollection;

import java.util.Objects;

/**
 * Helper clas to help create and initialise services.
 */
public class ServiceCreator
{
    /**
     * Creates services for this server from the given plugin.
     * <br>This method DOES have side effects, do not call it if you do not plan on using the returned services.
     * @param plugin The MLGPvP plugin.
     * @return New server services.
     * @throws NullPointerException if <code>plugin</code> is <code>null</code>/
     */
    // Methods.
    public IServerServices CreateServices(Plugin plugin)
    {
        Objects.requireNonNull(plugin, "plugin is null");
        IServerServices Services = new DefaultServerServices(plugin,
                new DefaultPlayerCollection(),
                new DefaultEventDispatcher());

        Bukkit.getPluginManager().registerEvents(Services.GetEventDispatcher(), Services.GetPlugin());

        return Services;
    }
}