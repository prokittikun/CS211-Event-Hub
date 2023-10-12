package cs211.project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeService {
    public static String getCurrentDate() {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Bangkok"));

        int year = now.getYear();

        return String.format("%04d", year) + "-" + String.format("%02d", now.getMonthValue()) + "-" + String.format("%02d", now.getDayOfMonth());
    }

    public static String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formattedTime = currentTime.format(timeFormatter);

        return formattedTime;
    }

    public static String convertDateFormat(String inputDate) {
        try {
            // Define the input date format
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            // Parse the input date string
            Date date = inputFormat.parse(inputDate);

            // Add 543 to the year
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//            date.setYear(date.getYear() + 543);

            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    public static String toString(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = inputFormat.parse(inputDate);
            int gregorianYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));

            SimpleDateFormat thaiDateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
            String thaiDate = thaiDateFormat.format(date);

            return thaiDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    public static String getCurrentDateTime() {
        return getCurrentDate() + " " + getCurrentTime();
    }
}