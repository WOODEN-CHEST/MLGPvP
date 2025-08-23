package sus.keiger.mlgpvp.game;

import sus.keiger.plugincommon.PCMath;

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
    public double BorderDiameterMax = 200d;

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
    public int TotemCount = 4;

    @GameIntField(Description = "The amount of water buckets granted to each player.",
            MinValue = 0, MaxValue = 16)
    public int WaterBucketCount = 2;

    @GameIntField(Description = "The amount of full armor sets granted to each player.",
            MinValue = 0, MaxValue = 4)
    public int ArmorSetCount = 2;

    @GameIntField(Description = "The amount of golden apples granted to each player.",
            MinValue = 0, MaxValue = 128)
    public int GoldenAppleCount = 16;

    @GameIntField(Description = "The amount of arrows granted to each player.",
            MinValue = 0, MaxValue = 512)
    public int ArrowCount = 64;


    /* Explosions */
    @GameIntField(Description = "The scale of explosion knockback.",
            MinValue = 0, MaxValue = 16)
    public double ExplosionKnockbackScale = 1d;

    @GameIntField(Description = "The scale of explosion damage to players' health.",
            MinValue = 0, MaxValue = 16)
    public double ExplosionDamageScale = 1d;

    @GameIntField(Description = "The scale of explosion block destruction radius.",
            MinValue = 0, MaxValue = 16)
    public double ExplosionBlockDamageScale = 1d;

    @GameBoolField(Description = "Whether directly hitting a player with an arrow causes an explosion.")
    public boolean ArrowsExplodeOnDirectImpact = false;


    // Methods.
}