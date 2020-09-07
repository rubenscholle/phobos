import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDateTime {

    // Singleton design pattern for class CurrentDateTime
    private static final CurrentDateTime instance = new CurrentDateTime();

    private CurrentDateTime() {}

    public static CurrentDateTime getInstance() {

        return instance;
    }

    private static final String sqlFormat = "YYYY-MM-dd hh:mm:ss";

    public static String getSqlFormat() {
        return sqlFormat;
    }

    public String get(String dateTimeFormat) {

        DateTimeFormatter dtf;
        LocalDateTime now = LocalDateTime.now();

        dtf = DateTimeFormatter.ofPattern(dateTimeFormat);
        return dtf.format(now);
    }
}