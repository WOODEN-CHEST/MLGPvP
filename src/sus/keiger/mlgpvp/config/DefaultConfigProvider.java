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
        _configs.put("chaos", GetChaosConfig());
        _configs.put("casual", GetCasualConfig());
        _configs.put("hardcore", GetHardcoreConfig());
    }

    private GameInstanceValues GetChaosConfig()
    {
        GameInstanceValues Config = new GameInstanceValues();

        Config.ExplosionKnockbackScale = 2;
        Config.ExplosionDamageScale = 0.5;
        Config.ExplosionBlockDamageScale = 2;
        Config.BorderDiameterMax = 150;
        Config.EnderPearlCount = 4;
        Config.ArrowCount = 64;
        Config.FeatherFallingLevel = 3;

        return Config;
    }

    private GameInstanceValues GetCasualConfig()
    {
        GameInstanceValues Config = new GameInstanceValues();

        Config.BorderDiameterMax = 300;
        Config.EnderPearlCount = 8;
        Config.ArrowCount = 64;
        Config.FeatherFallingLevel = 4;
        Config.GoldenAppleCount = 32;
        Config.IsClimbHeightReset = false;
        Config.TotemCount = 7;
        Config.WaterBucketCount = 3;

        return Config;
    }

    private GameInstanceValues GetHardcoreConfig()
    {
        GameInstanceValues Config = new GameInstanceValues();

        Config.ArrowCount = 16;
        Config.FeatherFallingLevel = 0;
        Config.GoldenAppleCount = 8;
        Config.TotemCount = 0;

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