package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.inventory.ItemStack;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MLGRewarder extends GameEntityComponent<GamePlayerEntity>
{
    // Private fields.
    private final List<MLGReward> _rewards = new ArrayList<>();


    // Constructors.
    public MLGRewarder(GamePlayerEntity entity)
    {
        super(entity);
        InitRewardList();
    }


    // Methods.
    public void RewardPlayer(double fallDistance)
    {

    }


    // Private methods.
    private void InitRewardList()
    {

    }


    // Types.
    private record MLGReward(double RequiredFalLDistance, Supplier<ItemStack> RewardProvider) {}
}