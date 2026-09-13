package kiu.webmailnauta.view;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ImpWebClient extends WebViewClient {

    private static final String javascriptCode = """
                    function fillForm(){
                        var username = "magaly.larubia@nauta.cu";
                        var password = "PtAS8S48rH";
                        document.getElementById("horde_user").value = username;
                        document.getElementById("horde_pass").value = password;
                    }
            
                    function hiddeForm() {
                        // Select all elements with data-role="fieldcontain"
                        var fields = document.querySelectorAll('[data-role="fieldcontain"]');
                        fields.forEach(function(field) {
                            field.style.display = "none";
                        });
                        document.getElementsByClassName("ui-collapsible")[0].style.display = "none";
                        var img = document.querySelector('img[src="/themes/nauta/graphics/etecsa_logo.png"]');
                        if (img) {
                            var table = img.closest('table');
                            if (table) {
                                table.style.display = "none";
                            }
                        }
                        var submitBtn = document.querySelector('input[type="submit"]');
                        if (submitBtn) {
                            var fieldset = submitBtn.closest('fieldset');
                            if (fieldset) {
                                fieldset.style.display = "none";
                            }
                        }
                    }
            
                    fillForm();
                    hiddeForm();
            """;

    @Override
    public void onPageFinished(WebView view, String url) {
//        view.evaluateJavascript("window.loadListener.onLoadComplete(document.documentElement.outerHTML);", null);
        String js = """
                    const observer = new MutationObserver((mutations, obs) => {
                        const target = document.querySelector('#imp-message-body'); // or your target selector
                        if (target && target.innerText.trim().length > 0) {
                            obs.disconnect();
                            window.loadListener.onLoadComplete(document.documentElement.outerHTML);
                        }
                    });
                    observer.observe(document.body, { childList: true, subtree: true });
                """;
        view.evaluateJavascript(js, null);
        view.evaluateJavascript(javascriptCode, null);
    }
}
