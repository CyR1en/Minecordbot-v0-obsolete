package eb.cyrien.MineCordBot;

import eb.cyrien.MineCordBot.entity.Messenger;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.util.ArrayList;

public abstract class Command extends Messenger {

    private String usage;
    private String definition;

    public Command(Main instance) {
        super(instance);
    }

    public abstract boolean called(String[] args, MessageReceivedEvent e);

    public abstract void action(String[] args, MessageReceivedEvent e);

    public abstract void executed(boolean success, MessageReceivedEvent e);

    public String noPermMessage() {
        return "```css\n[You do not have permission]\n```";
    }

    protected String invalidArgsMessage() {
        return "```\n[Invalid Argument(s)]\nUsage: " + usage + "\n```";
    }

    public String successMessage() {
        return ":white_check_mark:";
    }

    public boolean hasPermission(String userID) {
        ArrayList<String> wl = instance.getBotConfig().WHITELIST;
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
    public String getDescription() {
        return definition;
    }


}
