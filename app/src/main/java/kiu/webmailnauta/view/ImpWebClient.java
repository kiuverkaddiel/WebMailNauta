package kiu.webmailnauta.view;

import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ImpWebClient extends WebViewClient {

    private static final long INITIAL_DELAY_MS = 150;
    private static final long POLL_INTERVAL_MS = 200;
    private static final long MAX_WAIT_MS = 5000;
    private static final int STABLE_CHECKS_REQUIRED = 2;

    private static final String CHECK_AJAX_ACTIVE_JS = """
            (function() {
                try {
                    return (typeof jQuery !== 'undefined' && jQuery.active) ? jQuery.active : 0;
                } catch (e) {
                    return 0;
                }
            })();
            """;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private int generation = 0;
    private int stableChecks = 0;
    private long pollStartTime = 0;

    @Override
    public void onPageFinished(WebView view, String url) {
        generation++;
        final int currentGeneration = generation;
        stableChecks = 0;
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(() -> startPolling(view, currentGeneration), INITIAL_DELAY_MS);
    }

    private void startPolling(WebView view, int gen) {
        if (gen != generation) return;
        pollStartTime = System.currentTimeMillis();
        pollAjaxState(view, gen);
    }

    private void pollAjaxState(WebView view, int gen) {
        if (gen != generation) return;
        view.evaluateJavascript(CHECK_AJAX_ACTIVE_JS, value -> {
            if (gen != generation) return;

            boolean idle = "0".equals(value);
            stableChecks = idle ? stableChecks + 1 : 0;

            boolean timedOut = System.currentTimeMillis() - pollStartTime > MAX_WAIT_MS;
            if ((idle && stableChecks >= STABLE_CHECKS_REQUIRED) || timedOut) {
                captureHtml(view, gen);
            } else {
                handler.postDelayed(() -> pollAjaxState(view, gen), POLL_INTERVAL_MS);
            }
        });
    }

    private void captureHtml(WebView view, int gen) {
        if (gen != generation) return;
        view.evaluateJavascript(
                "window.loadListener.onLoadComplete(document.documentElement.outerHTML);", null);
    }
}