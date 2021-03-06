# CHANGELOG

## 0.3.1 (2016-12-6)

**Feature**
  
  * Added Prefix command.
  * Better Invalid arguments message.

**Bug fix**

  * fixed Dcmd command output in discord.
  * fixed math evaluator.
  * fixed NullPointerExceptions when joining game.
  * fixed command's help arguments.
  
## 0.3.0 (2016-11-29)

**Feature**
  
  * Spigot, Bukkit, etc. 1.11 support.
  * New config file auto update feature.
  * Better /mcmd command. It now returns minecraft command output text to discord.
  * Support for new Discord versions.(Reactions to be implemented)
  * New API Version.
  * config files now have versions.
  * You can now toggle event broadcast for each event in the config file.
  * New minecraft permission minecordbot.incognito.* (must be enabled in config)

**Code**

  * Almost all classes refactored for the new version of JDA support.
  * Added new command sender type (for mcmd command).
  * More function in BotConfig class.

## 0.2.7 (2016-10-29)

**Feature**
  
  * Added mutli binded text channel support.
  * Added setexecutor command.
  * Added textchannel commands.
  * Added invite command.

**Code**

  * Major code refactoring.
  * Added MessengerUtil.
  
**Bug fix**
  
  * Fixed discord and minecraft reload command.
  
## 0.2.6 (2016-10-15)

**Feature**
  
  * Better ~!ls update function.
  * Removed last argument for ~!ls, it no longer needs an update interval.
  
**Bug fix**
  
  * Fixed the updating list... it was crap..
  * Fixed info showing wrong textchannel counts in the server.

## 0.2.5 (2016-10-12)

**Feature**

  * Math Listener is better now..
  * Even better typing indicators.
  * New ~!ls features.
      * You can now decide if the ls message should be updated regularly.
  * Removed the useless command "syntest".
  
**Code**
  * Command class now extends Messenger class.
  * Trash syntest command.
  
**Bug fix**
  
  * Fixed the ~!mcmd No permission but. Even the command was passed, it still said No permission.
  * Fixed the slow typing speed...
  
## 0.2.4 (2016-10-5)

**Feature**

  * Better math evaluation. It can do basic trig functions and also exponents (ie. sqrt(6^2 + 2 * 10))
  * Added shutdown command.
  * Better typing indicators
  * Better Ping response, it now shows the time.
  
**Bug fix**
  
  * Fixed problem on detecting if a message is an equation and resorted into parsing.
  
## 0.2.3 (2016-10-2)

**Feature**

  * It can do simple math for you now.

**Code**

  * Moved some classes to utils

**Bug fix**
  
  * Fixed messenger that caused it to stop relaying messages from Discord to Minecraft.

## 0.2.2 (2016-9-4)

**Feature**

  * Now do Minecraft commands from discord.
  * New info command for the bot.

**Code**

  * Better Messenger class.
  * Moved Minecraft listener and Discord listener to messenger class.

**Bug fix**
  
  * fixed commands having less information with their usage.

## 0.2.1 (2016-8-24)

**Feature**

  * Even better help command.
  * You can now see the bot typing.

**Code**

  * Added Messenger class
  * Refactored all usage info for every command.

**Bug fix**
  
  * Minecraft /dme permission fix. 

## 0.2.0.1 (2016-8-16)

**Bug fix**
  
  * Hopefully fixed bot config reload.

## 0.2.0 (2016-8-16)

**Feature**

  * Added SetNick command.
  * Added SetStreaming command.
  * Added SetUsername command.
  * Better help command. You can now specify what command do you need help with.

**Code**

  * New instances for command to support the updated version of the help command.
  
**Bug fix**
  
  * Fixed Discord prefix problem, where format codes gets carried over to a different section of the prefix.


## 0.1.11 (2016-8-13)

**Feature**

  * Added Minecraft command to do Discord command.(command-ception).
  * Prefix that will be seen in Minecraft can now have bukkit conventional text formats. i.e &6&l&nTest.
  * Tested on CraftBukkit1.10 and Spigot1.10, and it seems to work.
  
**Bug fix**

  * Fixed prefix problem that makes all message from discord to have a prefix "null".

## 0.1.10 (2016-8-12)

**Feature**

  * Better discord help command.   
  * Added description for commands.

**Code**
  
  * Modified code style for all commands to support updated Command.java

## 0.1.9 (2016-8-12)

**Feature**
  
  * Use custom prefixes for messages.

**Bug fix**
  
  * Fixed permission for /dme command.

## 0.1.8 (2016-8-11)

**Feature**
  
  * Re-Branded, from CFABot to MineCordBot. "CFA" is a personal thing

## 0.1.7 (2016-8-11)

**Feature**
  
  * Added reload BotConfig.yml command from both Discord and Minecraft.
  * Syntax highlighting test for a couple of language.
  * Added help command.
  * Added colors for some of the message blocks.

**Code**
  
  * Changed Command.java from interface to and abstract class.
  * Added hasPermission() method to Command.java.

**Bug fix**
  
  * Fixed problem with permissions.
  * Fixed a couple of message formats.
 
_Disclaimer: not all of the versions will have a changelog, 
specialy if the update is very minor._
