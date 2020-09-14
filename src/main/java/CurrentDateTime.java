import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class CurrentDateTime {

    // Singleton design pattern for class CurrentDateTime
    private CurrentDateTime() {}

    private static final String sqlFormat = "YYYY-MM-dd hh:mm:ss";

    public static String getSqlFormat() {
        return sqlFormat;
    }

    public static String getTime(String dateTimeFormat) {

        // Get current System Time as String
        DateTimeFormatter dateTimeFormatter;
        LocalDateTime now = LocalDateTime.now();

        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return dateTimeFormatter.format(now);
    }

    public static String getTimeDifference(String timeString1, String timeString2) {

        // Get time difference between twi time strings in MM:SS format
        String timeDifference;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm:ss");
        Date dateTime1;
        Date dateTime2;

        try {
            // Times as Strings to times as Dates (datetime)
            dateTime1 = simpleDateFormat2.parse(timeString1);
            dateTime2 = simpleDateFormat1.parse(timeString2);

            // Time difference between dateTime1 and dateTime2 to time difference as String
            timeDifference = simpleDateFormat2.format((dateTime2.getTime() - dateTime1.getTime()));

            return timeDifference;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAverageTime(List<String> times) {

        // Get the average time (as String)from a List of time Strings
        double timeSum = 0;
        long currentTime;
        double averageTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

        for (String timeDifference : times) {
            try {
                // Time as String to time as long for calculation
                currentTime = simpleDateFormat.parse(timeDifference).getTime();
                timeSum += currentTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Calculate average (statistical mean) time
        averageTime = timeSum / times.size();

        // Return the average time as String
        return simpleDateFormat.format(averageTime);
    }
}