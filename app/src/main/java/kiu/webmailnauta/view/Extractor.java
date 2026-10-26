package kiu.webmailnauta.view;

import android.util.Log;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Extractor {

    private Document document;

    public Extractor() {
    }

    public Extractor(Document document) {
        this.document = document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public String extractMessageDate() {
        if (document == null) return null;
        Element dateLabel = document.getElementById("imp-message-date");
        return dateLabel != null ? dateLabel.text() : null;
    }

    public String extractMessageSubject() {
        if (document == null) return null;
        Element message = document.getElementById("message");
        if (message == null || message.text().isEmpty())
            return null;
        Element subjectLabel = message.selectFirst("h1.smartmobile-title");
        return subjectLabel != null ? subjectLabel.text() : null;
    }

    public static Date parseMessageDate(String rawDate) {
        if (rawDate == null || rawDate.trim().isEmpty()) {
            return null;
        }

        String trimmed = rawDate.trim();
        String lower = trimmed.toLowerCase(Locale.US);

        int dayOffset = Integer.MIN_VALUE;
        String timePart = null;

        if (lower.startsWith("today,") || lower.startsWith("hoy,")) {
            timePart = trimmed.substring(trimmed.indexOf(',') + 1).trim();
            dayOffset = 0;
        }

        if (dayOffset != Integer.MIN_VALUE) {
            String[] timePatterns = {
                    "h:mm:ss a z",
                    "h:mm:ss a",
                    "hh:mm:ss a z",
                    "hh:mm:ss a",
                    "HH:mm:ss z",
                    "HH:mm:ss",
                    "h:mm a z",
                    "h:mm a",
                    "HH:mm z",
                    "HH:mm"
            };

            for (String pattern : timePatterns) {
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat(pattern, Locale.US);
                    Date parsedTime = timeFormat.parse(timePart);
                    if (parsedTime != null) {
                        TimeZone tz = timeFormat.getTimeZone();
                        Calendar calendar = Calendar.getInstance(tz);
                        calendar.add(Calendar.DAY_OF_YEAR, dayOffset);

                        Calendar timeCal = Calendar.getInstance(tz);
                        timeCal.setTime(parsedTime);

                        calendar.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
                        calendar.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
                        calendar.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
                        calendar.set(Calendar.MILLISECOND, 0);

                        return calendar.getTime();
                    }
                } catch (ParseException ignored) {
                }
            }
        }

        String[] fullPatterns = {
                "MM/dd/yyyy ( h:mm:ss a z)",
                "MM/dd/yyyy (h:mm:ss a z)",
                "dd/MM/yyyy ( h:mm:ss a z)",
                "dd/MM/yyyy (h:mm:ss a z)",
                "MM/dd/yyyy, h:mm:ss a z",
                "MM/dd/yyyy h:mm:ss a z",
                "dd/MM/yyyy, h:mm:ss a z",
                "dd/MM/yyyy h:mm:ss a z",
                "MM/dd/yy, h:mm:ss a z",
                "MM/dd/yy h:mm:ss a z",
                "dd/MM/yy, h:mm:ss a z",
                "dd/MM/yy h:mm:ss a z",
                "MM/dd/yyyy, h:mm:ss a",
                "dd/MM/yyyy, h:mm:ss a",
                "yyyy-MM-dd HH:mm:ss z",
                "yyyy-MM-dd HH:mm:ss",
                "EEE, d MMM yyyy HH:mm:ss Z",
                "d MMM yyyy HH:mm:ss Z"
        };

        for (String pattern : fullPatterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
                return sdf.parse(trimmed);
            } catch (ParseException ignored) {
            }
        }

        Log.e("Extractor", "Unable to parse date: " + rawDate);
        return null;
    }

    public static String getTurnByDate(Date parsedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String turn = "OUT";
        if (hour == 7 || (hour > 7 && hour < 13) || (hour == 13 && minute <= 30)) {
            turn = "DIA";
        } else if (hour == 14 || (hour > 14 && hour < 21) || (hour == 21 && minute <= 30)) {
            turn = "NOCHE";
        }

        SimpleDateFormat folderDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String folderName = folderDateFormat.format(parsedDate) + "-" + turn;
        // remove zero on the day and month if present
        folderName = folderName.replaceAll("(^|-)0", "$1");
        return folderName;
    }

    public String getMessageTurn() {
        if (document == null) return null;

        Element div = document.selectFirst("#imp-message-body");
        if (div == null) return null;
        String date = extractMessageDate();

        Date parsedDate = parseMessageDate(date);
        if (parsedDate == null) {
            Log.e("Extractor", "Unable to parse date: " + date);
            return null;
        }

        return getTurnByDate(parsedDate);
    }
}
