package eb.cyrien.MineCordBot.entity;

import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import javax.management.RuntimeErrorException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class BasicMathSolver extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean notSelf = !event.getMessage().getAuthor().getId().equalsIgnoreCase(Main.botConfig.BOT_ID);
        if (notSelf) {
            double sol;
            boolean validEquation;
            try {
                sol = eval(event.getMessage().getContent());
                validEquation = true;
            } catch (Exception e) {
                sol = -1;
                validEquation = false;
            }
            if(validEquation)
                event.getTextChannel().sendMessage("`" + event.getMessage().getContent() + " = " + sol + "`");
        }
    }

    public static double eval(final String str) throws RuntimeException {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());

                return x;
            }
        }.parse();
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
        if (result.equalsIgnoreCase("syntax error"))
            e.getTextChannel().sendMessage(result);
        else
            e.getTextChannel().sendMessage("`" + equation + " = " + result + "`");
    }
}
