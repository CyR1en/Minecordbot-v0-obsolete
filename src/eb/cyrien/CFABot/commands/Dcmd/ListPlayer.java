package eb.cyrien.CFABot.commands.Dcmd;

import eb.cyrien.CFABot.Command;
import eb.cyrien.CFABot.Main;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ListPlayer extends Command {
    public static final String HELP = "USAGE: " + Main.botConfig.COMMAND_EXECUTOR + "lp";

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        String blockFix = "```";
        TextChannel tc = e.getTextChannel();
        tc.sendMessage("Online Players in " + Bukkit.getServer().getName());
        int i = 1;
        String s = blockFix + "\n";
        if (Bukkit.getServer().getOnlinePlayers().size() == 0 || Bukkit.getServer().getOnlinePlayers() == null)
            s += "There are no players online";
        else
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                s += (i + ". " + p.getName() + "\n");
                i++;
            }
        s += blockFix;
        e.getTextChannel().sendMessage(s);
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
