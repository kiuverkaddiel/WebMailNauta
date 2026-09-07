package kiu.webmailnauta.task.callback;

public interface Callback<T> {
    void onComplete(T result);
}
