import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// ToDo: Ändere Namen der Klasse
public class EmoteAdd extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        if (event.getReactionEmote().getName().equals("🚨")) {
            event.getGuild().getDefaultChannel().sendMessage("Toll, das war nicht irgendein Emoji, das war Tatütata").queue();
        } else {
            event.getGuild().getDefaultChannel().sendMessage("Tolles Emoji, hier hast du einen 🍪, weiter so!").queue();
        }
    }
}
