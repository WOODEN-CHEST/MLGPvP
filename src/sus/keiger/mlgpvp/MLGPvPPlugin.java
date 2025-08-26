package sus.keiger.mlgpvp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sus.keiger.plugincommon.PCString;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MLGPvPPlugin extends JavaPlugin
{
    // Private fields.
    private final ServerContext _context;


    // Constructors.
    public MLGPvPPlugin()
    {
        _context = new ServerContext(this);
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

    public static NumberFormat GetFormat(String format)
    {
        return new DecimalFormat(format, DecimalFormatSymbols.getInstance(GetLocale()));
    }


    // Inherited methods.
    @Override
    public void onEnable()
    {
        super.onEnable();

        try
        {
            _context.Initialize();
        }
        catch (ServerContextException e)
        {
            getLogger().severe("Failed to initialize MLGPvP Plugin: %s"
                    .formatted(PCString.ExceptionToString(e)));
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable()
    {
        super.onDisable();

        try
        {
            _context.Deinitialize();
        }
        catch (ServerContextException e)
        {
            getLogger().severe("Failed to deinitialize MLGPvP Plugin: %s"
                    .formatted(PCString.ExceptionToString(e)));
        }
    }
}