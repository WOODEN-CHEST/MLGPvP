package sus.keiger.mlgpvp.game;

import sus.keiger.plugincommon.PCString;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @GameIntField(Description = "The amount of totems of undying granted to each player.",
        MinValue = 0, MaxValue = 16)
    public int TotemCount = 5;

    @GameIntField(Description = "The amount of water buckets granted to each player.",
            MinValue = 0, MaxValue = 16)
    public int WaterBucketCount = 2;

    @GameIntField(Description = "The amount of golden apples granted to each player.",
            MinValue = 0, MaxValue = 128)
    public int GoldenAppleCount = 12;

    @GameIntField(Description = "The amount of arrows granted to each player.",
            MinValue = 0, MaxValue = 512)
    public int ArrowCount = 24;

    @GameIntField(Description = "The amount of ender pearls granted to each player.",
            MinValue = 0, MaxValue = 64)
    public int EnderPearlCount = 0;

    @GameBoolField(Description = "Whether the players are given the explosive bow")
    public boolean IsExplosiveBowEnabled = true;

    @GameBoolField(Description = "Whether the players are given the explosive crossbow")
    public boolean IsExplosiveCrossbowEnabled = true;

    @GameIntField(Description = "The feather falling level applied to boots.",
            MinValue = 0, MaxValue = 10)
    public int FeatherFallingLevel = 2;


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
    public double CrossbowExplosionPower = 2.1d;

    @GameBoolField(Description = "Whether the climb height is reset when a player is affected by a smaller " +
            "explosion while still mid-air from a previous explosion. Set to false for a more casual experience.")
    public boolean IsClimbHeightReset = true;


    // Static methods.
    public static List<Field> GetModifiableFields()
    {
        return Arrays.stream(GameInstanceValues.class.getFields())
                .filter(GameInstanceValues::IsModifiableField)
                .toList();
    }

    public static boolean IsModifiableField(Field field)
    {
        return (field.getAnnotation(GameBoolField.class) != null)
                || (field.getAnnotation(GameIntField.class) != null )
                || (field.getAnnotation(GameDoubleField.class) != null)
                || (field.getAnnotation(GameStringField.class) != null);
    }

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

    public void Reset()
    {
        CopyValuesFrom(new GameInstanceValues());
    }

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

    public void SetField(Field field, Object value)
    {
        try
        {
            field.set(this, value);
        }
        catch (IllegalAccessException | IllegalArgumentException e)
        {
            throw new GameValuesException("Failed to set field \"%s\": %s"
                    .formatted(field.getName(), PCString.ExceptionToString(e)));
        }
    }
}