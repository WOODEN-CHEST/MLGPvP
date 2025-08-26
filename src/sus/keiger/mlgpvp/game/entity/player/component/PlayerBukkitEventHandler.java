package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.ExplosiveWeaponBuilder;
import sus.keiger.mlgpvp.game.entity.player.ExplosiveWeaponStats;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.plugincommon.item.ItemFunctions;
import sus.keiger.plugincommon.player.PlayerFunctions;

public class PlayerBukkitEventHandler extends GameEntityComponent<GamePlayerEntity>
{
    // Constructors.
    public PlayerBukkitEventHandler(GamePlayerEntity entity)
    {
        super(entity);
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



    // Inherited methods.

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        dispatcher.GetShootBowEvent().Subscribe(this, this::OnEntityShootBowEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        dispatcher.GetShootBowEvent().Unsubscribe(this);
    }
}