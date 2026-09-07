package kiu.webmailnauta.legacy.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import kiu.webmailnauta.legacy.Constants;

public final class MyTime {
    public static final String[] MONTHS = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private static Calendar calendar = null;

    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Long timeStamp) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        calendar = gregorianCalendar;
        gregorianCalendar.setTimeInMillis(timeStamp.longValue());
    }

    public static void setCalendar(int year, int month, int day, int hour) {
        calendar = new GregorianCalendar(year, month, day, hour, 0);
    }

    public static void setCalendar(String turn) {
        String[] parts = turn.split(" ");
        int i = 0;
        int day = Integer.valueOf(parts[0]).intValue();
        int month = 0;
        int year = Integer.valueOf(parts[4]).intValue();
        int hour = parts[6].equals("DIA") ? 12 : 19;
        String[] strArr = MONTHS;
        int length = strArr.length;
        while (i < length && !strArr[i].equals(parts[2])) {
            month++;
            i++;
        }
        setCalendar(year, month, day, hour);
    }

    public static void setCalendar(Calendar newCalendar) {
        calendar = newCalendar;
    }

    public static void setCalendar() {
        calendar = Calendar.getInstance();
    }

    public static void releaseCalendar() {
        calendar = null;
    }

    public static String generateShortDate() {
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        String day_of_month = Integer.valueOf(calendar.get(5)).toString();
        if (day_of_month.length() < 2) {
            day_of_month = "0" + day_of_month;
        }
        String month = Integer.valueOf(calendar.get(2) + 1).toString();
        if (month.length() < 2) {
            month = "0" + month;
        }
        String year = Integer.valueOf(calendar.get(1)).toString().substring(2);
        String hour = Integer.valueOf(calendar.get(11)).toString();
        if (hour.length() < 2) {
            hour = "0" + hour;
        }
        String minute = Integer.valueOf(calendar.get(12)).toString();
        if (minute.length() < 2) {
            minute = "0" + minute;
        }
        String turn = day_of_month + month + year + "-" + hour + Constants.VALUE_SEPARATOR + minute;
        if (wasNull) {
            releaseCalendar();
        }
        return turn;
    }

    public static String generateDate() {
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        int day_of_month = calendar.get(5);
        int month = calendar.get(2);
        int year = calendar.get(1);
        if (calendar.after(new GregorianCalendar(year, month, day_of_month, 21, 45))) {
            calendar.roll(11, 12);
            day_of_month = calendar.get(5);
            month = calendar.get(2);
            year = calendar.get(1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.valueOf(day_of_month));
        sb.append(" de ");
        sb.append(MONTHS[month]);
        sb.append(" de ");
        StringBuilder date = sb.append(Integer.valueOf(year));
        if (wasNull) {
            releaseCalendar();
        }
        return date.toString();
    }

    public static Boolean isNight() {
        return Boolean.valueOf(!isDay().booleanValue());
    }

    public static Boolean isDay() {
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        int day_of_month = calendar.get(5);
        int month = calendar.get(2);
        boolean z = true;
        int year = calendar.get(1);
        if (!calendar.after(new GregorianCalendar(year, month, day_of_month, 7, 0)) || !calendar.before(new GregorianCalendar(year, month, day_of_month, 14, 0))) {
            z = false;
        }
        boolean result = z;
        if (wasNull) {
            releaseCalendar();
        }
        return Boolean.valueOf(result);
    }

    public static boolean preLockTime() {
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        int day_of_month = calendar.get(5);
        int month = calendar.get(2);
        int year = calendar.get(1);
        boolean lockDay = calendar.after(new GregorianCalendar(year, month, day_of_month, 12, 45)) && calendar.before(new GregorianCalendar(year, month, day_of_month, 13, 30));
        boolean lockNight = calendar.after(new GregorianCalendar(year, month, day_of_month, 20, 0)) && calendar.before(new GregorianCalendar(year, month, day_of_month, 21, 45));
        if (wasNull) {
            releaseCalendar();
        }
        if (lockDay || lockNight) {
            return true;
        }
        return false;
    }

    public static boolean preLockTime(String lockTime) {
        if (lockTime == null) {
            return preLockTime();
        }
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        boolean needLock = false;
        String[] aux = lockTime.split("-");
        if (aux.length == 2) {
            String[] aux1 = aux[0].split(Constants.VALUE_SEPARATOR);
            String[] aux2 = aux[1].split(Constants.VALUE_SEPARATOR);
            if (aux1.length + aux2.length == 4) {
                int hour1 = Integer.valueOf(aux1[0]).intValue();
                int minute1 = Integer.valueOf(aux1[1]).intValue();
                int hour2 = Integer.valueOf(aux2[0]).intValue();
                int minute2 = Integer.valueOf(aux2[1]).intValue();
                int day_of_month = calendar.get(5);
                int month = calendar.get(2);
                int year = calendar.get(1);

                Calendar calendar1 = new GregorianCalendar(year, month, day_of_month, hour1, minute1);
                Calendar calendar2 = new GregorianCalendar(year, month, day_of_month, hour2, minute2);

                needLock = calendar.after(calendar1) && calendar.before(calendar2);
            }
        }
        if (wasNull) {
            releaseCalendar();
        }
        return needLock;
    }

    public static String isNightString() {
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        String result = isNight().booleanValue() ? "NOCHE" : "DIA";
        if (wasNull) {
            releaseCalendar();
        }
        return result;
    }

    public static String generateTurn() {
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        String turn = generateDate() + " - " + isNightString();
        if (wasNull) {
            releaseCalendar();
        }
        return turn;
    }

    public static boolean isLockTime() {
        return !isInRange();
    }

    public static boolean isInRange() {
        setCalendar();
        int day_of_month = calendar.get(5);
        int month = calendar.get(2);
        boolean z = true;
        int year = calendar.get(1);
        if ((!calendar.after(new GregorianCalendar(year, month, day_of_month, 7, 0)) || !calendar.before(new GregorianCalendar(year, month, day_of_month, 13, 15))) && (!calendar.after(new GregorianCalendar(year, month, day_of_month, 14, 0)) || !calendar.before(new GregorianCalendar(year, month, day_of_month, 21, 30)))) {
            z = false;
        }
        boolean result = z;
        releaseCalendar();
        return result;
    }

    public static int compareToCurrentTime(long timeMillis) {
        long current = System.currentTimeMillis();
        if (timeMillis > current) {
            return 1;
        }
        if (timeMillis < current) {
            return -1;
        }
        return 0;
    }

    public static String generateHour() {
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        String result = calendar.get(10) + Constants.VALUE_SEPARATOR + calendar.get(12) + Constants.VALUE_SEPARATOR + calendar.get(13);
        if (wasNull) {
            releaseCalendar();
        }
        return result;
    }

    public static String getDayOfMonth() {
        boolean wasNull = false;
        if (calendar == null) {
            setCalendar();
            wasNull = true;
        }
        int day_of_month = calendar.get(5);
        int month = calendar.get(2);
        if (calendar.after(new GregorianCalendar(calendar.get(1), month, day_of_month, 21, 45))) {
            calendar.roll(5, 1);
            day_of_month = calendar.get(5);
        }
        String result = Integer.valueOf(day_of_month).toString();
        if (wasNull) {
            releaseCalendar();
        }
        return result;
    }
}
