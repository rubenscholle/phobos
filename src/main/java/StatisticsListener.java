import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsListener extends ListenerAdapter {

    private static final String PREFIX = "!";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] commandMessage;
        int reportCount;
        int reactionCount;
        ResultSet result;
        List<String> timeDifferences = new ArrayList<>();
        String currentReportTime;
        String currentReactionTime;
        String currentTimeDifference;
        String averageTime;
        EmbedBuilder statisticsFeed = new EmbedBuilder();

        // Bots are not allowed to make this command
        if (event.getAuthor().isBot()) {
            return;
        }

        commandMessage = event.getMessage().getContentRaw().split(" ");

        if (commandMessage[0].equalsIgnoreCase(PREFIX + "statistik")) {

            try {
                SQLParser.connect();
                result = SQLParser.select("report_count");
                result.next();
                reportCount = result.getInt("count");
                result.close();

                result = SQLParser.select("reaction_count");
                result.next();
                reactionCount = result.getInt("count");
                result.close();

                result = SQLParser.select("messages");
                while (result.next()) {

                    if (result.getString("reaction_time") != null) {

                        currentReportTime = result.getString("report_time");
                        currentReactionTime = result.getString("reaction_time");
                        currentTimeDifference =
                                CurrentDateTime.getTimeDifference(currentReportTime, currentReactionTime);

                        timeDifferences.add(currentTimeDifference);



                    }

                }

                SQLParser.disconnect();

                averageTime = CurrentDateTime.getAverageTime(timeDifferences);

                statisticsFeed.setTitle("Zeit für etwas Statistik");
                statisticsFeed.setDescription(
                        "Anzahl Meldungen: **" + reportCount + "**\n" +
                                "Anzahl Reaktionen: **" + reactionCount + "**\n" +
                                "Durchschnittliche Reaktionszeit: **" + averageTime + "**");

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(statisticsFeed.build()).queue();
                statisticsFeed.clear();

            } catch(SQLException e) {
                e.printStackTrace();
            }


        }
    }
}
