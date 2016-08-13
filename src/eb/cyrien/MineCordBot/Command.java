package eb.cyrien.MineCordBot;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public abstract class Command {

    private String usage;
    private String definition;

    public abstract boolean called(String[] args, MessageReceivedEvent e);

    public abstract void action(String[] args, MessageReceivedEvent e);

    public abstract void executed(boolean success, MessageReceivedEvent e);

    public String noPermMessage() {
        return "```css\n[You do not have permission]\n```";
    }

    public boolean hasPermission(String userID) {
        ArrayList<String> wl = Main.botConfig.WHITELIST;
        for (String s : wl) {
            if(s.equals(userID))
                return true;
        }
        return false;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setDescription(String definition) {
        this.definition = definition;
    }

    public String getUsage() {
        return usage;
    }
    public String getDefinition() {
        return definition;
    }


}
