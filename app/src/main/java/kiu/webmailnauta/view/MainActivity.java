package kiu.webmailnauta.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import kiu.webmailnauta.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String WEBMAIL_URL = "https://webmail.nauta.cu/login.php";
    private Document htmlDocument;
    private Button btnLogin;
    private static final String javascriptCodeSubmitForm = """
                function submitForm() {
                    var submitBtn = document.querySelector('input[type="submit"]');
                    if (submitBtn) {
                        submitBtn.click();
                    }
                }
                submitForm();
            """;

    private static final String javascriptCodeNextMessage = """
                (function() {
                        if (window.ImpMobile && typeof ImpMobile.navigateMessage === 'function') {
                                    ImpMobile.navigateMessage(1);
                                }
                    })();
            """;

    private WebView webView;
    private static final int PERMISSION_CODE = 100;
    private String pendingEncryptedText;
    private String pendingDate;
    private String pendingSubject;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.wv_nauta);
        webView.getSettings().setJavaScriptEnabled(true);

        ImpWebClient impWebClient = new ImpWebClient();
        webView.setWebViewClient(impWebClient);
        webView.addJavascriptInterface(this, "loadListener");

        webView.loadUrl(WEBMAIL_URL);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnSaveList = findViewById(R.id.bt_save_list);
        btnSaveList.setOnClickListener(v -> {
            if (htmlDocument != null) {
                webView.reload();
                // wait 5 seconds and reload the page to ensure the next message is loaded
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Element div = htmlDocument.selectFirst("#imp-message-body");
                    if (div == null) return;
                    String date = extractMessageDate();
                    if (date == null || date.isEmpty()) return;
                    String subject = extractMessageSubject();
                    if (subject == null || subject.isEmpty()) return;

                    div.select("br").remove(); // Remove <br> tags
                    String encryptedText = div.text();
                    saveEncryptedText(encryptedText, date, subject);
                }, 5000);
            }
        });

        btnLogin = findViewById(R.id.bt_login);
        btnLogin.setOnClickListener(v -> {
            webView.evaluateJavascript(javascriptCodeSubmitForm, null);
            btnLogin.setVisibility(Button.GONE);
        });

        Button nextMessageButton = findViewById(R.id.bt_next);
        nextMessageButton.setOnClickListener(v -> webView.evaluateJavascript(javascriptCodeNextMessage, null));

        Button reloadButton = findViewById(R.id.bt_reload);
        reloadButton.setOnClickListener(v -> webView.reload());

        Button saveAllButton = findViewById(R.id.bt_save_all_list);
        saveAllButton.setOnClickListener(v -> saveAllMessages(null));
    }

    private void saveAllMessages(String messageTurn) {
        if (messageTurn == null) {
            messageTurn = getMessageTurn();
        }
        final String currentTurn = messageTurn;

        webView.reload();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // wait 5 seconds and reload the page to ensure the next message is loaded
            Element div = htmlDocument.selectFirst("#imp-message-body");
            if (div == null) return;
            String date = extractMessageDate();
            if (date == null || date.isEmpty()) return;
            String subject = extractMessageSubject();
            if (subject == null || subject.isEmpty()) return;

            div.select("br").remove(); // Remove <br> tags
            String encryptedText = div.text();
            saveEncryptedText(encryptedText, date, subject);

            webView.evaluateJavascript(javascriptCodeNextMessage, null);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // wait 3 seconds and reload the page to ensure the next message is loaded
                if (Objects.equals(currentTurn, getMessageTurn())) {
                    saveAllMessages(currentTurn);
                }
            }, 3000);
        }, 5000);
    }

    private String getMessageTurn() {
        if (htmlDocument == null) return null;

        Element div = htmlDocument.selectFirst("#imp-message-body");
        if (div == null) return null;
        String date = extractMessageDate();

        Date parsedDate = parseMessageDate(date);
        if (parsedDate == null) {
            Toast.makeText(this, "Invalid date format: " + date, Toast.LENGTH_SHORT).show();
            return null;
        }

        return getTurnByDate(parsedDate);
    }

    private String getTurnByDate(Date parsedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String turn = "OUT";
        if (hour == 7 || (hour > 7 && hour < 13) || (hour == 13 && minute <= 30)) {
            turn = "DIA";
        } else if (hour == 14 || (hour > 14 && hour < 21) || (hour == 21 && minute <= 30)) {
            turn = "NOCHE";
        }

        SimpleDateFormat folderDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String folderName = folderDateFormat.format(parsedDate) + "-" + turn;
        // remove zero on the day and month if present
        folderName = folderName.replaceAll("(^|-)0", "$1");
        return folderName;
    }

    private void saveEncryptedText(String content, String date, String subject) {
        if (content == null || content.isEmpty()) {
            Toast.makeText(this, "No content to save", Toast.LENGTH_SHORT).show();
            return;
        }

        Date parsedDate = parseMessageDate(date);
        if (parsedDate == null) {
            Toast.makeText(this, "Invalid date format: " + date, Toast.LENGTH_SHORT).show();
            return;
        }

        String folderName = getTurnByDate(parsedDate);

        // create folder in Downloads
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File folder = new File(downloadsDir, folderName);
        if (!folder.exists() && !folder.mkdirs()) {
            Log.w(TAG, "Failed to create directory: " + folder.getAbsolutePath());
            Toast.makeText(this, "Cannot create directory: " + folder.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = subject + ".txt";

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_DOWNLOADS + "/" + folderName);

                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    try (OutputStream os = getContentResolver().openOutputStream(uri)) {
                        if (os != null) {
                            os.write(content.getBytes(StandardCharsets.UTF_8));
                            Toast.makeText(this, "Saved to Downloads: " + fileName, Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "Failed to create file in Downloads", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    pendingEncryptedText = content;
                    pendingDate = date;
                    pendingSubject = subject;
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_CODE);
                    return;
                }

                downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!downloadsDir.exists() && !downloadsDir.mkdirs()) {
                    Log.w(TAG, "Failed to create directory: " + downloadsDir.getAbsolutePath());
                }
                File file = new File(downloadsDir, fileName);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(content.getBytes(StandardCharsets.UTF_8));
                    Toast.makeText(this, "Saved to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error saving file", e);
            Toast.makeText(this, "Error saving file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (pendingEncryptedText != null) {
                    saveEncryptedText(pendingEncryptedText, pendingDate, pendingSubject);
                    pendingEncryptedText = null;
                    pendingDate = null;
                }
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @android.webkit.JavascriptInterface
    public void onLoadComplete(String html) {

        htmlDocument = Jsoup.parse(html);
        Element form = htmlDocument.selectFirst("input[type=\"submit\"]");
        if (form != null) {
            runOnUiThread(() -> btnLogin.setVisibility(Button.VISIBLE));
        } else {
            runOnUiThread(() -> btnLogin.setVisibility(Button.GONE));
        }

//        Element div = htmlDocument.selectFirst("#imp-message-body div.fixed.leftAlign");
//        if (div != null) {
//            runOnUiThread(() -> findViewById(R.id.bt_save_list).setVisibility(Button.VISIBLE));
//        } else {
//            runOnUiThread(() -> findViewById(R.id.bt_save_list).setVisibility(Button.GONE));
//        }
    }

    private String extractMessageDate() {
        if (htmlDocument == null) return null;
        Element dateLabel = htmlDocument.getElementById("imp-message-date");
        return dateLabel != null ? dateLabel.text() : null;
    }

    private String extractMessageSubject() {
        if (htmlDocument == null) return null;
        Element message = htmlDocument.getElementById("message");
        if (message == null || message.text().isEmpty())
            return null;
        Element subjectLabel = message.selectFirst("h1.smartmobile-title");
        return subjectLabel != null ? subjectLabel.text() : null;
    }

    private static Date parseMessageDate(String rawDate) {
        if (rawDate == null || rawDate.trim().isEmpty()) {
            return null;
        }

        String trimmed = rawDate.trim();
        String lower = trimmed.toLowerCase(Locale.US);

        int dayOffset = Integer.MIN_VALUE;
        String timePart = null;

        if (lower.startsWith("today,") || lower.startsWith("hoy,")) {
            timePart = trimmed.substring(trimmed.indexOf(',') + 1).trim();
            dayOffset = 0;
        }

        if (dayOffset != Integer.MIN_VALUE) {
            String[] timePatterns = {
                    "h:mm:ss a z",
                    "h:mm:ss a",
                    "hh:mm:ss a z",
                    "hh:mm:ss a",
                    "HH:mm:ss z",
                    "HH:mm:ss",
                    "h:mm a z",
                    "h:mm a",
                    "HH:mm z",
                    "HH:mm"
            };

            for (String pattern : timePatterns) {
                try {
                    SimpleDateFormat timeFormat = new SimpleDateFormat(pattern, Locale.US);
                    Date parsedTime = timeFormat.parse(timePart);
                    if (parsedTime != null) {
                        TimeZone tz = timeFormat.getTimeZone();
                        Calendar calendar = Calendar.getInstance(tz);
                        calendar.add(Calendar.DAY_OF_YEAR, dayOffset);

                        Calendar timeCal = Calendar.getInstance(tz);
                        timeCal.setTime(parsedTime);

                        calendar.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
                        calendar.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
                        calendar.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
                        calendar.set(Calendar.MILLISECOND, 0);

                        return calendar.getTime();
                    }
                } catch (ParseException ignored) {
                }
            }
        }

        String[] fullPatterns = {
                "MM/dd/yyyy ( h:mm:ss a z)",
                "MM/dd/yyyy (h:mm:ss a z)",
                "dd/MM/yyyy ( h:mm:ss a z)",
                "dd/MM/yyyy (h:mm:ss a z)",
                "MM/dd/yyyy, h:mm:ss a z",
                "MM/dd/yyyy h:mm:ss a z",
                "dd/MM/yyyy, h:mm:ss a z",
                "dd/MM/yyyy h:mm:ss a z",
                "MM/dd/yy, h:mm:ss a z",
                "MM/dd/yy h:mm:ss a z",
                "dd/MM/yy, h:mm:ss a z",
                "dd/MM/yy h:mm:ss a z",
                "MM/dd/yyyy, h:mm:ss a",
                "dd/MM/yyyy, h:mm:ss a",
                "yyyy-MM-dd HH:mm:ss z",
                "yyyy-MM-dd HH:mm:ss",
                "EEE, d MMM yyyy HH:mm:ss Z",
                "d MMM yyyy HH:mm:ss Z"
        };

        for (String pattern : fullPatterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
                return sdf.parse(trimmed);
            } catch (ParseException ignored) {
            }
        }

        Log.e(TAG, "Unable to parse date: " + rawDate);
        return null;
    }
}