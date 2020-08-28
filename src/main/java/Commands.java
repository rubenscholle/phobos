import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    public static String PREFIX = "!";
    public static int nMeldungen, nReaktionen;


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        String[] arguments = event.getMessage().getContentRaw().split(" ");

        // if (event.isFromType(ChannelType.TEXT)) to restrict kind of channel in which message was received
        if (arguments[0].equalsIgnoreCase(PREFIX + "hi")) {
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Hi " + event.getMember().getUser().getName() +", wie geht es dir?").queue();
        } else if (arguments[0].equalsIgnoreCase(PREFIX + "news")) {

            EmbedBuilder infoFeed = new EmbedBuilder();
            infoFeed.setTitle("Nachricht des Tages:");
            infoFeed.setDescription("Alle lieben ck1");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(infoFeed.build()).queue();
            infoFeed.clear();
        } else if (arguments[0].equalsIgnoreCase(PREFIX + "info")) {

            EmbedBuilder infoFeed = new EmbedBuilder();
            infoFeed.setTitle("Dies ist Rubies Testserver");
            infoFeed.setDescription("Hier testen wir mich, den Phobos-Bot");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(infoFeed.build()).queue();
            infoFeed.clear();
        } else if (arguments[0].equalsIgnoreCase(PREFIX + "melde")) {

            if (arguments.length >= 4) {

                if (!arguments[1].equalsIgnoreCase("server1")
                    && !arguments[1].equalsIgnoreCase("server2")) {

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Ruhig Cowboy!\n" +
                            "bitte verwende folgendes Schema:\n" +
                            "`!melde Server1 SPIELER VERGEHEN`").queue();
                    return;
                }

                String message = "";

                nMeldungen += 1;

                for (int i = 3; i < arguments.length; i++) {
                    message = message.join(" ", message, arguments[i]);
                }

                EmbedBuilder infoFeed = new EmbedBuilder();
                infoFeed.setTitle("Spielermeldung:");
                infoFeed.setDescription("Server: **"+arguments[1]+"**\n" +
                        "Spieler: **"+arguments[2]+"**\n" +
                        "Vergehen: **"+message+"**");

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(infoFeed.build()).queue();
                infoFeed.clear();
            } else {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Ruhig Cowboy!\n" +
                        "bitte verwende folgendes Schema:\n" +
                        "`!melde Server1 SPIELER VERGEHEN`").queue();
            }
        } else if (arguments[0].equalsIgnoreCase(PREFIX + "statistik")) {

            EmbedBuilder statistikFeed = new EmbedBuilder();
            statistikFeed.setTitle("Zeit für etwas Statistik, wooot!:");
            statistikFeed.setDescription("Anzahl Meldungen: **"+nMeldungen+"**\n" +
                    "Anzahl Reaktionen: **"+nReaktionen+"**\n" +
                    "Das sind: **"+((nReaktionen/nMeldungen)*100)+" Prozent**");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(statistikFeed.build()).queue();
            statistikFeed.clear();
        }

    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        if (event.getReactionEmote().getName().equals("🚨")) {
            nReaktionen += 1;
        }
    }
}
