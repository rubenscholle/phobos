import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CurrentDateTime {

    // Singleton design pattern for class CurrentDateTime
    private CurrentDateTime() {}

    private static final String sqlFormat = "YYYY-MM-dd hh:mm:ss";

    public static String getSqlFormat() {
        return sqlFormat;
    }

    // Get current System Time as String
    public static String getTime(String dateTimeFormat) {

        DateTimeFormatter dateTimeFormatter;
        LocalDateTime now = LocalDateTime.now();

        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return dateTimeFormatter.format(now);
    }

    // Get time difference between two time strings in MM:SS format
    public static long getTimeDifference(String timeString1, String timeString2) {

        long timeDifference;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateTime1;
        Date dateTime2;

        try {
            // Times as Strings to times as Dates (datetime)
            dateTime1 = simpleDateFormat.parse(timeString1);
            dateTime2 = simpleDateFormat.parse(timeString2);

            // Time difference between dateTime1 and dateTime2 to time difference as long
            timeDifference = (dateTime2.getTime() - dateTime1.getTime());

            return timeDifference;

        } catch (ParseException e) {
            e.printStackTrace();
            // Lowest possible long from .getTime()
            return -3600000;
        }
    }

    // Get the average time (as String) from a List of time Strings
    public static String getAverageTime(List<Long> times) {

        long timeSum = 0;
        double averageTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

        for (long time : times) {
            System.out.println(time);
            timeSum += time;
        }

        // Calculate average (statistical mean) time
        averageTime = (double) timeSum / times.size();
        System.out.println(averageTime);

        // Return the average time as mm:ss String
        return simpleDateFormat.format(averageTime);
    }

    // Get grouped times as HashMap
    public static HashMap<String, List<Long>> getGroupedTimes(List<Long> times) {

        HashMap<String, List<Long>> groupedTimes = new HashMap<>();
        List<Long> currentList = new ArrayList<>();

        // Initialize groupedTimes HashMap with empty Lists
        groupedTimes.put("<5 min", currentList);
        groupedTimes.put("<15 min", currentList);
        groupedTimes.put("<30 min", currentList);
        groupedTimes.put("<60 min", currentList);
        groupedTimes.put(">60 min", currentList);

        for (long time : times) {
            if (time < 300000) {
                currentList = groupedTimes.get("<5 min");
                currentList.add(time);
                groupedTimes.put("<5 min", currentList);
            } else if (time < 900000) {
                currentList = groupedTimes.get("<15 min");
                currentList.add(time);
                groupedTimes.put("<15 min", currentList);
            } else if (time < 1800000) {
                currentList = groupedTimes.get("<30 min");
                currentList.add(time);
                groupedTimes.put("<30 min", currentList);
            } else if (time < 3600000) {
                currentList = groupedTimes.get("<60 min");
                currentList.add(time);
                groupedTimes.put("<60 min", currentList);
            } else {
                currentList = groupedTimes.get(">60 min");
                currentList.add(time);
                groupedTimes.put(">60 min", currentList);
            }
        }

      return groupedTimes;
  }
}
