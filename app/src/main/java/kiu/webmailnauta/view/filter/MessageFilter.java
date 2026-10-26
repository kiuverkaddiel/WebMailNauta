package kiu.webmailnauta.view.filter;

import android.webkit.WebView;

import org.jsoup.nodes.Document;

import kiu.webmailnauta.model.SavedListDao;
import kiu.webmailnauta.view.Extractor;

public class MessageFilter extends Filter {

    final private Extractor extractor;
    final private SavedListDao savedListDao;

    public MessageFilter(WebView webView, Extractor extractor, SavedListDao savedListDao) {
        super(webView);
        this.extractor = extractor;
        this.savedListDao = savedListDao;
    }

    @Override
    public void applyFilter() {
        String subject = extractor.extractMessageSubject();
        if (subject != null && !subject.isEmpty() && savedListDao.isSubjectSaved(subject)) {
            jsCode = """
                    function paintSubjectGreen() {
                        var subjectLabel = document.querySelector('#message h1.smartmobile-title');
                        if (subjectLabel) {
                            subjectLabel.style.color = 'green';
                        }
                    }
                    paintSubjectGreen();
                """;
        } else {
            jsCode = "";
        }

        super.applyFilter();
    }
}
