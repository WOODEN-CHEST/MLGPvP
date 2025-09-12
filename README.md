MLGPvP is a dumb gamemode where the main goal is to not fail any MLG water buckets (or ladders, vines, anything else you can MLG on).

The players are given explosive bows and crossbows which deal massive knockback, launching the players up to 200 blocks in the air.
They then have to try to MLG their way down and not die!

This plugin requires PaperMC.


# Plugin usage.
After downloading and placing the plugin into the server's plugin directory, you can start using it.

All plugin related things are controlled with the /mlgpvp command. Admin privileges (OP) is required to run this command.

Matches can be started with `/mlgpvp start` and stopped with `/mlgpvp cancel`. **Only 1 match may be active at a time.**
Matches will start in the location where you execute the command, so you can pick a nice spot to play.

When a match is started, all online server players are placed into it as participants. Players who join the server while a match is active are set as spectators.

## Changing the settings.
The plugin offers ways to change the settings of a game. While they technically *can* be changed mid-match, most of them will only have an effect if they are changed before you start the match.

Settings can be changed with the `mlgpvp setting` command.

Typing `mlgpvp setting` will display all of your plugin's settings into the executor's chat.

Individual setting values can be previewed by typing `mlgpvp setting <setting_name>`

You can change a setting by typing `mlgpvp setting <setting_name> <setting_value>`. The tab auto-complete will suggest the default value for this setting, but you can put any value you want in there.
Note that for numbers, the values will be clamped to a range.

You can use `mlgpvp reset_settings` to reset all settings to their defaults.

## Managing whole configurations.
Changing settings individually can be long and tedious, and if you find a setting combination you like, it'd be annoying to have to reconfigure the plugin each time you restart the server.
To solve this, there exist **configurations**.
A config simply holds a snapshot of the plugin's settings. You can load, save and delete configurations with this plugin.

To save your current settings into a confugration, type `mlgpvp config save "<config_name>"`.

To load a configuration, type `mlgpvp config load "<config_name>"`.

Similarly, to delete a config, just type `mlgpvp config delete "<config_name>"`.

To list your available configurations, use `mlgpvp config list`.

The plugin comes with a few built-in configurations which you can use. Those cannot be deleted, and you cannot save a custom config with the same name as a built-in config.

The configurations are saved in <server_directory>/plugins/MLGPvP/configs/<config_name>.json. You *can* manually create and modify the configs since they are just JSON files, but you
risk corrupting them if you're not careful, so I advise to not do that.
If you do deicde to manually tamper with configs, there exists a `mlgpvp config refresh` command which refreshes the list of configurations
(for instance, if you add a config manually, it won't show up until you type this command).


# Building the plugin.
To build the plugin, you'll firstly need to not only include all of PaperMC's libraries, but also include the library [PluginCommon](https://github.com/WOODEN-CHEST/PluginCommon).
You'll need to include the extracted PluginCommon library in the resulting MLGPvP archive as well for the plugin to work.


# Other notes
This plugin was coded by an idiot, so it likely has bugs. Nothing major was found while testing, but if you find something, please don't be afraid to open an issue,
I'll try to fix it as soon as I can!
