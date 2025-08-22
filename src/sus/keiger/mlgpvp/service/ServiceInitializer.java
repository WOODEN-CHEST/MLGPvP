package sus.keiger.mlgpvp.service;

import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.player.DefaultPlayerCollection;

public class ServiceInitializer
{
    // Methods.
    public IServerServices CreateServices(Plugin plugin)
    {
        return new DefaultServerServices(plugin,
                new DefaultPlayerCollection());
    }
}