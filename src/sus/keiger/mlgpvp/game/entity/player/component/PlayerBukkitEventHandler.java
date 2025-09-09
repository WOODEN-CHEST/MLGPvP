package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.ExplosiveWeaponBuilder;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerEmptyBucketEvent;
import sus.keiger.plugincommon.PCPluginEvent;


public class PlayerBukkitEventHandler extends GameEntityComponent<GamePlayerEntity>
{
    // Private fields.
    private final PCPluginEvent<GamePlayerEmptyBucketEvent> _emptyBucketEvent = new PCPluginEvent<>();


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


    // Inherited methods.
    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        dispatcher.GetShootBowEvent().Subscribe(this, this::OnEntityShootBowEvent);
        dispatcher.GetPlayerBucketEmptyEvent().Subscribe(this, this::OnEmptyBucketEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        dispatcher.GetShootBowEvent().Unsubscribe(this);
        dispatcher.GetPlayerBucketEmptyEvent().Unsubscribe(this);
    }
}