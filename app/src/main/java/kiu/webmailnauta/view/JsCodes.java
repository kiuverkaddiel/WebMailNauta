package kiu.webmailnauta.view;

public interface JsCodes {
    String javascriptCodeSubmitForm = """
                function submitForm() {
                    var submitBtn = document.querySelector('input[type="submit"]');
                    if (submitBtn) {
                        submitBtn.click();
                    }
                }
                submitForm();
            """;

    String javascriptCodeNextMessage = """
                (function() {
                        if (window.ImpMobile && typeof ImpMobile.navigateMessage === 'function') {
                                    ImpMobile.navigateMessage(1);
                                }
                    })();
            """;
}
