package sus.keiger.mlgpvp.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import sus.keiger.mlgpvp.game.IGameSessionExecutor;
import sus.keiger.mlgpvp.service.IServerServices;
import sus.keiger.plugincommon.command.ServerCommand;

import java.nio.Buffer;

public class CommandInitializer
{
    // Methods.
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