package kiu.webmailnauta.view.filter;

import android.os.Build;
import android.webkit.WebView;
import android.widget.Button;

import kiu.webmailnauta.BuildConfig;

public class LoginFilter extends Filter {

    final private Button btnLogin;

    public LoginFilter(WebView webView, Button btnLogin) {
        super(webView);
        this.btnLogin = btnLogin;

        // Credentials are injected at build time from local.properties (see
        // local.properties.sample) and never hardcoded in source.
        String username = BuildConfig.NAUTA_USERNAME;
        String password = BuildConfig.NAUTA_PASSWORD;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            jsCode = """
                            function fillForm(){
                                var username = "%s";
                                var password = "%s";
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
                    """.formatted(username, password);
        }
    }

    @Override
    public void applyFilter() {
        btnLogin.setVisibility(Button.VISIBLE);
        super.applyFilter();
    }
}
