import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class PropertiesInjector {

    private String bot_token, db_server, db_driver, db_user, db_password;

    public PropertiesInjector(String fileName) {
        File propertiesFile;
        DocumentBuilderFactory documentBuilderFactory;
        DocumentBuilder documentBuilder;
        Document document;

        propertiesFile = new File(fileName);
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            try {
                document = documentBuilder.parse(propertiesFile);
                bot_token =
                        document.getElementsByTagName("bot_token").item(0).getTextContent();
                db_server =
                        document.getElementsByTagName("db_server").item(0).getTextContent();
                db_driver =
                        document.getElementsByTagName("db_driver").item(0).getTextContent();
                db_user =
                        document.getElementsByTagName("db_user").item(0).getTextContent();
                db_password =
                        document.getElementsByTagName("db_password").item(0).getTextContent();
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getBot_token() {
        return bot_token;
    }

    public String getDb_server() {
        return db_server;
    }

    public String getDb_driver() {
        return db_driver;
    }

    public String getDb_user() {
        return db_user;
    }

    public String getDb_password() {
        return db_password;
    }
}
