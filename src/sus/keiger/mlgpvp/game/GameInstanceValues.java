package sus.keiger.mlgpvp.game;

import sus.keiger.plugincommon.PCString;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Stores configuration for a game instance, this pretty much is just a bunch of modifiable "constants" in code
 * lumped together in one class.
 *
 * <br>The fields which are meant to be modified must be public and annotated with "GameXField" annotations.
 *
 * <br>The values are modified using reflection.
 */
public class GameInstanceValues
{
    // Fields.
    /* Sounds. */
    @GameBoolField(Description = "Whether the ticking sound is played while the player is climbing in altitude.")
    public boolean IsClimbingSoundEnabled = true;

    /* Time. */
    @GameDoubleField(Description = "The maximum duration of the game, in seconds, before the deathmatch starts.",
        MinValue = 0.1d, MaxValue = 60d * 60d * 24d)
    public double MaxGameDurationSeconds = 60d * 12.5d;


    /* Border */
    @GameDoubleField(Description = "The diameter of the border at the start of the game.",
            MinValue = 1d, MaxValue = 10_000_000d)
    public double BorderDiameterMax = 250d;

    @GameDoubleField(Description = "The diameter of the border to which it shrinks.",
            MinValue = 1d, MaxValue = 10_000_000d)
    public double BorderDiameterMin = 5d;

    @GameDoubleField(Description = "The duration of time which needs to pass before the border begins to shrink, " +
            "in seconds.", MinValue = 0.1d, MaxValue = 60d * 60d * 24d)
    public double BorderShrinkStartTimeSeconds = 60d * 5d;

    /* Items */
    @GameBoolField(Description = "Whether certain items (like golden apples and arrows) are rewarded for landing " +
            "an MLG water bucket.")
    public boolean IsMLGRewardingEnabled = true;

    @GameIntField(Description = ITEM_DESC_START + "totems" + ITEM_DESC_END,
        MinValue = 0, MaxValue = 16)
    public int TotemCount = 5;

    @GameIntField(Description = ITEM_DESC_START + "water buckets" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 16)
    public int WaterBucketCount = 2;

    @GameIntField(Description = ITEM_DESC_START + "powdered snow buckets" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 16)
    public int PowderedSnowBucketCount = 0;

    @GameIntField(Description = ITEM_DESC_START + "scaffolding blocks" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 256)
    public int ScaffoldingCount = 0;

    @GameIntField(Description = ITEM_DESC_START + "sweet berries" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 256)
    public int SweetBerryCount = 0;

    @GameIntField(Description = ITEM_DESC_START + "ladders" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 256)
    public int LadderCount = 0;

    @GameIntField(Description = ITEM_DESC_START + "vines" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 256)
    public int VineCount = 0;

    @GameIntField(Description = ITEM_DESC_START + "twisting vines" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 256)
    public int TwistingVineCount = 0;

    @GameIntField(Description = ITEM_DESC_START + "cobwebs" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 256)
    public int CobwebCount = 0;

    @GameBoolField(Description = "Whether the players are given a sword.")
    public boolean IsSwordIncluded = true;

    @GameBoolField(Description = "Whether the players are given an axe.")
    public boolean IsAxeIncluded = true;

    @GameBoolField(Description = "Whether the players are given a shovel.")
    public boolean IsShovelIncluded = true;

    @GameBoolField(Description = "Whether the players are given a pickaxe.")
    public boolean IsPickaxeIncluded = true;

    @GameBoolField(Description = "Whether the players are given a full set of netherite armor.")
    public boolean IsArmorIncluded = true;

    @GameIntField(Description = ITEM_DESC_START + "golden apples" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 128)
    public int GoldenAppleCount = 12;

    @GameIntField(Description = ITEM_DESC_START + "arrows" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 512)
    public int ArrowCount = 24;

    @GameIntField(Description = ITEM_DESC_START + "ender pearls" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 64)
    public int EnderPearlCount = 0;

    @GameIntField(Description = ITEM_DESC_START + "chorus fruit" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 512)
    public int ChorusFruitCount = 0;

    @GameIntField(Description = ITEM_DESC_START + "slime blocks" + ITEM_DESC_END,
            MinValue = 0, MaxValue = 512)
    public int SlimeBlockCount = 0;

    @GameBoolField(Description = "Whether chorus fruits can be eaten.")
    public boolean IsChorusFruitEnabled = true;

    @GameBoolField(Description = "Whether ender pearls can be used.")
    public boolean IsEnderPearlsEnabled = true;

    @GameBoolField(Description = "Whether the players are given the explosive bow")
    public boolean IsExplosiveBowEnabled = true;

    @GameBoolField(Description = "Whether the players are given the explosive crossbow")
    public boolean IsExplosiveCrossbowEnabled = true;

    @GameIntField(Description = "The feather falling level applied to boots.",
            MinValue = 0, MaxValue = 10)
    public int FeatherFallingLevel = 2;

    @GameIntField(Description = "The armor protection enchantment level applied to all armor pieces.",
            MinValue = 0, MaxValue = 5)
    public int ArmorProtectionLevel = 2;


    /* Explosions */
    @GameDoubleField(Description = "The scale of explosion knockback.",
            MinValue = 0, MaxValue = 16)
    public double ExplosionKnockbackScale = 1d;

    @GameDoubleField(Description = "The scale of explosion damage to players' health.",
            MinValue = 0, MaxValue = 16)
    public double ExplosionDamageScale = 1d;

    @GameDoubleField(Description = "The scale of explosion block destruction radius.",
            MinValue = 0, MaxValue = 16)
    public double ExplosionBlockDamageScale = 1d;

    @GameBoolField(Description = "Whether directly hitting a player with an arrow causes an explosion.")
    public boolean ArrowsExplodeOnDirectImpact = true;

    @GameDoubleField(Description = "The scale of bow explosion power.",
            MinValue = 0, MaxValue = 10d)
    public double BowExplosionPower = 1.5d;

    @GameDoubleField(Description = "The scale of crossbow explosion power.",
            MinValue = 0, MaxValue = 10d)
    public double CrossbowExplosionPower = 2.7d;

    @GameDoubleField(Description = "The multiplier of shot arrow velocity.",
            MinValue = -10d, MaxValue = 10d)
    public double ArrowSpeedMultiplier = 1d;


    /* Other. */
    @GameBoolField(Description = "Whether the climb height is reset when a player is affected by a smaller " +
            "explosion while still mid-air from a previous explosion. Set to false for a more casual experience.")
    public boolean IsClimbHeightReset = true;


    /* Attributes. */
    @GameDoubleField(Description = "The player's max health, in half-hearts.",
            MinValue = 1d, MaxValue = 100d)
    public double PlayerMaxHealth = 20d;

    @GameDoubleField(Description = "The player's block interaction range, in blocks.",
            MinValue = 1d, MaxValue = 100d)
    public double PlayerBlockReach = 4.5;

    @GameDoubleField(Description = "The player's gravity acceleration in blocks per tick " +
            "(blocks per 1/20th of a second).", MinValue = -1, MaxValue = 1d)
    public double PlayerGravity = 0.08;

    @GameDoubleField(Description = "The jump strength, larger value = higher jump.", MinValue = 0, MaxValue = 10d)
    public double PlayerJumpStrength = 0.42;


    /* Damage. */
    @GameBoolField(Description = "Whether melee attacks deal damage.")
    public boolean IsMeleeDamageEnabled = true;

    @GameBoolField(Description = "Whether explosions deal damage.")
    public boolean IsExplosionDamageEnabled = true;

    @GameBoolField(Description = "Whether arrows (on direct hits) deal damage.")
    public boolean IsArrowDamageEnabled = true;


    //Private static fields.
    private static final String ITEM_DESC_START = "The amount of ";
    private static final String ITEM_DESC_END = " granted to each player.";



    // Static methods.
    /**
     * Gets a list of fields in this class which are considered modifiable constants, any other fields outside
     * these should not be used in other field related methods and should not be modified.
     * @return List of modifiable fields.
     */
    public static List<Field> GetModifiableFields()
    {
        return Arrays.stream(GameInstanceValues.class.getFields())
                .filter(GameInstanceValues::IsModifiableField)
                .toList();
    }


    /**
     * Determines whether the following field is an annotated modifiable constant.
     * @param field The field to test.
     * @return <code>true</code> if it's a modifiable field, <code>false</code> otherwise.
     */
    public static boolean IsModifiableField(Field field)
    {
        return (field.getAnnotation(GameBoolField.class) != null)
                || (field.getAnnotation(GameIntField.class) != null )
                || (field.getAnnotation(GameDoubleField.class) != null)
                || (field.getAnnotation(GameStringField.class) != null);
    }

    /**
     * Gets the type of field which the given one is.
     * @param field The field to get the type of.
     * @return The field's type.
     * @throws GameValuesException if the field is not an annotated modifiable constant.
     */
    public static GameFieldType GetFieldType(Field field)
    {
        GameBoolField BoolField = field.getAnnotation(GameBoolField.class);
        if (BoolField != null)
        {
            return GameFieldType.BoolField;
        }

        GameIntField IntField = field.getAnnotation(GameIntField.class);
        if (IntField != null)
        {
            return GameFieldType.IntField;
        }

        GameDoubleField DoubleField = field.getAnnotation(GameDoubleField.class);
        if (DoubleField != null)
        {
            return GameFieldType.DoubleField;
        }

        GameStringField StringField = field.getAnnotation(GameStringField.class);
        if (StringField != null)
        {
            return GameFieldType.StringField;
        }
        throw new GameValuesException("Invalid field \"%s\"".formatted(field.toString()));
    }


    /**
     * Copies the values of modifiable fields from the given source into this object's modifiable fields.
     * @param source The source of the values to copy from.
     * @throws NullPointerException if <code>source</code> is <code>null</code>.
     * @throws GameValuesException if an error occurs copying the values.
     */
    // Methods.
    public void CopyValuesFrom(GameInstanceValues source)
    {
        Objects.requireNonNull(source, "source is null");
        for (Field TargetField : GetModifiableFields())
        {
            try
            {
                TargetField.set(this, TargetField.get(source));
            }
            catch (IllegalAccessException e)
            {
                throw new GameValuesException("Failed to copy values from source: %s"
                        .formatted(PCString.ExceptionToString(e)));
            }
        }
    }

    /**
     * Resets all values for this object to their defaults.
     * @throws GameValuesException If an error occurs while resetting the values.
     */
    public void Reset()
    {
        CopyValuesFrom(new GameInstanceValues());
    }

    /**
     * Gets the currently set properties for a modifiable field for this object.
     * @param field The field whose value and properties to read.
     * @return The field's properties.
     * @throws NullPointerException if <code>field</code> is <code>null</code>.
     * @throws GameValuesException if the given field is not a valid modifiable field or an error occurs
     * getting the properties of the field.
     */
    public GameFieldProperties GetProperties(Field field)
    {
        Objects.requireNonNull(field, "field is null");
        try
        {
            GameBoolField BoolField = field.getAnnotation(GameBoolField.class);
            if (BoolField != null)
            {
                return new GameFieldProperties(field.get(this).toString(), null, null, BoolField.Description());
            }

            GameIntField IntField = field.getAnnotation(GameIntField.class);
            if (IntField != null)
            {
                return new GameFieldProperties(field.get(this).toString(),
                        Integer.toString(IntField.MinValue()),
                        Integer.toString(IntField.MaxValue()),
                        IntField.Description());
            }

            GameDoubleField DoubleField = field.getAnnotation(GameDoubleField.class);
            if (DoubleField != null)
            {
                return new GameFieldProperties(field.get(this).toString(),
                        Double.toString(DoubleField.MinValue()),
                        Double.toString(DoubleField.MaxValue()),
                        DoubleField.Description());
            }

            GameStringField StringField = field.getAnnotation(GameStringField.class);
            if (StringField != null)
            {
                return new GameFieldProperties(field.get(this).toString(), null, null, StringField.Description());
            }
        }
        catch (IllegalAccessException e)
        {
            throw new GameValuesException("Failed to get field properties: %s"
                    .formatted(PCString.ExceptionToString(e)));
        }
        throw new GameValuesException("Invalid field \"%s\"".formatted(field.toString()));
    }

    /**
     * Sets the value of the given field for this object.
     * @param field The field whose value to change.
     * @param value The new value of the field.
     * @throws NullPointerException if <code>field</code> is <code>null</code>.
     * @throws GameValuesException if <code>field</code> an error occurs while setting the field.
     */
    public void SetField(Field field, Object value)
    {
        Objects.requireNonNull(field, "field is null");
        try
        {
            GameFieldType FieldType = GetFieldType(field);
            if (FieldType == GameFieldType.DoubleField)
            {
                SetDoubleField(field, value);
            }
            else if (FieldType == GameFieldType.IntField)
            {
                SetIntField(field, value);
            }
            else
            {
                field.set(this, value);
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | ClassCastException e)
        {
            throw new GameValuesException("Failed to set field \"%s\": %s"
                    .formatted(field.getName(), PCString.ExceptionToString(e)));
        }
    }


    // Private methods.
    private void SetDoubleField(Field field, Object value) throws IllegalAccessException
    {
        GameDoubleField AnnotationValue = field.getAnnotation(GameDoubleField.class);
        double DoubleValue = ((Double)value);
        field.set(this, Math.max(AnnotationValue.MinValue(), Math.min(DoubleValue, AnnotationValue.MaxValue())));
    }

    private void SetIntField(Field field, Object value) throws IllegalAccessException
    {
        GameIntField AnnotationValue = field.getAnnotation(GameIntField.class);
        int IntValue = ((Integer)value);
        field.set(this, Math.max(AnnotationValue.MinValue(), Math.min(IntValue, AnnotationValue.MaxValue())));
    }
}