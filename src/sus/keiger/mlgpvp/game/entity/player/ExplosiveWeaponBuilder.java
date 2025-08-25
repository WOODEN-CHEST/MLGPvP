package sus.keiger.mlgpvp.game.entity.player;

import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import sus.keiger.mlgpvp.MLGPvPPlugin;
import sus.keiger.plugincommon.item.ItemFunctions;

import java.util.Optional;

public class ExplosiveWeaponBuilder
{
    // Private static fields.
    private static final NamespacedKey KEY_IS_MLGPVP_WEAPON
            = new NamespacedKey(MLGPvPPlugin.GetNamespace(), "weapon_is_mlgpvp_weapon");

    private static final NamespacedKey KEY_EXPLOSIONS_SCALE
            = new NamespacedKey(MLGPvPPlugin.GetNamespace(), "weapon_explosion_scale");
    private static final NamespacedKey KEY_IS_AMMO_CONSUMED
            = new NamespacedKey(MLGPvPPlugin.GetNamespace(), "weapon_is_ammo_consumed");

    private static final double DEFAULT_EXPLOSION_STRENGTH = 0d;
    private static final boolean DEFAULT_IS_AMMO_CONSUMED = true;


    // Constructors.
    private ExplosiveWeaponBuilder() { }


    // Static methods.
    public static void SetWeaponData(ItemStack item, double explosionScale, boolean isAmmoConsumed)
    {
        item.editMeta(meta ->
        {
            meta.getPersistentDataContainer().set(KEY_IS_MLGPVP_WEAPON, PersistentDataType.BOOLEAN, true);
            meta.getPersistentDataContainer().set(KEY_EXPLOSIONS_SCALE, PersistentDataType.DOUBLE, explosionScale);
            meta.getPersistentDataContainer().set(KEY_IS_AMMO_CONSUMED, PersistentDataType.BOOLEAN, isAmmoConsumed);
        });
    }

    public static Optional<ExplosiveWeaponStats> GetWeaponStats(ItemStack item)
    {
        if (ItemFunctions.IsItemEmpty(item))
        {
            return Optional.empty();
        }

        PersistentDataContainerView Container = item.getPersistentDataContainer();
        if (!Container.has(KEY_IS_MLGPVP_WEAPON))
        {
            return Optional.empty();
        }

        return Optional.of(new ExplosiveWeaponStats(
                Container.getOrDefault(KEY_EXPLOSIONS_SCALE, PersistentDataType.DOUBLE, DEFAULT_EXPLOSION_STRENGTH),
                Container.getOrDefault(KEY_IS_AMMO_CONSUMED, PersistentDataType.BOOLEAN, DEFAULT_IS_AMMO_CONSUMED)));
    }
}