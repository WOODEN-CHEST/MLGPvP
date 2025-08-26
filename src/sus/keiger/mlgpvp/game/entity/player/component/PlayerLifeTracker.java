package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.entity.PlayerDeathEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.PlayerLifeChangeEvent;
import sus.keiger.plugincommon.PCPluginEvent;

public class PlayerLifeTracker extends GameEntityComponent<GamePlayerEntity>
{
    // Private fields.
    private boolean _isAlive = true;
    private final PCPluginEvent<PlayerLifeChangeEvent> _lifeChangeEvent = new PCPluginEvent<>();


    // Constructors.
    public PlayerLifeTracker(GamePlayerEntity entity)
    {
        super(entity);
    }


    // Private methods.
    private void OnPlayerDeathEvent(PlayerDeathEvent event)
    {
        if (!event.getPlayer().equals(GetEntity().GetPlayerEntity()))
        {
            return;
        }

        SetIsAlive(false);
    }


    // Constructors.
    public void SetIsAlive(boolean isAlive)
    {
        if (_isAlive == isAlive)
        {
            return;
        }

        _isAlive = isAlive;
        _lifeChangeEvent.FireEvent(new PlayerLifeChangeEvent(GetEntity(), _isAlive));
    }

    public boolean GetIsAlive()
    {
        return _isAlive;
    }

    public void Damage(double amount)
    {
        if (!GetIsAlive())
        {
            return;
        }

        GetEntity().GetPlayerEntity().damage(amount);
    }

    public void Spawn()
    {
        SetIsAlive(true);
    }

    public void ResetHealth()
    {
        AttributeInstance Health = GetEntity().GetPlayerEntity().getAttribute(Attribute.MAX_HEALTH);
        if (Health != null)
        {
            GetEntity().GetPlayerEntity().setHealth(Health.getValue());
        }
    }

    public PCPluginEvent<PlayerLifeChangeEvent> GetLifeChangeEvent()
    {
        return _lifeChangeEvent;
    }


    // Inherited methods.

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        dispatcher.GetEntityDeathEvent().Subscribe(this, this::OnPlayerDeathEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        dispatcher.GetEntityDeathEvent().Unsubscribe(this);
    }
}