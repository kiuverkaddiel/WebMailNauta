package kiu.webmailnauta.view.filter;

import android.webkit.WebView;
import android.widget.Button;

public class LoginFilter extends Filter {

    final private Button btnLogin;

    public LoginFilter(WebView webView, Button btnLogin) {
        super(webView);
        this.btnLogin = btnLogin;

        jsCode = """
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
    }

    @Override
    public void applyFilter() {
        btnLogin.setVisibility(Button.VISIBLE);
        super.applyFilter();
    }
}
