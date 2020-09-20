import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class GuildMemberLeave extends ListenerAdapter {

    // Listen to users leaving the server
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {

        Objects.requireNonNull(event.getGuild().getDefaultChannel())
                .sendMessage(Objects.requireNonNull(event.getMember()).getNickname() +
                "hat " +
                event.getGuild().getName() +
                "verlassen!").queue();
    }
}
