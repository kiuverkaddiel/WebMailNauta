package kiu.webmailnauta.task;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kiu.webmailnauta.task.callback.Callback;

public class TaskRunnerSingleThreadExecutor {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public <T> void executeAsync(Callable<T> callable, Callback<T> callback) {
        executor.execute(() -> {
            try {
                final T result = callable.call();
                handler.post(() -> callback.onComplete(result));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
