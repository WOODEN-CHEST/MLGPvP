package sus.keiger.mlgpvp.game.entity.arrow.component;

import org.bukkit.event.entity.ProjectileHitEvent;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.CustomExplosionCreator;
import sus.keiger.mlgpvp.game.ExplosionCreateOptions;
import sus.keiger.mlgpvp.game.IExplosionCreator;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.GamePlayerHitByArrowEvent;

import java.util.Optional;

public class ArrowBukkitEventHandler extends GameEntityComponent<GameArrowEntity>
{
    // Private fields.
    private final IExplosionCreator _explosionCreator;


    // Constructors.
    public ArrowBukkitEventHandler(GameArrowEntity entity)
    {
        super(entity);
        _explosionCreator = new CustomExplosionCreator(GetGameInstance());
    }


    // Private methods.
    private void OnProjectileHitEvent(ProjectileHitEvent event)
    {
        if (!event.getEntity().equals(GetEntity().GetUnderlyingEntity()))
        {
            return;
        }

        HandleCollision(event);
    }

    private void HandleCollision(ProjectileHitEvent event)
    {
        if ((event.getHitEntity() != null) && !GetConfigValues().ArrowsExplodeOnDirectImpact)
        {
            return;
        }

        _explosionCreator.CreateExplosion(ExplosionCreateOptions.CreateFromValues(
                GetEntity().GetLocation(),
                GetEntity().GetExplosionStrengthScale(),
                GetEntity().GetShooter().orElse(null),
                GetConfigValues()));

        Optional.ofNullable(GetGameInstance().GetEntity(event.getHitEntity()))
                .flatMap(optional -> optional)
                .filter(entity -> entity instanceof GamePlayerEntity)
                .map(entity -> (GamePlayerEntity)entity)
                .ifPresent(entity ->
                        entity.GetHitByArrowEvent().FireEvent(new GamePlayerHitByArrowEvent(entity, GetEntity())));

        GetGameInstance().RemoveEntity(GetEntity());
    }


    // Inherited methods.

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        dispatcher.GetProjectileHitEvent().Subscribe(this, this::OnProjectileHitEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        dispatcher.GetProjectileHitEvent().Unsubscribe(this);
    }
}