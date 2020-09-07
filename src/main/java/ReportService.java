//import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
//import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class ReportService extends ListenerAdapter {
//
//
//
//
//
//    public static int getReportCount() {
//        return reportCount;
//    }
//
//    public static void setReportCount(int reportCount) {
//        ReportService.reportCount = reportCount;
//    }
//
//    public static int getReactionCount() {
//        return reactionCount;
//    }
//
//    public static void setReactionCount(int reactionCount) {
//        ReportService.reactionCount = reactionCount;
//    }
//
//    public static void incrementReportCount(int increments) {
//        ReportService.reportCount += increments;
//    }
//
//    public static void incrementReactionCount(int increments) {
//        ReportService.reactionCount += increments;
//    }
//
//    public static boolean isDeleteMessage() {
//        return deleteMessage;
//    }
//
//    public static void setDeleteMessage(boolean deleteMessage) {
//        ReportService.deleteMessage = deleteMessage;
//    }
//
//    public static int getReportRatio() {
//
//        float reportRatio;
//
//        if (ReportService.reportCount > 0) {
//            // cast to float to allow numbers between 0 and 1
//            reportRatio = (float) ReportService.reactionCount / ReportService.reportCount;
//        } else {
//            reportRatio = 0;
//        }
//        // convert into percentage and cast back into int
//        return (int) (reportRatio * 100);
//    }
//
//    @Override
//    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
//
//        ResultSet result;
//
//        if (event.getReactionEmote().getName().equals("🚨")) {
//            try {
//                sql.makeJDBCConnection();
//                result = sql.select("reaction_count");
//                result.next();
//                setReactionCount(result.getInt("count"));
//                result.close();
//
//                incrementReactionCount(1);
//                sql.update("reaction_count", "id", "1", "count", getReactionCount());
//                String messageId = "'" + event.getMessageId() + "'";
//                String dateTime = "'" + currentDateTime.getDateTime() + "'";
//                sql.update("UPDATE messages SET reaction_time = '"+currentDateTime.getDateTime()+"' WHERE discord_id = '"+event.getMessageId()+"'");
//            } catch(SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
//
//            try {
//                sql.makeJDBCConnection();
//                result = sql.select("report_count");
//                result.next();
//                setReportCount(result.getInt("count"));
//                result.close();
//
//                incrementReportCount(1);
//                sql.update("report_count", "id", "1", "count", getReportCount());
//                valueString = "(" + event.getMessageId() + ", '" + currentDateTime.getDateTime() + "', null)";
//                sql.insert("messages", valueString);
//            } catch(SQLException e) {
//                e.printStackTrace();
//            }
//
//
//
//
//        }
//
//        if (reportMessage[0].equalsIgnoreCase(PREFIX + "statistik")) {
//
//            try {
//                sql.makeJDBCConnection();
//                result = sql.select("report_count");
//                result.next();
//                setReportCount(result.getInt("count"));
//                result.close();
//
//                result = sql.select("reaction_count");
//                result.next();
//                setReactionCount(result.getInt("count"));
//                result.close();
//            } catch(SQLException e) {
//                e.printStackTrace();
//            }
//
//            int reportRatio = getReportRatio();
//
//            EmbedBuilder statisticsFeed = new EmbedBuilder();
//            statisticsFeed.setTitle("Zeit für etwas Statistik, wooot!:");
//            statisticsFeed.setDescription(
//                    "Anzahl Meldungen: **"+getReportCount()+"**\n" +
//                    "Anzahl Reaktionen: **"+getReactionCount()+"**\n" +
//                    "Das sind: **"+reportRatio+" Prozent**");
//
//            event.getChannel().sendTyping().queue();
//            event.getChannel().sendMessage(statisticsFeed.build()).queue();
//            statisticsFeed.clear();
//        }
//
//        if (reportMessage[0].equalsIgnoreCase(PREFIX + "delete")) {
//            event.getChannel().deleteMessageById(event.getMessageId());
//        }
//    }
//}
