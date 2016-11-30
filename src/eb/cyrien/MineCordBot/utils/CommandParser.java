package eb.cyrien.MineCordBot.utils;

import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


import java.util.ArrayList;

public class CommandParser {

    public CommandContainer parse(String rw, MessageReceivedEvent e) {
        ArrayList<String> split = new ArrayList<>();
        String raw = rw;
        String beheaded = raw.replaceFirst(Main.getInstance().getBotConfig().COMMAND_EXECUTOR, "");
        String[] splitBeheaded = beheaded.split(" ");
        for (String s : splitBeheaded)
            split.add(s);
        String invoke = split.get(0).toLowerCase();
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);
        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, e);
    }

    public class CommandContainer {
        public final String raw;
        public final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        public final String[] args;
        public final MessageReceivedEvent event;

        public CommandContainer(String raw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent event) {
            this.raw = raw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = event;
        }
    }
}
