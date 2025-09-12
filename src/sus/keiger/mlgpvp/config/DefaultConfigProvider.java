package sus.keiger.mlgpvp.config;

import sus.keiger.mlgpvp.game.GameInstanceValues;

import java.util.*;

public class DefaultConfigProvider
{
    // Private fields.
    private final Map<String, GameInstanceValues> _configs = new HashMap<>();


    // Constructors.
    public DefaultConfigProvider()
    {
        InitConfigs();
    }


    // Private methods.
    private void InitConfigs()
    {
        _configs.clear();
        _configs.put("Chaos", GetChaosConfig());
        _configs.put("Hardcore", GetHardcoreConfig());
        _configs.put("MLGOnly", GetMLGOnlyConfig());
        _configs.put("Default", new GameInstanceValues());
        _configs.put("VariableMLGMethod", GetVariableMLGMethodsConfig());
    }

    private GameInstanceValues GetChaosConfig()
    {
        GameInstanceValues Config = new GameInstanceValues();

        Config.ExplosionKnockbackScale = 2;
        Config.ExplosionDamageScale = 1.5;
        Config.ExplosionBlockDamageScale = 2;
        Config.BorderDiameterMax = 400;
        Config.EnderPearlCount = 16;
        Config.ArrowCount = 64;
        Config.FeatherFallingLevel = 3;
        Config.ChorusFruitCount = 2;
        Config.ArrowSpeedMultiplier = 1.5d;
        Config.PlayerBlockReach = 6;
        Config.PlayerGravity = 0.12;
        Config.PlayerJumpStrength = 0.55;
        Config.PlayerMaxHealth = 40;
        Config.TotemCount = 2;
        Config.BowExplosionPower = 2.25;
        Config.PlayerBlockReach = 5.5;

        return Config;
    }

    private GameInstanceValues GetHardcoreConfig()
    {
        GameInstanceValues Config = new GameInstanceValues();

        Config.ArrowCount = 8;
        Config.FeatherFallingLevel = 0;
        Config.GoldenAppleCount = 4;
        Config.TotemCount = 0;
        Config.ArmorProtectionLevel = 0;
        Config.ArrowSpeedMultiplier = 0.9;
        Config.IsEnderPearlsEnabled = false;
        Config.IsChorusFruitEnabled = false;
        Config.BorderDiameterMax = 200;
        Config.ExplosionDamageScale = 1.25d;

        return Config;
    }

    private GameInstanceValues GetMLGOnlyConfig()
    {
        GameInstanceValues Config = new GameInstanceValues();

        Config.IsArrowDamageEnabled = false;
        Config.IsExplosionDamageEnabled = false;
        Config.IsMeleeDamageEnabled = false;
        Config.TotemCount = 1;
        Config.IsSwordIncluded = false;
        Config.IsAxeIncluded = false;
        Config.IsShovelIncluded = false;
        Config.IsChorusFruitEnabled = false;
        Config.IsEnderPearlsEnabled = false;
        Config.FeatherFallingLevel = 0;
        Config.ArmorProtectionLevel = 0;
        Config.BorderShrinkStartTimeSeconds = 60 * 2.5d;
        Config.MaxGameDurationSeconds = 60 * 7.5;

        return Config;
    }

    private GameInstanceValues GetVariableMLGMethodsConfig()
    {
        GameInstanceValues Config = new GameInstanceValues();

        Config.WaterBucketCount = 0;
        Config.ScaffoldingCount = 12;
        Config.SweetBerryCount = 12;
        Config.LadderCount = 12;
        Config.VineCount = 12;
        Config.TwistingVineCount = 12;
        Config.CobwebCount = 12;
        Config.SlimeBlockCount = 12;
        Config.TotemCount = 3;
        Config.EnderPearlCount = 1;
        Config.ChorusFruitCount = 1;

        return Config;
    }


    // Methods.
    public Optional<GameInstanceValues> GetConfig(String name)
    {
        return Optional.ofNullable(_configs.get(Objects.requireNonNull(name, "name is null")));
    }

    public List<String> GetConfigs()
    {
        return List.copyOf(_configs.keySet());
    }
}