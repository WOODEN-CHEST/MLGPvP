package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.entity.PlayerDeathEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerDamageEvent;
import sus.keiger.mlgpvp.game.entity.player.event.PlayerLifeChangeEvent;
import sus.keiger.plugincommon.PCPluginEvent;
import sus.keiger.plugincommon.entity.EntityFunctions;

public class PlayerLifeTracker extends GameEntityComponent<GamePlayerEntity>
{
    // Private fields.
    private final PCPluginEvent<PlayerLifeChangeEvent> _lifeChangeEvent = new PCPluginEvent<>();
    private final PCPluginEvent<GamePlayerDamageEvent> _damageEvent = new PCPluginEvent<>();

    private boolean _isAlive = true;
    private double _savedAttributeMaxHealth;
    private boolean _wasHealthReset = false;


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

        event.setCancelled(true);
        SetIsAlive(false);
    }

    private void DamageIfAlive(double amount, GameEntity source)
    {
        if (!GetIsAlive())
        {
            return;
        }

        GetEntity().GetPlayerEntity().damage(amount);
        _damageEvent.FireEvent(new GamePlayerDamageEvent(GetEntity(), amount, source));
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
        DamageIfAlive(amount, null);
    }

    public void DamageFromEntity(double amount, GameEntity source)
    {
        DamageIfAlive(amount, source);
    }

    public void Spawn()
    {
        SetIsAlive(true);
    }

    public void ResetHealth()
    {
        EntityFunctions.SetHealthPortion(GetEntity().GetPlayerEntity(), 1f);
    }

    public PCPluginEvent<PlayerLifeChangeEvent> GetLifeChangeEvent()
    {
        return _lifeChangeEvent;
    }

    public PCPluginEvent<GamePlayerDamageEvent> GetDamageEvent()
    {
        return _damageEvent;
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

    @Override
    public void AddPrepare()
    {
        super.AddPrepare();

        GetEntity().GetAttributeInstance(Attribute.MAX_HEALTH).ifPresent(instance ->
        {
            if (!_wasHealthReset)
            {
                _savedAttributeMaxHealth = instance.getBaseValue();
            }
            instance.setBaseValue(GetConfigValues().PlayerMaxHealth);
        });

        if (!_wasHealthReset)
        {
            SetIsAlive(true);
            ResetHealth();
            _wasHealthReset = true;
        }

        GetEntity().SetIsGlowing(GetEntity().GetIsAlive());
    }

    @Override
    public void RemoveCleanup()
    {
        super.RemoveCleanup();
        GetEntity().SetIsGlowing(false);
        GetEntity().GetAttributeInstance(Attribute.MAX_HEALTH).ifPresent(instance ->
                instance.setBaseValue(_savedAttributeMaxHealth));
    }
}