package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextChannel extends Command {

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "textchannel [add:remove:list] <text channel ID>";
    private final String DESCRIPTION = "Add or Remove Minecraft Binded text channels";

    public TextChannel(Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if (!hasPermission(e.getAuthor().getId()))
            if (!e.getAuthor().getId().equals(instance.getBotConfig().OWNER_ID))
                return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        if (args.length == 1)
            if (!args[0].equalsIgnoreCase("list")) {
                MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), invalidArgsMessage());
                return;
            }
        if (args.length == 0 || args == null || args.length > 2) {
            MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), invalidArgsMessage());
            return;
        }
        switch (args[0]) {
            case "add" :
                addTextChannel(args[1], e);
                break;
            case "remove" :
                removeTextChannel(args[1], e);
                break;
            case "list" :
                MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), listOfBindedChannels());
                break;
            default :
                MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), invalidArgsMessage());
                break;
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (!success)
            e.getTextChannel().sendMessage(noPermMessage()).queue();
    }

    private void addTextChannel(String textChannelID, MessageReceivedEvent e) {
        List<String> tc = (List<String>) instance.getPluginFile().getConfig().getList("chat_channel");
        net.dv8tion.jda.core.entities.TextChannel c = instance.getJda().getTextChannelById(textChannelID);
        if (c != null) {
            tc.add(textChannelID);
            instance.getPluginFile().getConfig().set("chat_channel", tc);
        } else {
            MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), "Text channel `" + textChannelID + "` is not a valid text channel.");
            return;
        }
        try {
            instance.getPluginFile().save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        instance.getBotConfig().reload();
        MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), "Added text channel `" + textChannelID + "` to the binded channels.");
        instance.getMCBLogger().info("Added text channel " + textChannelID);
    }

    private void removeTextChannel(String textChannelID, MessageReceivedEvent e) {
        List<String> tc = (List<String>) instance.getPluginFile().getConfig().getList("chat_channel");
        if (!containsID(textChannelID)) {
            MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), "Text channel `" + textChannelID + "` is not binded to Minecraft");
            return;
        }
        tc.remove(textChannelID);
        instance.getPluginFile().getConfig().set("chat_channel", tc);
        try {
            instance.getPluginFile().save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        instance.getBotConfig().reload();
        MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), "Removed text channel `" + textChannelID + "` from the binded channels.");
        instance.getMCBLogger().info("Removed text channel " + textChannelID);
    }

    private boolean containsID(String textChannelID) {
        ArrayList<String> ids = Main.getInstance().getBotConfig().BINDED_CHANNELS;
        for (String s : ids)
            if (s.equalsIgnoreCase(textChannelID))
                return true;
        return false;
    }

    private String listOfBindedChannels() {
        Main instance = Main.getInstance();
        String out = "```css\n Binded Channels\n";
        if(instance.getBotConfig().BINDED_CHANNELS.size() == 0) {
            out = "```css\nThere are no channels binded to minecraft\n```";
            return out;
        } else
        for (String s : instance.getBotConfig().BINDED_CHANNELS) {
            String channelName = instance.getJda().getTextChannelById(s).getName();
            String serverName = instance.getJda().getTextChannelById(s).getGuild().getName();
            out += "\t Text Channel: " + s + "\n" + "\t\t - Guild: " + serverName + "\n"
                    + "\t\t - Channel Name: " + channelName + "\n";
        }
        out += "```";
        return out;
    }
}
