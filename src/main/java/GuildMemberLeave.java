import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberLeave extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {

        event.getGuild().getDefaultChannel().sendMessage(event.getMember().getAsMention() + "hat " + event.getGuild().getName() + "verlassen!").queue();
    }
}
