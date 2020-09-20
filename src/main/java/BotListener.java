import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

public class BotListener extends ListenerAdapter {

    // Listen to reportFeeds of the Bot and save timestamp of report to database
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String botDiscordName = "U:phobos_bot(667060580854661121)";
        List<MessageEmbed> messageEmbedsReceived;
        MessageEmbed firstEmbed;
        String queryStatement;
        String messageId;
        String currentTime;

        // Listens to phobos_bot only
        if (!event.getAuthor().toString().equals(botDiscordName)) return;

        messageEmbedsReceived = event.getMessage().getEmbeds();
        if (messageEmbedsReceived.isEmpty()) {
            return;
        } else {
            firstEmbed = messageEmbedsReceived.get(0);
        }

        if (!firstEmbed.isEmpty() &
                Objects.requireNonNull(firstEmbed.getTitle()).startsWith("Spielermeldung durch")) {
            messageId = event.getMessageId();
            currentTime = CurrentDateTime.getTime(CurrentDateTime.getSqlFormat());
            // Save system time of ReportFeed to database
            SQLParser.connect();
            queryStatement = "INSERT INTO messages " +
                    "(discord_id, report_time) VALUES ('" +
                    messageId +
                    "', '" +
                    currentTime +
                    "');";
            SQLParser.insert(queryStatement);
            SQLParser.disconnect();
        }
    }
}
