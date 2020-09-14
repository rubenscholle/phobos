import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class CurrentDateTime {

    // Singleton design pattern for class CurrentDateTime
    //private static final CurrentDateTime instance = new CurrentDateTime();

    private CurrentDateTime() {}

//   public static CurrentDateTime getInstance() {

//       return instance;
//   }

    private static final String sqlFormat = "YYYY-MM-dd hh:mm:ss";

    public static String getSqlFormat() {
        return sqlFormat;
    }

    public static String getTime(String dateTimeFormat) {

        DateTimeFormatter dateTimeFormatter;
        LocalDateTime now = LocalDateTime.now();

        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return dateTimeFormatter.format(now);
    }

    public static String getTimeDifference(String time1, String time2) {

        String timeDifference;
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm:ss");
        Date d1;
        Date d2;

        try {
            d1 = simpleDateFormat1.parse(time1);
            d2 = simpleDateFormat1.parse(time2);

            timeDifference = simpleDateFormat2.format((d2.getTime() - d1.getTime()));

            return timeDifference;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAverageTime(List<String> timeDifferences) {

        double timeSum = 0;
        long currentTime;
        double averageTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

        for (String timeDifference : timeDifferences) {
            try {
                currentTime = simpleDateFormat.parse(timeDifference).getTime();
                timeSum += currentTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        averageTime = timeSum / timeDifferences.size();
        return simpleDateFormat.format(averageTime);
    }
}