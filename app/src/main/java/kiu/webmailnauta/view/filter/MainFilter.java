package kiu.webmailnauta.view.filter;

import android.os.Build;
import android.webkit.WebView;

import org.json.JSONArray;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import kiu.webmailnauta.model.SavedListDao;

public class MainFilter extends Filter {

    final private Document document;
    final private SavedListDao savedListDao;

    public MainFilter(WebView webView, Document document, SavedListDao savedListDao) {
        super(webView);
        this.document = document;
        this.savedListDao = savedListDao;
    }

    @Override
    public void applyFilter() {
        List<String> savedSubjects = new ArrayList<>();

        if (document != null) {
            Elements items = document.select("#imp-mailbox-list li.imp-message");
            for (Element item : items) {
                Element link = item.selectFirst("a.ui-link-inherit");
                if (link == null) continue;

                String subject = link.text().trim();
                if (!subject.isEmpty() && savedListDao.isSubjectSaved(subject)) {
                    savedSubjects.add(subject);
                }
            }
        }

        if (!savedSubjects.isEmpty()) {
            String savedSubjectsJson = new JSONArray(savedSubjects).toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                jsCode = """
                        function paintSavedMessagesGreen() {
                            var savedSubjects = %s;
                            var items = document.querySelectorAll('#imp-mailbox-list li.imp-message');
                            items.forEach(function(item) {
                                var link = item.querySelector('a.ui-link-inherit');
                                if (link && savedSubjects.indexOf(link.textContent.trim()) !== -1) {
                                    link.style.color = 'green';
                                }
                            });
                        }
                        paintSavedMessagesGreen();
                    """.formatted(savedSubjectsJson);
            }
        } else {
            jsCode = "";
        }

        super.applyFilter();
    }
}
