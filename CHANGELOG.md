# CHANGELOG

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
