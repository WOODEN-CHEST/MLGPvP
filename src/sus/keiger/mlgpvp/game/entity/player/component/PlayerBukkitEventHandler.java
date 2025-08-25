package sus.keiger.mlgpvp.game.entity.player.component;

import net.kyori.adventure.text.Component;
import org.bukkit.event.entity.EntityShootBowEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.PlayerGameEntity;

public class PlayerBukkitEventHandler extends GameEntityComponent<PlayerGameEntity>
{
    // Constructors.
    public PlayerBukkitEventHandler(PlayerGameEntity entity)
    {
        super(entity);
    }


    // Private methods.
    private void OnEntityShootBowEvent(EntityShootBowEvent event)
    {
        if (!event.getEntity().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }
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