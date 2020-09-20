import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class ReportFeed {

    private static final EmbedBuilder reportFeed = new EmbedBuilder();

    // Singleton design pattern for class ReportFeed
    private ReportFeed() {}

    // Send an embedded player report message to the Bot
    public static void send(
            String reportingPlayer,
            String serverPart,
            String playerPart,
            String reportPart,
            TextChannel channel) {

        reportFeed.setTitle("Spielermeldung durch " + reportingPlayer + ":");
        reportFeed.setDescription(
                "Server: **" + serverPart + "**\n" +
                        "Spieler: **" + playerPart + "**\n" +
                        "Vergehen: **" + reportPart + "**");

        channel.sendTyping().queue();
        channel.sendMessage(reportFeed.build()).queue();
        reportFeed.clear();
    }
}
