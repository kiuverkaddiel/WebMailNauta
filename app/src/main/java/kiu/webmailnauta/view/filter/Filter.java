package kiu.webmailnauta.view.filter;

import android.webkit.WebView;

public class Filter implements IFilter {

    final private WebView webView;
    protected String jsCode = "";

    public Filter(WebView webView) {
        this.webView = webView;
    }

    @Override
    public void applyFilter() {
        webView.evaluateJavascript(jsCode, null);
    }
}
