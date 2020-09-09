import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.util.List;

public class BotListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String botDiscordName = "U:phobos_bot(667060580854661121)";
        List<MessageEmbed> messageEmbedsReceived;
        MessageEmbed firstEmbed;
        String queryStatement;
        String messageId;
        String currentTime;

        try {
            // Listens to phobos_bot only
            if (event.getAuthor().toString().equals(botDiscordName)) {
                messageEmbedsReceived = event.getMessage().getEmbeds();
                firstEmbed = messageEmbedsReceived.get(0);

                if (firstEmbed.getTitle().startsWith("Spielermeldung durch")) {

                    messageId = event.getMessageId();
                    currentTime = CurrentDateTime.getTime(CurrentDateTime.getSqlFormat());

                    //try {
                        SQLParser.connect();
                        queryStatement = "INSERT INTO messages " +
                                "(discord_id, report_time) VALUES ('"
                                + messageId +
                                "', '"
                                + currentTime +
                                "');";
                        SQLParser.insert(queryStatement);
                        SQLParser.disconnect();
                    //} catch (SQLException e) {
                    //    e.printStackTrace();
                    //}
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
