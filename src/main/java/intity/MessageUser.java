package intity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MessageUser {
    public final User userTo;
    public final User userFrom;
    public final String messageText;
    public final String data;

    public MessageUser(User userTo, User userFrom, String messageText, String data) {
        this.userTo = userTo;
        this.userFrom = userFrom;
        this.messageText = messageText;
        this.data = data;
    }

    public String getMessageText() {
        return messageText;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public String getData() {
        StringBuilder sb = new StringBuilder();
        LocalDateTime parse = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a"));
        Month month = parse.getMonth();
        int day = parse.getDayOfMonth();
        int hour = parse.getHour();
        int minute = parse.getMinute();
        String timePeriod = data.substring(data.length() - 2);
        sb.append(month.toString());
        sb.append(" ");
        sb.append(day);
        sb.append(" ");
        sb.append(hour);
        sb.append(":");
        sb.append(minute);
        sb.append(" ");
        sb.append(timePeriod);
        return sb.toString();
    }

    public String getMonth() {
        StringBuilder sb = new StringBuilder();
        LocalDateTime parse = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a"));
        Month month = parse.getMonth();
        int day = parse.getDayOfMonth();
        int hour = parse.getHour();
        int minute = parse.getMinute();
        String timePeriod = data.substring(data.length() - 2);
        sb.append(month.toString().toLowerCase());
        sb.append(" ");
        sb.append(day);
        sb.append(" ");
        sb.append(hour);
        sb.append(":");
        sb.append(minute);
        sb.append(" ");
        sb.append(timePeriod);
        return sb.toString();
    }

}
