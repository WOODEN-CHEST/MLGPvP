package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;
import sus.keiger.plugincommon.player.PlayerFunctions;

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
    public void RewardPlayer(double fallFactor)
    {
        for (int i = _rewards.size() - 1; i > 0; i--)
        {
            MLGReward Reward = _rewards.get(i);
            if (Reward.RequiredFactor <= fallFactor)
            {
                PlayerFunctions.AddItem(GetEntity().GetPlayerEntity(), Reward.RewardProvider.get());
            }
        }
    }


    // Private methods.
    private void InitRewardList()
    {
        _rewards.clear();
        _rewards.add(new MLGReward(0.1d, () -> ItemStack.of(Material.ARROW, 1)));
        _rewards.add(new MLGReward(0.25d, () -> ItemStack.of(Material.ARROW, 1)));
        _rewards.add(new MLGReward(0.35d, () -> ItemStack.of(Material.GOLDEN_APPLE, 1)));
        _rewards.add(new MLGReward(0.5d, () -> ItemStack.of(Material.ARROW, 1)));
        _rewards.add(new MLGReward(0.7d, () -> ItemStack.of(Material.ARROW, 1)));
        _rewards.add(new MLGReward(0.85d, () -> ItemStack.of(Material.GOLDEN_APPLE, 1)));
        _rewards.add(new MLGReward(0.99d, () -> ItemStack.of(Material.ARROW, 1)));
    }


    // Types.
    private record MLGReward(double RequiredFactor, Supplier<ItemStack> RewardProvider) {}
}