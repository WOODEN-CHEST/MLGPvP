package sus.keiger.mlgpvp;

import org.bukkit.plugin.java.JavaPlugin;
import sus.keiger.plugincommon.player.IBasicAudience;

import java.util.Locale;

public class MLGPvPPlugin extends JavaPlugin
{
    // Private fields.



    // Constructors.
    public MLGPvPPlugin()
    {

    }


    // Static methods.
    public static String GetNamespace()
    {
        return "mlgpvp";
    }

    public static Locale GetLocale()
    {
        return Locale.ROOT;
    }


    // Inherited methods.
    @Override
    public void onEnable()
    {
        super.onEnable();
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }
}