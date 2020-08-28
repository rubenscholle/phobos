import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class PhobosBot {

    public static JDA jda;

    public static void main(String[] args) throws Exception {

        //jda = JDABuilder.createDefault("NjY3MDYwNTgwODU0NjYxMTIx.Xh9Okw.IKR2b7PSbrWMoMUr8SZkf0c7-gs").build();

        JDABuilder builder = JDABuilder.createDefault("NjY3MDYwNTgwODU0NjYxMTIx.Xh9Okw.IKR2b7PSbrWMoMUr8SZkf0c7-gs")
                // Enables the bot to update user information / joins / leaves
                // See https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/requests/GatewayIntent.html for more
                //
                .enableIntents(GatewayIntent.GUILD_MEMBERS);

        JDA jda = builder.build();

        jda.addEventListener(new Commands());
        jda.addEventListener(new GuildMemberJoin());
        jda.addEventListener(new EmoteAdd());
        jda.addEventListener(new TicTacToe());

        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.watching("Max ist blöd"));

      }

}

