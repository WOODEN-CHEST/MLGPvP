package sus.keiger.mlgpvp.game.entity.player.component;

import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.PlayerGameStats;
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

    private void OnPlayerDamageEvent(GamePlayerTakeDamageEvent event)
    {
        GetStats().ifPresent(stats ->
        {
            stats.SetDamageTaken(stats.GetDamageTaken() + event.GetFinalDamageAmount());

            Optional<GamePlayerEntity> HitPlayer = event.GetCausePlayer();
            if (HitPlayer.isEmpty())
            {
                return;
            }
            GetStats(HitPlayer.get()).ifPresent(attackerStats ->
            {
                attackerStats.SetDamageDealt(attackerStats.GetDamageDealt() + event.GetFinalDamageAmount());
                if (event.IsProjectileHit())
                {
                    attackerStats.SetDirectHits(attackerStats.GetDirectHits() + 1);
                }
            });
        });

    }

    private void OnPlayerLandMLGEvent(GamePlayerLandMLGEvent event)
    {
        GetStats().ifPresent(stats ->
        {
            stats.SetWaterBucketsLanded(stats.GetWaterBucketsLanded() + 1);
            AddDistanceFallen(stats, event.GetFallDistance());
        });
    }

    private void OnPlayerFailMLGEvent(GamePlayerFailMLGEvent event)
    {
        GetStats().ifPresent(stats ->
        {
            stats.SetWaterBucketsFailed(stats.GetWaterBucketsFailed() + 1);
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
        GetEntity().GetDamageTakeEvent().Subscribe(this, this::OnPlayerDamageEvent);
    }

    @Override
    public void UnsubscribeFromEvents(IEventDispatcher dispatcher)
    {
        super.UnsubscribeFromEvents(dispatcher);
    }
}
