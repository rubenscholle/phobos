import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class TicTacToe extends ListenerAdapter {

    //private static User playerOne, playerTwo = null;
    public String hasTurn = "x";
    public boolean gameRunning = false;
    public HashMap<String, String> currentFields;
    public String gameMap;

    public String PREFIX = "!";

    public void drawMap(HashMap<String, String> currentFields) {
        gameMap = "`\n" +
                "\u250C\u2500\u2500\u2500\u252c\u2500\u2500\u2500\u252c\u2500\u2500\u2500\u2510\n" +
                "\u2502\u0020" + currentFields.get("1") +
                "\u0020\u2502\u0020" + currentFields.get("2") +
                "\u0020\u2502\u0020" + currentFields.get("3") +
                "\u0020\u2502\n" +
                "\u251c\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2524\n" +
                "\u2502\u0020" + currentFields.get("4") +
                "\u0020\u2502\u0020" + currentFields.get("5") +
                "\u0020\u2502\u0020" + currentFields.get("6") +
                "\u0020\u2502\n" +
                "\u251c\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2524\n" +
                "\u2502\u0020" + currentFields.get("7") +
                "\u0020\u2502\u0020" + currentFields.get("8") +
                "\u0020\u2502\u0020" + currentFields.get("9") +
                "\u0020\u2502\n" +
                "\u2514\u2500\u2500\u2500\u2534\u2500\u2500\u2500\u2534\u2500\u2500\u2500\u2518\n" +
                "`";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        String[] arguments = event.getMessage().getContentRaw().split(" ");

        // if (event.isFromType(ChannelType.TEXT)) to restrict kind of channel in which message was received
        if (arguments[0].equalsIgnoreCase(PREFIX + "tictactoe")
                && arguments[1].equalsIgnoreCase("new")) {

            if (gameRunning) {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Sorry, " + event.getMember().getAsMention() + ", es läuft bereits eine Runde").queue();
            } else {

                currentFields = new HashMap<String, String>();
                currentFields.put("1", "1");
                currentFields.put("2", "2");
                currentFields.put("3", "3");
                currentFields.put("4", "4");
                currentFields.put("5", "5");
                currentFields.put("6", "6");
                currentFields.put("7", "7");
                currentFields.put("8", "8");
                currentFields.put("9", "9");

                drawMap(currentFields);

                EmbedBuilder infoFeed = new EmbedBuilder();
                infoFeed.setTitle("Dies ist eine neue Runde TicTacToe");
                infoFeed.setDescription(gameMap);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(infoFeed.build()).queue();
                infoFeed.clear();
            }

        } else if (arguments[0].equalsIgnoreCase(PREFIX + "tictactoe")
                && (arguments[1].equalsIgnoreCase("x"))
                && currentFields.containsKey(arguments[2])) {

            currentFields.put(arguments[2], "x");

            drawMap(currentFields);

            EmbedBuilder infoFeed = new EmbedBuilder();
            infoFeed.setTitle("Dies ist eine neue Runde TicTacToe");
            infoFeed.setDescription(gameMap);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(infoFeed.build()).queue();
            infoFeed.clear();

        } else if (arguments[0].equalsIgnoreCase(PREFIX + "tictactoe")
                && (arguments[1].equalsIgnoreCase("o"))
                && currentFields.containsKey(arguments[2])) {

            currentFields.put(arguments[2], "o");

            drawMap(currentFields);

            EmbedBuilder infoFeed = new EmbedBuilder();
            infoFeed.setTitle("Dies ist eine neue Runde TicTacToe");
            infoFeed.setDescription(gameMap);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(infoFeed.build()).queue();
            infoFeed.clear();

        }
    }
}