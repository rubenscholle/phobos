import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportListener extends ListenerAdapter {

    private static final String PREFIX = "!";
    private static boolean deleteMessage = false;


    public ReportListener(boolean deleteMessage) {
        setDeleteMessage(deleteMessage);
    }

    public static boolean isDeleteMessage() {
        return deleteMessage;
    }

    public static void setDeleteMessage(boolean deleteMessage) {
        ReportListener.deleteMessage = deleteMessage;
    }

    private static void incrementReportCount() {
        ResultSet result;
        int reportCount;
        String queryStatement;

        try {
            SQLParser.connect();
            result = SQLParser.select("report_count");
            result.next();
            reportCount = result.getInt("count");
            result.close();

            reportCount++;
            queryStatement = "Update report_count SET count = "
                    + reportCount +
                    " WHERE id = 1;";

            SQLParser.update(queryStatement);
            SQLParser.disconnect();

            } catch(SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] reportMessage;
        String reportingPlayer;
        String warningMessage;
        String serverPart, playerPart, reportPart;

        // Bots are not allowed to report
        if (event.getAuthor().isBot()) {
            return;
        } else {
            try {
                reportingPlayer = event.getMember().getUser().getName();
            } catch (NullPointerException e) {
                e.printStackTrace();
                reportingPlayer = null;
            }
        }

        reportMessage = event.getMessage().getContentRaw().split(" ");

        warningMessage = "Ruhig Cowboy!\n" +
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

            serverPart = reportMessage[1];
            playerPart = reportMessage[2];
            reportPart = "";

            for (int i = 3; i < reportMessage.length; i++) {
                reportPart = reportPart.join(" ", reportPart, reportMessage[i]);
            }

            ReportFeed.send(reportingPlayer, serverPart, playerPart, reportPart, event.getChannel());
            incrementReportCount();
        }
    }
}