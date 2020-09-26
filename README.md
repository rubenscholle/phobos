# phobos
Java Discord Bot using the Java Discord Api (JDA)

PHOBOS is a bot for Discord gaming communities.
Members of the community can report other players violating server rules on the associated gaming servers.
The bot is attached to an SQL database and tracks the times and number of player reports.
Server Admins can react to reports amde by the bot and the bot tracks the number of responses and time-to-response.
The bot is also able to give basic statistics about the ratio of reports / reactions and the number of reactions within certain time windows.

*** Requirements ***
PHOBOS was written with Java 14 (14.0.2) and Maven using the compiler version 1.8

*** Installation ***

The bot project is written with MAVEN which must be rebuild and run on a local server or webserver. The bot must be invited to the Discord server
using the Discord Developer Portal at https://discord.com/login?redirect_to=%2Fdevelopers%2Fapplications.

*** USAGE ***

The PhobosBot.java contains the main method. Bot classes musst be loaded into the main method for the JDA to recognize the classes.
The bot provides a basic properties template (template.properties.xml) for setting the bot token and database logins
