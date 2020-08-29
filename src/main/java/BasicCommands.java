import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BasicCommands extends ListenerAdapter {

    public static final String PREFIX = "!";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] commandArguments;

        if (event.getAuthor().isBot()) return;

        commandArguments = event.getMessage().getContentRaw().split(" ");

        // if (event.isFromType(ChannelType.TEXT)) to restrict kind of channel in which message was received
        if (commandArguments[0].equalsIgnoreCase(PREFIX + "hi")) {
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Hi " + event.getMember().getUser().getName() +", wie geht es dir?").queue();
        } else if (commandArguments[0].equalsIgnoreCase(PREFIX + "news")) {
            EmbedBuilder infoFeed = new EmbedBuilder();
            infoFeed.setTitle("Nachricht des Tages:");
            infoFeed.setDescription("Alle lieben ck1");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(infoFeed.build()).queue();
            infoFeed.clear();
        } else if (commandArguments[0].equalsIgnoreCase(PREFIX + "info")) {

            EmbedBuilder infoFeed = new EmbedBuilder();
            infoFeed.setTitle("Dies ist Rubies Testserver");
            infoFeed.setDescription("Hier testen wir mich, den Phobos-Bot");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(infoFeed.build()).queue();
            infoFeed.clear();
        }
    }
}
