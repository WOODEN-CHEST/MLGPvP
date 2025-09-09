package sus.keiger.mlgpvp.event;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sus.keiger.plugincommon.EmptyEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public interface IEventDispatcher extends Listener
{
    PCPluginEvent<PlayerJoinEvent> GetJoinEvent();
    PCPluginEvent<PlayerQuitEvent> GetQuitEvent();
    PCPluginEvent<EntityShootBowEvent> GetShootBowEvent();
    PCPluginEvent<ProjectileHitEvent> GetProjectileHitEvent();
    PCPluginEvent<EntityDamageEvent> GetEntityDamageEvent();
    PCPluginEvent<PlayerDeathEvent> GetEntityDeathEvent();
    PCPluginEvent<EmptyEvent> GetTickEvent();
    PCPluginEvent<PlayerBucketEmptyEvent> GetPlayerBucketEmptyEvent();
}