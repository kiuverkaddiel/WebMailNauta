package kiu.webmailnauta.view.onclick;

import android.view.View;
import android.widget.EditText;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.util.Arrays;
import java.util.Objects;

import kiu.webmailnauta.task.TaskRunnerSingleThreadExecutor;
import kiu.webmailnauta.task.callable.CallableJsoupResponse;
import kiu.webmailnauta.task.callback.CallbackResponse;

public class OnclickLogin implements View.OnClickListener {

    private final Document document;
    private final EditText etEmailUser;
    private final EditText etPassword;
    private final EditText etCaptcha;

    public OnclickLogin(Document document, EditText etEmailUser, EditText etPassword, EditText etCaptcha) {
        this.document = document;
        this.etEmailUser = etEmailUser;
        this.etPassword = etPassword;
        this.etCaptcha = etCaptcha;
    }

    @Override
    public void onClick(View view) {
        int countEmptyFields = 3;

        String user = etEmailUser.getText().toString();
        if (!user.isEmpty()) {
            countEmptyFields--;
            if (!user.contains("@")) {
                user += "@nauta.cu";
            }
        }

        String password = etPassword.getText().toString();
        if (!password.isEmpty()) {
            countEmptyFields--;
        }

        String captcha = etCaptcha.getText().toString();
        if (!captcha.isEmpty()) {
            countEmptyFields--;
        }

        if (countEmptyFields == 0 && document != null) {

            String token = Objects.requireNonNull(document.getElementsByAttributeValue("name", "anticsrf").first()).val();

            if (!token.isEmpty()) {
                HttpConnection.KeyVal[] params = {
                        HttpConnection.KeyVal.create("anticsrf", token),
                        HttpConnection.KeyVal.create("horde_user", user),
                        HttpConnection.KeyVal.create("horde_pass", password),
                        HttpConnection.KeyVal.create("captcha_code", captcha)
                };

                new TaskRunnerSingleThreadExecutor().executeAsync(
                        new CallableJsoupResponse(
                                document.connection()
                                        .data(Arrays.asList(params))
                                        .method(Connection.Method.POST)
                        ),
                        (CallbackResponse) mailDocument -> {
                            String axu = String.valueOf(mailDocument.body());
                        }
                );
            }
        }
    }
}
