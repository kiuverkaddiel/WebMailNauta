package kiu.webmailnauta.legacy;

import java.util.HashMap;

public class ContentValues extends HashMap<String, Object> {
    private static final long serialVersionUID = -5088543786592429001L;

    public Long getAsLong(String key) {
        return (Long) get(key);
    }

    public Integer getAsInteger(String key) {
        return (Integer) get(key);
    }

    public String getAsString(String key) {
        return (String) get(key);
    }

    public StringBuilder getAsStringBuilder(String key) {
        return new StringBuilder(getAsString(key));
    }
}
