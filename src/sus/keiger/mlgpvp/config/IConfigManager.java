package sus.keiger.mlgpvp.config;

import sus.keiger.mlgpvp.game.GameInstanceValues;
import sus.keiger.plugincommon.ExplainedResult;

import java.util.List;

public interface IConfigManager
{
    GameInstanceValues LoadConfig(String name);
    ExplainedResult SaveConfig(String name, GameInstanceValues config);
    boolean DoesConfigExist(String name);
    ExplainedResult VerifyConfigName(String name);
    ExplainedResult DeleteConfig(String name);
    List<String> GetConfigs();
}