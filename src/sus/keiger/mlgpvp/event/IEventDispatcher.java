package sus.keiger.mlgpvp.event;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public interface IEventDispatcher extends Listener
{
    PCPluginEvent<PlayerJoinEvent> GetJoinEvent();
    PCPluginEvent<PlayerQuitEvent> GetQuitEvent();
    PCPluginEvent<EntityShootBowEvent> GetShootBowEvent();
    PCPluginEvent<ProjectileHitEvent> GetProjectileHitEvent();
    PCPluginEvent<EntityDamageEvent> GetEntityDamageEvent();
    PCPluginEvent<EntityDeathEvent> GetEntityDeathEvent();
}