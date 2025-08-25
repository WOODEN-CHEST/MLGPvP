package sus.keiger.mlgpvp.game.entity.arrow.component;

import org.bukkit.Particle;
import sus.keiger.mlgpvp.event.IEventDispatcher;
import sus.keiger.mlgpvp.game.entity.arrow.GameArrowEntity;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;

public class ArrowVisualsExecutor extends GameEntityComponent<GameArrowEntity>
{
    // Private static fields
    private static final Particle PARTICLE = Particle.FLAME;
    private static final double PARTICLE_DELTA = 0d;
    private static final int PARTICLE_COUNT = 1;
    private static final double PARTICLE_SPEED = 0;



    // Constructors.
    public ArrowVisualsExecutor(GameArrowEntity entity)
    {
        super(entity);
    }


    // Private methods.
    private void SpawnParticles()
    {
        GetGameInstance().SpawnParticle(PARTICLE,
                GetEntity().GetLocation(),
                PARTICLE_DELTA,
                PARTICLE_DELTA,
                PARTICLE_DELTA,
                PARTICLE_COUNT,
                PARTICLE_SPEED,
                null);
    }


    // Inherited methods.
    @Override
    public void Tick()
    {
        super.Tick();
        SpawnParticles();
    }
}
