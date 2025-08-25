package sus.keiger.mlgpvp.game.entity.player.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.ExplosiveWeaponBuilder;
import sus.keiger.mlgpvp.game.entity.player.PlayerGameEntity;
import sus.keiger.plugincommon.player.PlayerFunctions;

import java.util.function.Function;
import java.util.function.Supplier;

public class PlayerInitializer extends GameEntityComponent<PlayerGameEntity>
{
    // Private static fields.
    private static final int MAX_ITEMS_TO_ADD = 1024;
    private static final int[] SLOT_FIRST_TOTEM = new int[] { PlayerFunctions.SLOT_OFFHAND };
    private static final int[] SLOT_FIRST_HELMET = new int[] { PlayerFunctions.SLOT_ARMOR_HEAD };
    private static final int[] SLOT_FIRST_CHESTPLATE = new int[] { PlayerFunctions.SLOT_ARMOR_CHEST };
    private static final int[] SLOT_FIRST_LEGGINGS = new int[] { PlayerFunctions.SLOT_ARMOR_LEGS };
    private static final int[] SLOT_FIRST_BOOTS = new int[] { PlayerFunctions.SLOT_ARMOR_FEET };

    private static final double POWER_BOW = 1d;
    private static final boolean DOES_BOW_USE_ARROWS = false;
    private static final double POWER_CROSSBOW = 1.75d;
    private static final boolean DOES_CROSSBOW_USE_ARROWS = true;



    // Constructors.
    public PlayerInitializer(PlayerGameEntity entity)
    {
        super(entity);
    }


    // Private methods.
    private void InitInventory()
    {
        PlayerFunctions.ClearInventory(GetEntity().GetPlayerEntity());

        AddItems(() -> CreateUnbreakableTool(Material.NETHERITE_SWORD), 1, null);
        AddItems(() -> ItemStack.of(Material.WATER_BUCKET), GetConfigValues().WaterBucketCount, null);
        AddItems(this::GetBow, GetConfigValues().IsExplosiveBowEnabled ? 1 : 0, null);
        AddItems(this::GetCrossbow, GetConfigValues().IsExplosiveCrossbowEnabled ? 1 : 0, null);
        AddItems(() -> ItemStack.of(Material.GOLDEN_APPLE), GetConfigValues().GoldenAppleCount, null);
        AddItems(() -> CreateUnbreakableTool(Material.NETHERITE_PICKAXE), 1, null);
        AddItems(() -> CreateUnbreakableTool(Material.NETHERITE_AXE), 1, null);
        AddItems(() -> CreateUnbreakableTool(Material.NETHERITE_SHOVEL), 1, null);
        AddItems(() -> ItemStack.of(Material.TOTEM_OF_UNDYING), GetConfigValues().TotemCount, SLOT_FIRST_TOTEM);

        int ArmorSetCount = GetConfigValues().ArmorSetCount;
        for (int i = 0; i < ArmorSetCount; i++)
        {
            AddItems(() -> CreateArmor(Material.NETHERITE_HELMET), 1, SLOT_FIRST_HELMET);
            AddItems(() -> CreateArmor(Material.NETHERITE_CHESTPLATE), 1, SLOT_FIRST_CHESTPLATE);
            AddItems(() -> CreateArmor(Material.NETHERITE_LEGGINGS), 1, SLOT_FIRST_LEGGINGS);
            AddItems(() -> CreateArmor(Material.NETHERITE_BOOTS), 1, SLOT_FIRST_BOOTS);
        }
    }

    private ItemStack CreateUnbreakableTool(Material material)
    {
        ItemStack Item = ItemStack.of(material);
        Item.editMeta(meta -> meta.setUnbreakable(true));
        return Item;
    }

    private ItemStack CreateArmor(Material material)
    {
        ItemStack Item = CreateUnbreakableTool(material);
        Item.editMeta(meta ->
        {
            meta.addEnchant(Enchantment.PROTECTION, 1, true);
            if (Enchantment.FEATHER_FALLING.canEnchantItem(Item) && (GetConfigValues().FeatherFallingLevel > 0))
            {
                meta.addEnchant(Enchantment.FEATHER_FALLING, GetConfigValues().FeatherFallingLevel, true);
            }
        });
        return Item;
    }

    private ItemStack GetBow()
    {
        ItemStack Bow = CreateUnbreakableTool(Material.BOW);
        Bow.editMeta(meta ->
        {
            meta.displayName(Component.text("Sigma Bow")
                    .decoration(TextDecoration.ITALIC, false)
                    .color(NamedTextColor.LIGHT_PURPLE));
            meta.addEnchant(Enchantment.INFINITY, 1, true);
        });
        ExplosiveWeaponBuilder.SetWeaponData(Bow, POWER_BOW, DOES_BOW_USE_ARROWS);

        return Bow;
    }

    private ItemStack GetCrossbow()
    {
        ItemStack Bow = CreateUnbreakableTool(Material.CROSSBOW);
        Bow.editMeta(meta ->
        {
            meta.displayName(Component.text("Omega Crossbow")
                    .decoration(TextDecoration.ITALIC, false)
                    .color(NamedTextColor.GOLD));
            meta.addEnchant(Enchantment.MULTISHOT, 1, true);
        });
        ExplosiveWeaponBuilder.SetWeaponData(Bow, POWER_CROSSBOW, DOES_CROSSBOW_USE_ARROWS);

        return Bow;
    }

    private void AddItems(Supplier<ItemStack> itemSupplier, int count, int[] startSlots)
    {
        if (count > MAX_ITEMS_TO_ADD)
        {
            throw new IllegalArgumentException("Invalid count of items supplied (%s), maximum is %d"
                    .formatted(count, MAX_ITEMS_TO_ADD));
        }

        Player TargetPlayer = GetEntity().GetPlayerEntity();
        int RemainingCount = count;

        if (startSlots != null)
        {
            for (int Slot : startSlots)
            {
                ItemStack Item = itemSupplier.get();
                Item.setAmount(Math.min(RemainingCount, Item.getMaxStackSize()));
                RemainingCount -= Item.getAmount();
                PlayerFunctions.SetOrAddItem(Item, Slot, TargetPlayer);
            }
        }

        while (RemainingCount > 0)
        {
            ItemStack Item = itemSupplier.get();
            Item.setAmount(Math.min(RemainingCount, Item.getMaxStackSize()));
            RemainingCount -= Item.getAmount();
            PlayerFunctions.AddItem(TargetPlayer, Item);
        }
    }

    private void InitializeProperties()
    {
        GetEntity().SetFood(PlayerFunctions.MAX_FOOD);
        GetEntity().SetSaturation(PlayerFunctions.MAX_SATURATION);
        GetEntity().ClearPotionEffects();
        GetEntity().SetIsGlowing(true);
        GetEntity().SetGameMode(GameMode.SURVIVAL);
        GetEntity().SetIsAlive(true);
    }


    // Inherited methods.

    @Override
    public void Initialize()
    {
        super.Initialize();
        InitInventory();
        InitializeProperties();
    }
}