package sus.keiger.mlgpvp.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.game.IGameSessionExecutor;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.command.ServerCommand;

/**
 * Class to initialise all commands used by the MLGPvP plugin.
 */
public class CommandInitializer
{
    // Methods.

    /**
     * Initialises MLGPvP's required commands.
     * @param plugin The MLGPvP plugin, currently unused but may be used in the future.
     * @param services The server's services.
     * @param sessionExecutor The game session executor which the commands will act on.
     */
    public void InitializeCommands(Plugin plugin, IServerServices services, IGameSessionExecutor sessionExecutor)
    {
        InitOneCommand(MLGPvPCommand.LABEL, MLGPvPCommand.CreteCommand(sessionExecutor, services));
    }


    // Private methods.
    private void InitOneCommand(String name, ServerCommand command)
    {
         PluginCommand TargetCommand = Bukkit.getPluginCommand(name);
         if (TargetCommand == null)
         {
             throw new CommandNotFoundException("No command found with name \"%s\"".formatted(name));
         }

         TargetCommand.setExecutor(command);
         TargetCommand.setTabCompleter(command);
    }
}