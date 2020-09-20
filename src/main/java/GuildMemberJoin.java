import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Objects;

public class GuildMemberJoin extends ListenerAdapter {

    private static String roleToAdd;

    public static String getRoleToAdd() {
        return roleToAdd;
    }

    public static void setRoleToAdd(String role) {
        GuildMemberJoin.roleToAdd = role;
    }

    public GuildMemberJoin(String role) {
        GuildMemberJoin.setRoleToAdd(role);
    }

    // Listen to Guildmemberjoins and assign the role "Gast"
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        List<Role> rolesFromServer;

        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage("Hi " +
                event.getMember().getAsMention() +", willkommen auf " +
                event.getGuild().getName()).queue();

        rolesFromServer = event.getGuild().getRolesByName(getRoleToAdd(), true);
        event.getGuild().addRoleToMember(event.getMember(), rolesFromServer.get(0)).queue();
    }
}
