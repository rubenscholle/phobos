import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ReactionListener extends ListenerAdapter {

    public ReactionListener(String emoji) {
        setReactionToListen(emoji);
    }

    private String reactionToListen = null;

    public String getReactionToListen() {
        return reactionToListen;
    }

    public void setReactionToListen(String reactionToListen) {
        this.reactionToListen = reactionToListen;
    }

    private static void incrementReactionCount() {
        ResultSet result;
        int reactionCount;
        String queryStatement;

        try {
            SQLParser.connect();
            result = SQLParser.select("report_count");
            result.next();
            reactionCount = result.getInt("count");
            result.close();

            reactionCount++;
            queryStatement = "Update reaction_count SET count = "
                    + reactionCount +
                    " WHERE id = 1;";

            SQLParser.update(queryStatement);
            SQLParser.disconnect();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        String queryStatement;
        String messageId;
        String currentTime;

        if (event.getReactionEmote().getName().equals(getReactionToListen())) {

            messageId = event.getMessageId();
            currentTime = CurrentDateTime.getTime(CurrentDateTime.getSqlFormat());

            incrementReactionCount();

            //try {
            SQLParser.connect();
            queryStatement = "Update messages SET reaction_time = '"
                    + currentTime +
                    "' WHERE discord_id = '"
                    + messageId +
                    "';";
            SQLParser.insert(queryStatement);
            SQLParser.disconnect();
            //} catch (SQLException e) {
            //    e.printStackTrace();
            //}

        }
    }
}
