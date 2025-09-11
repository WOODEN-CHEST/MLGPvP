package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.ExplosiveWeaponBuilder;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerDamageEvent;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerEmptyBucketEvent;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerFireArrowEvent;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerHitByArrowEvent;
import sus.keiger.plugincommon.PCPluginEvent;


public class PlayerBukkitEventHandler extends GameEntityComponent<GamePlayerEntity>
{
    // Private fields.
    private final PCPluginEvent<GamePlayerEmptyBucketEvent> _emptyBucketEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GamePlayerFireArrowEvent> _fireArrowEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GamePlayerHitByArrowEvent> _hitByArrowEvent = new PCPluginEvent<>();


    // Constructors.
    public PlayerBukkitEventHandler(GamePlayerEntity entity)
    {
        super(entity);
    }


    // Methods.
    public PCPluginEvent<GamePlayerEmptyBucketEvent> GetEmptyBucketEvent()
    {
        return _emptyBucketEvent;
    }

    public PCPluginEvent<GamePlayerFireArrowEvent> GetFireArrowEvent()
    {
        return _fireArrowEvent;
    }

    public PCPluginEvent<GamePlayerHitByArrowEvent> GetHitByArrowEvent()
    {
        return _hitByArrowEvent;
    }


    // Private methods.
    private void OnEntityShootBowEvent(EntityShootBowEvent event)
    {
        if (!event.getEntity().equals(GetEntity().GetUnderlyingEntity())
                || !(event.getProjectile() instanceof Arrow ArrowProjectile))
        {
            return;
        }

        ExplosiveWeaponBuilder.GetWeaponStats(event.getBow()).ifPresent(stats ->
        {
            GameArrowEntity SpawnedEntity = new GameArrowEntity(
                    GetGameInstance(),
                    ArrowProjectile,
                    GetEntity(),
                    stats.StrengthScale());
            GetGameInstance().AddEntity(SpawnedEntity);

            _fireArrowEvent.FireEvent(new GamePlayerFireArrowEvent(GetEntity(), SpawnedEntity));
        });
    }

    private void OnEmptyBucketEvent(PlayerBucketEmptyEvent event)
    {
        if (!event.getPlayer().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        _emptyBucketEvent.FireEvent(new GamePlayerEmptyBucketEvent(GetEntity(), event));
    }

    private void OnEntityDamageEvent(EntityDamageEvent event)
    {
        if (!event.getEntity().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        GameEntity SourceEntity = (event instanceof EntityDamageByEntityEvent ByEntityEvent) ?
                GetGameInstance().GetEntity(ByEntityEvent.getDamager()).orElse(null) : null;

        GetEntity().GetDamageEvent().FireEvent(new GamePlayerDamageEvent(GetEntity(), event.getDamage(), SourceEntity));
    }



    // Inherited methods.
    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        dispatcher.GetShootBowEvent().Subscribe(this, this::OnEntityShootBowEvent);
        dispatcher.GetPlayerBucketEmptyEvent().Subscribe(this, this::OnEmptyBucketEvent);
        dispatcher.GetEntityDamageEvent().Subscribe(this, this::OnEntityDamageEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        dispatcher.GetShootBowEvent().Unsubscribe(this);
        dispatcher.GetPlayerBucketEmptyEvent().Unsubscribe(this);
        dispatcher.GetEntityDamageEvent().Unsubscribe(this);
    }
}