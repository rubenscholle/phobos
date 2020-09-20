import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import net.dv8tion.jda.api.requests.GatewayIntent;


public class PhobosBot {

    public static JDA jda;

    public static void main(String[] args) throws Exception {

        // Load settings from file
        PropertiesInjector propertiesInjector = new PropertiesInjector("src/main/resources/properties.xml");

        JDABuilder builder = JDABuilder.createDefault(propertiesInjector.getBot_token())
                // Enables the bot to update user information / joins / leaves
                // See https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/requests/GatewayIntent.html for more
                .enableIntents(GatewayIntent.GUILD_MEMBERS);

        JDA jda = builder.build();

        // jda.getPresence().setStatus(OnlineStatus.IDLE); // Set online status of Phobos bot
        // jda.getPresence().setActivity(Activity.watching("everything")); // Set current activity of Phobos bot

        jda.addEventListener(new BasicCommands());
        jda.addEventListener(new GuildMemberJoin("Gast"));
        jda.addEventListener(new GuildMemberLeave());
        jda.addEventListener(new ReportListener(true));
        jda.addEventListener(new BotListener());
        jda.addEventListener(new ReactionListener("🚨"));
        jda.addEventListener(new StatisticsListener());
        //jda.addEventListener(new TicTacToe());
    }
}

