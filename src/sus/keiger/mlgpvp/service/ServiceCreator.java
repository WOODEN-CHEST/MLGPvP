package sus.keiger.mlgpvp.service;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.event.DefaultEventDispatcher;
import sus.keiger.mlgpvp.player.DefaultPlayerCollection;

public class ServiceCreator
{
    // Methods.
    public IServerServices CreateServices(Plugin plugin)
    {
        IServerServices Services = new DefaultServerServices(plugin,
                new DefaultPlayerCollection(),
                new DefaultEventDispatcher());

        Bukkit.getPluginManager().registerEvents(Services.GetEventDispatcher(), Services.GetPlugin());

        return Services;
    }
}