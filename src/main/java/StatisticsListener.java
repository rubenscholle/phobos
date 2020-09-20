import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatisticsListener extends ListenerAdapter {

    private static final String PREFIX = "!";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] commandMessage;
        int reportCount;
        int reactionCount;
        ResultSet result;
        List<Long> timeDifferences = new ArrayList<>();
        String currentReportTime;
        String currentReactionTime;
        long currentTimeDifference;
        String averageTime;
        EmbedBuilder statisticsFeed = new EmbedBuilder();
        double reactionRatio;
        HashMap<String, Integer> groupedReactions = new HashMap<>();
        HashMap<String, Integer> groupedReactionRatios = new HashMap<>();
        int currentItem;
        Set<String> groupedReactionKeys;
        double currentRatio;
        int currentValue;

        // Bots are not allowed to make this command
        if (event.getAuthor().isBot()) return;

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

                try {
                    reactionRatio = 100*((double) reactionCount / (double) reportCount);
                } catch (ArithmeticException e) {
                    e.printStackTrace();
                    reactionRatio = 0.0;
                }

                averageTime = CurrentDateTime.getAverageTime(timeDifferences);

                // Initialize groupedTimes HashMap with empty Lists
                groupedReactions.put("<5 min", 0);
                groupedReactions.put("<15 min", 0);
                groupedReactions.put("<30 min", 0);
                groupedReactions.put("<60 min", 0);
                groupedReactions.put(">60 min", 0);

                //ToDo work with fall through switch
                for (long time : timeDifferences) {
                    if (time < 300000) {
                        currentItem = groupedReactions.get("<5 min");
                        currentItem++;
                        groupedReactions.put("<5 min", currentItem);
                    } else if (time < 900000) {
                        currentItem = groupedReactions.get("<15 min");
                        currentItem++;
                        groupedReactions.put("<15 min", currentItem);
                    } else if (time < 1800000) {
                        currentItem = groupedReactions.get("<30 min");
                        currentItem++;
                        groupedReactions.put("<30 min", currentItem);
                    } else if (time < 3600000) {
                        currentItem = groupedReactions.get("<60 min");
                        currentItem++;
                        groupedReactions.put("<60 min", currentItem);
                    } else {
                        currentItem = groupedReactions.get(">60 min");
                        currentItem++;
                        groupedReactions.put(">60 min", currentItem);
                    }
                }

                groupedReactionKeys = groupedReactions.keySet();
                for (String key : groupedReactionKeys) {
                    currentValue = groupedReactions.get(key);
                    if (currentValue != 0) {
                        currentRatio = 100*((double) currentValue / (double) reactionCount);
                        groupedReactionRatios.put(key, (int) currentRatio);
                    } else {
                        groupedReactionRatios.put(key, 0);
                    }
                }

                statisticsFeed.setTitle("Zeit für etwas Statistik");
                statisticsFeed.setDescription(
                        "Anzahl Meldungen: **" + reportCount + "**\n" +
                                "Anzahl Reaktionen: **" + reactionCount +
                                " (" + (int) reactionRatio + "%)**\n\n" +
                                "Reaktionen <5 min: **" + groupedReactions.get("<5 min") +
                                " (" + groupedReactionRatios.get("<5 min") + "%)**\n" +
                                "Reaktionen <15 min: **" + groupedReactions.get("<15 min") +
                                " (" + groupedReactionRatios.get("<15 min") + "%)**\n" +
                                "Reaktionen <30 min: **" + groupedReactions.get("<30 min") +
                                " (" + groupedReactionRatios.get("<30 min") + "%)**\n" +
                                "Reaktionen <60 min: **" + groupedReactions.get("<60 min") +
                                " (" + groupedReactionRatios.get("<60 min") + "%)**\n" +
                                "Reaktionen >60 min: **" + groupedReactions.get(">60 min") +
                                " (" + groupedReactionRatios.get(">60 min") + "%)**\n\n" +
                                "Durchschnittliche Reaktionszeit (mm:ss): **" + averageTime + "**");

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(statisticsFeed.build()).queue();
                statisticsFeed.clear();

            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
