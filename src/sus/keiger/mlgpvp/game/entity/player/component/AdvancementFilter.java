package sus.keiger.mlgpvp.game.entity.player.component;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

public class AdvancementFilter extends GameEntityComponent<GamePlayerEntity>
{
    // Constructors.
    public AdvancementFilter(GamePlayerEntity entity)
    {
        super(entity);
    }


    // Private methods.
    private void OnCriteriaGrantEvent(PlayerAdvancementCriterionGrantEvent event)
    {
        if (event.getPlayer().equals(GetEntity().GetPlayerEntity()))
        {
            event.setCancelled(true);
        }
    }


    // Inherited methods.

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        dispatcher.GetAdvancementCriteriaGrantEvent().Subscribe(this, this::OnCriteriaGrantEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        dispatcher.GetAdvancementCriteriaGrantEvent().Unsubscribe(this);
    }
}