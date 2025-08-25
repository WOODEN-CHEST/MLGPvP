package sus.keiger.mlgpvp.game.entity.arrow.component;

import org.bukkit.event.entity.ProjectileHitEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;

public class ArrowBukkitEventHandler extends GameEntityComponent<GameArrowEntity>
{
    // Constructors.
    public ArrowBukkitEventHandler(GameArrowEntity entity)
    {
        super(entity);
    }


    // Private methods.
    private void OnProjectileHitEvent(ProjectileHitEvent event)
    {
        if (!event.getEntity().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        HandleCollision();
    }

    private void HandleCollision()
    {

    }


    // Inherited methods.

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);
    }
}