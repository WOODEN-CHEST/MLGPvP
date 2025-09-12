package sus.keiger.mlgpvp.game.entity.player.component;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.PlayerGameStats;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.mlgpvp.game.entity.player.event.*;

import java.util.Optional;

public class PlayerStatTracker extends GameEntityComponent<GamePlayerEntity>
{
    // Constructors.
    public PlayerStatTracker(GamePlayerEntity entity)
    {
        super(entity);
    }


    // Private methods.
    private Optional<PlayerGameStats> GetStats()
    {
        return GetStats(GetEntity());
    }

    private Optional<PlayerGameStats> GetStats(GamePlayerEntity player)
    {
        return GetGameInstance().GetPlayerStats(player.GetServerPlayer());
    }

    private void OnPlayerDamageEvent(GamePlayerDamageEvent event)
    {
        GetStats().ifPresent(stats ->
        {
            stats.SetDamageTaken(stats.GetDamageTaken() + event.GetAmount());

            Optional<GameEntity> Source = event.GetSource();
            if (Source.isEmpty())
            {
                return;
            }

            Optional<PlayerGameStats> AttackerStats;

            if (Source.get() instanceof GamePlayerEntity PlayerSource)
            {
                AttackerStats = GetStats(PlayerSource);

            }
            else if (Source.get() instanceof GameArrowEntity ArrowEntity)
            {
                AttackerStats = ArrowEntity.GetShooter().flatMap(this::GetStats);
            }
            else
            {
                AttackerStats = Optional.empty();
            }

            AttackerStats.ifPresent(attackerStats ->
            {
                attackerStats.SetDamageDealt(attackerStats.GetDamageDealt() + event.GetAmount());
            });
        });
    }

    private void OnHitByArrowEvent(GamePlayerHitByArrowEvent event)
    {
        event.GetArrow().GetShooter().flatMap(this::GetStats).ifPresent(stats ->
        {
            stats.SetDirectHits(stats.GetDirectHits() + 1);
        });
    }

    private void OnPlayerLandMLGEvent(GamePlayerLandMLGEvent event)
    {
        GetStats().ifPresent(stats ->
        {
            stats.SetMLGSLanded(stats.GetMLGSLanded() + 1);
            AddDistanceFallen(stats, event.GetFallDistance());
        });
    }

    private void OnPlayerFailMLGEvent(GamePlayerFailMLGEvent event)
    {
        GetStats().ifPresent(stats ->
        {
            stats.SetMLGSFailed(stats.GetMLGSFailed() + 1);
            AddDistanceFallen(stats, event.GetFallDistance());
        });
    }

    private void AddDistanceFallen(PlayerGameStats stats, double distanceFallen)
    {
        stats.SetDistanceFallen(stats.GetDistanceFallen() + distanceFallen);
    }

    private void OnClimbEndEvent(GamePlayerClimbEndEvent event)
    {
        GetStats().ifPresent(stats ->
        {
            if (event.GetClimbHeight() > stats.GetHighestClimb())
            {
                stats.SetHighestClimb(event.GetClimbHeight());
            }
            stats.SetDistanceClimbed(stats.GetDistanceClimbed() + event.GetClimbHeight());
        });
    }

    private void OnFireArrowEvent(GamePlayerFireArrowEvent event)
    {
        GetStats().ifPresent(stats -> stats.SetArrowsFired(stats.GetArrowsFired() + 1));
    }



    // Inherited methods.

    @Override
    public void SubscribeToEvents(IEventDispatcher dispatcher)
    {
        super.SubscribeToEvents(dispatcher);

        GetEntity().GetFireArrowEvent().Subscribe(this, this::OnFireArrowEvent);
        GetEntity().GetLandMLGEvent().Subscribe(this, this::OnPlayerLandMLGEvent);
        GetEntity().GetFailMLGEvent().Subscribe(this, this::OnPlayerFailMLGEvent);
        GetEntity().GetClimbEndEvent().Subscribe(this, this::OnClimbEndEvent);
        GetEntity().GetDamageEvent().Subscribe(this, this::OnPlayerDamageEvent);
        GetEntity().GetHitByArrowEvent().Subscribe(this, this::OnHitByArrowEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);

        GetEntity().GetFireArrowEvent().Unsubscribe(this);
        GetEntity().GetLandMLGEvent().Unsubscribe(this);
        GetEntity().GetFailMLGEvent().Unsubscribe(this);
        GetEntity().GetClimbEndEvent().Unsubscribe(this);
        GetEntity().GetDamageEvent().Unsubscribe(this);
        GetEntity().GetHitByArrowEvent().Unsubscribe(this);
    }
}
