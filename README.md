# MineCordBot Bukkit
Bridge Minecraft and Discord using MineCordBot._Tested on CraftBukkit1.9.4_

current version : **MineCordBot_Alpha0.1.10**

## Feature
* Binds a channel to Minecraft. (This includes, death, join, disconnect, etc. events)
* Custom commands that applies to both Minecraft and Discord server.
* Do Discord bot commands from minecraft. (Still working on a feature to do it vice versa).
* Set your desired Discord command executor (Prefix to determine if a text is a command. i.e "~!", ~!sampleCommand).

## Commands
**Discord Commands** - Commands that you can do in discord.

`Usage: command_executor + command.`

`command executor = "~!" | "~!" + command`
 * ~!help - lists all the command you can do for the bot in discord.
 * ~!help [command] - better help for a specific command.
 * ~!setavatar [URL] - change the bot's avatar.
 * ~!setgame [game] - change what the bot's playing.
 * ~!ping - test if the bot is resposinve.
 * ~!reload - reload botConfig.yml.
 * ~!ls - lists online players in the server.
 * ~!syntest [text] - Test different syntax highlighting for a specific text.
 * _More commands to come_.

**Minecraft Commands**
 * /dme - it's just like /me but it's connected to discord.
 * /reload - reloads botConfig.yml.
 * _More commands to come_.

## Permission nodes
 * minecordbot.dme    | command: /dme
 * minecordbot.reload | command: /minecordbot reload

## Installation
**First of all** - Make a discord Bot
 * Go to https://discordapp.com/developers/docs/intro
 * MyApplications > login > New Application.
 * Complete the form. Once that's done, click "Create Application".
 * Click "Create a Bot User", and then click "Yes, do it!"
 * Now add your bot to your Discord Server. Remeber to Replace "[Place your bot's Client ID]" with your bot's client ID
 `https://discordapp.com/oauth2/authorize?client_id=[Place your bot's Client ID]&scope=bot&permissions=0`

**Install the plugin to your Minecraft Server**
 * Download MineCordBot.jar here https://dev.bukkit.org/bukkit-plugins/minecordbot-bukkit/
 * Put the jar file to your plugin folder in your minecraft server.
 * Start your server.
 * Configure your botConfig.yml
 * To easily get User ID and Channel ID. On Discord, go to settings > Appearance > enable developer mode. After that, just right click a user or a channel and then click "Copy ID" to get their ID.
 * Save your botConfig.yml.
 * Lastly restart your beautiful server.
 
## More Info
This is a project for my Minecraft Server. I use it so that players could interact
with everyone in-game or not.

The plugin is currently in a very early stage. So please don't hate when you see some bugs.
Also I'd say that i'm not the best at programming, since I don't have enough experience, and I've only studied computer science for a year.(I'm just 18 ;-;)

MineCordBot is also written in Java for easier CraftBukkit support.

Also I've only tested this on CrafBukkit1.9.4. But I will soon be testing newer version of CraftBukkit, even old versions.
Maybe add support to other versions of Minecraft servers, and other plugins like Multiverse, etc.

## Dependencies
  * JDA - [https://github.com/DV8FromTheWorld/JDA]
  * CraftBukkit [https://www.spigotmc.org/wiki/buildtools]

## To see the bot in Action
  * Join our Discord server: https://discord.gg/Dv7vbzJ
  * Join our Minecraft server: thewobros.mcph.co
