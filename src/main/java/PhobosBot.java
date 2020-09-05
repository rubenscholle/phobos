import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class PhobosBot {

    public static JDA jda;

    public static void main(String[] args) {

        //jda = JDABuilder.createDefault("NjY3MDYwNTgwODU0NjYxMTIx.Xh9Okw.IKR2b7PSbrWMoMUr8SZkf0c7-gs").build();

        try {
            JDABuilder builder = JDABuilder.createDefault("NjY3MDYwNTgwODU0NjYxMTIx.Xh9Okw.rVHm3rh2z9bTSeFQ6eGqfcALsu8")
                    // Enables the bot to update user information / joins / leaves
                    // See https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/requests/GatewayIntent.html for more
                    .enableIntents(GatewayIntent.GUILD_MEMBERS);

            JDA jda = builder.build();

        } catch (LoginException e) {
            System.out.println("Connection to Discord failed");
            e.printStackTrace();
            return;
        }

        SQLParser sql = new SQLParser();
        sql.makeJDBCConnection();

//       jda.addEventListener(new BasicCommands());
//       jda.addEventListener(new GuildMemberJoin());
//       jda.addEventListener(new GuildMemberLeave());
//       jda.addEventListener(new ReportService(true));
//       //jda.addEventListener(new EmoteAdd());
//       //jda.addEventListener(new TicTacToe());

//       jda.getPresence().setStatus(OnlineStatus.IDLE);
//       jda.getPresence().setActivity(Activity.watching("Max ist blöd"));
    }
}

