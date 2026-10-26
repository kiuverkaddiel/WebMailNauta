package kiu.webmailnauta.view.filter;

import android.webkit.WebView;
import android.widget.Button;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kiu.webmailnauta.model.SavedListDao;
import kiu.webmailnauta.view.Extractor;

public class FilterManager {

    final private Extractor extractor;
    final private WebView webView;
    final private Button btnLogin;
    final private SavedListDao savedListDao;

    public FilterManager(WebView webView, Extractor extractor, Button btnLogin, SavedListDao savedListDao) {
        this.webView = webView;
        this.extractor = extractor;
        this.btnLogin = btnLogin;
        this.savedListDao = savedListDao;
    }

    public IFilter getFilter() {
        Document document = extractor.getDocument();

        Element form = document.selectFirst("input[type=\"submit\"]");
        if (form != null) {
            return new LoginFilter(webView, btnLogin);
        }

        Element mailboxItem = document.selectFirst("#imp-mailbox-list li a");
        if (mailboxItem != null) {
            return new MainFilter(webView, document, savedListDao);
        }

        Element div = document.selectFirst("#imp-message-body");
        if (div != null) {
            // check if the div is empty
            if (!div.text().trim().isEmpty()) {
                return new MessageFilter(webView, extractor, savedListDao);
            }
        }

        return null;
    }
}
