import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportService extends ListenerAdapter {

    private static final String PREFIX = "!";
    private static int reportCount, reactionCount;
    private static boolean deleteMessage = false;
    private static SQLParser sql = new SQLParser();

    public ReportService(boolean deleteMessage) {
        setDeleteMessage(deleteMessage);
    }

    public static int getReportCount() {
        return reportCount;
    }

    public static void setReportCount(int reportCount) {
        ReportService.reportCount = reportCount;
    }

    public static int getReactionCount() {
        return reactionCount;
    }

    public static void setReactionCount(int reactionCount) {
        ReportService.reactionCount = reactionCount;
    }

    public static void incrementReportCount(int increments) {
        ReportService.reportCount += increments;
    }

    public static void incrementReactionCount(int increments) {
        ReportService.reactionCount += increments;
    }

    public static boolean isDeleteMessage() {
        return deleteMessage;
    }

    public static void setDeleteMessage(boolean deleteMessage) {
        ReportService.deleteMessage = deleteMessage;
    }

    public static int getReportRatio() {

        float reportRatio;

        if (ReportService.reportCount > 0) {
            // cast to float to allow numbers between 0 and 1
            reportRatio = (float) ReportService.reactionCount / ReportService.reportCount;
        } else {
            reportRatio = 0;
        }
        // convert into percentage and cast back into int
        return (int) (reportRatio * 100);
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        ResultSet result;

        if (event.getReactionEmote().getName().equals("🚨")) {
            try {
                sql.makeJDBCConnection();
                result = sql.select("reaction_count");
                result.next();
                setReactionCount(result.getInt("count"));
                result.close();

                incrementReactionCount(1);
                sql.update("reaction_count", 1, "count", getReactionCount());
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String reportingPlayer;
        String[] reportMessage;
        String warningMessage;
        String serverPart, playerPart, reportPart;
        ResultSet result;

        if (event.getAuthor().isBot()) {
            return;
        } else {
            reportingPlayer = event.getMember().getUser().getName();
        }

        reportMessage = event.getMessage().getContentRaw().split(" ");

        warningMessage =
                "Ruhig Cowboy!\n" +
                "bitte verwende folgendes Schema:\n" +
                "`!melde Server1 SPIELER VERGEHEN`";

        if (reportMessage[0].equalsIgnoreCase(PREFIX + "melde")) {

            if (reportMessage.length < 4) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(warningMessage).queue();
                return;
            }

            if (!reportMessage[1].equalsIgnoreCase("server1")
                    && !reportMessage[1].equalsIgnoreCase("server2")) {

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(warningMessage).queue();
                return;
            }

            if (deleteMessage) event.getChannel().deleteMessageById(event.getMessageId()).queue();


            try {
                sql.makeJDBCConnection();
                result = sql.select("report_count");
                result.next();
                setReportCount(result.getInt("count"));
                result.close();

                incrementReportCount(1);
                sql.update("report_count", 1, "count", getReportCount());
            } catch(SQLException e) {
                e.printStackTrace();
            }

            serverPart = reportMessage[1];
            playerPart = reportMessage[2];
            reportPart = "";

            for (int i = 3; i < reportMessage.length; i++) {
                reportPart = reportPart.join(" ", reportPart, reportMessage[i]);
            }

            EmbedBuilder reportFeed = new EmbedBuilder();
            reportFeed.setTitle("Spielermeldung durch @" + reportingPlayer + ":");
            reportFeed.setDescription(
                    "Server: **" + serverPart + "**\n" +
                    "Spieler: **" + playerPart + "**\n" +
                    "Vergehen: **" + reportPart + "**");
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(reportFeed.build()).queue();
            reportFeed.clear();
        }

        if (reportMessage[0].equalsIgnoreCase(PREFIX + "statistik")) {

            try {
                sql.makeJDBCConnection();
                result = sql.select("report_count");
                result.next();
                setReportCount(result.getInt("count"));
                result.close();

                result = sql.select("reaction_count");
                result.next();
                setReactionCount(result.getInt("count"));
                result.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }

            int reportRatio = getReportRatio();

            EmbedBuilder statisticsFeed = new EmbedBuilder();
            statisticsFeed.setTitle("Zeit für etwas Statistik, wooot!:");
            statisticsFeed.setDescription(
                    "Anzahl Meldungen: **"+getReportCount()+"**\n" +
                    "Anzahl Reaktionen: **"+getReactionCount()+"**\n" +
                    "Das sind: **"+reportRatio+" Prozent**");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(statisticsFeed.build()).queue();
            statisticsFeed.clear();
        }

        if (reportMessage[0].equalsIgnoreCase(PREFIX + "delete")) {
            event.getChannel().deleteMessageById(event.getMessageId());
        }
    }
}
