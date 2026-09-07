package kiu.webmailnauta.legacy.runners;

public class Action {
    private String action;
    private String from;
    private String json;
    private String subject;

    public Action() {
    }

    public Action(String subject2, String action2, String json2, String from2) {
        this.subject = subject2;
        this.action = action2;
        this.json = json2;
        this.from = from2;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String json2) {
        this.json = json2;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from2) {
        this.from = from2;
    }
}
