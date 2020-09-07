import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class ReportFeed {

    private static final EmbedBuilder reportFeed = new EmbedBuilder();

    // Singleton design pattern for class ReportFeed
    private static final ReportFeed instance = new ReportFeed();

    private ReportFeed() {}

    public static ReportFeed getInstance() {

        return instance;
    }

    public static void send(
            String reportingPlayer,
            String serverPart,
            String playerPart,
            String reportPart,
            TextChannel channel) {

        reportFeed.setTitle("Spielermeldung durch @" + reportingPlayer + ":");
        reportFeed.setDescription(
                "Server: **" + serverPart + "**\n" +
                        "Spieler: **" + playerPart + "**\n" +
                        "Vergehen: **" + reportPart + "**");

        channel.sendTyping().queue();
        channel.sendMessage(reportFeed.build()).queue();
        reportFeed.clear();
    }
}
