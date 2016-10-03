package eb.cyrien.MineCordBot.entity;

import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class BasicMathSolver extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean notSelf = !event.getMessage().getAuthor().getId().equalsIgnoreCase(Main.botConfig.BOT_ID);
        if(containsOperator(event.getMessage().toString()) && notSelf)
            solve(event.getMessage().getContent(), event);
    }

    private boolean containsOperator(String msg) {
        if(msg.contains("+"))
            return true;
        if(msg.contains("-"))
            return true;
        if(msg.contains("*"))
            return true;
        if(msg.contains("/"))
            return true;
        return false;
    }

    private void solve(String str, MessageReceivedEvent e) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String equation = str;
        String result;
        try {
            result = engine.eval(str).toString();
        } catch (ScriptException e1) {
            result = "`Syntax Error`";
            e1.printStackTrace();
        }
        if(result.equalsIgnoreCase("syntax error"))
            e.getTextChannel().sendMessage(result);
        else
            e.getTextChannel().sendMessage("`" + equation + " = " + result + "`");
    }
}
