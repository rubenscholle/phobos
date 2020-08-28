import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// ToDo Weise autom. die Rolle "Gast" zu
public class GuildMemberJoin extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        event.getGuild().getDefaultChannel().sendMessage("Hi " + event.getMember().getAsMention() +", willkommen auf " + event.getGuild().getName()).queue();
    }
}
