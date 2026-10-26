package kiu.webmailnauta.view;

import static kiu.webmailnauta.view.Extractor.getTurnByDate;
import static kiu.webmailnauta.view.Extractor.parseMessageDate;
import static kiu.webmailnauta.view.JsCodes.javascriptCodeNextMessage;
import static kiu.webmailnauta.view.JsCodes.javascriptCodeSubmitForm;

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
import android.view.View;
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
import java.util.Date;
import java.util.Objects;

import kiu.webmailnauta.R;
import kiu.webmailnauta.model.SavedListDao;
import kiu.webmailnauta.view.filter.FilterManager;
import kiu.webmailnauta.view.filter.IFilter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String WEBMAIL_URL = "https://webmail.nauta.cu/login.php";
    private Document htmlDocument;
    private Button btnLogin;

    private FilterManager filterManager;

    private final Extractor extractor = new Extractor();

    private WebView webView;
    private static final int PERMISSION_CODE = 100;
    private String pendingEncryptedText;
    private String pendingDate;
    private String pendingSubject;
    private SavedListDao savedListDao;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        savedListDao = new SavedListDao(this);

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
                    String date = extractor.extractMessageDate();
                    if (date == null || date.isEmpty()) return;
                    String subject = extractor.extractMessageSubject();
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
            btnLogin.setVisibility(View.GONE);
        });

        Button nextMessageButton = findViewById(R.id.bt_next);
        nextMessageButton.setOnClickListener(v -> webView.evaluateJavascript(javascriptCodeNextMessage, null));

        Button reloadButton = findViewById(R.id.bt_reload);
        reloadButton.setOnClickListener(v -> webView.reload());

        Button saveAllButton = findViewById(R.id.bt_save_all_list);
        saveAllButton.setOnClickListener(v -> saveAllMessages(null));

        filterManager = new FilterManager(webView, extractor, btnLogin, savedListDao);
    }

    private void saveAllMessages(String messageTurn) {
        if (messageTurn == null) {
            messageTurn = extractor.getMessageTurn();
        }
        final String currentTurn = messageTurn;

        webView.reload();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // wait 5 seconds and reload the page to ensure the next message is loaded
            Element div = htmlDocument.selectFirst("#imp-message-body");
            if (div == null) return;
            String date = extractor.extractMessageDate();
            if (date == null || date.isEmpty()) return;
            String subject = extractor.extractMessageSubject();
            if (subject == null || subject.isEmpty()) return;

            div.select("br").remove(); // Remove <br> tags
            String encryptedText = div.text();
            saveEncryptedText(encryptedText, date, subject);

            webView.evaluateJavascript(javascriptCodeNextMessage, null);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // wait 3 seconds and reload the page to ensure the next message is loaded
                if (Objects.equals(currentTurn, extractor.getMessageTurn())) {
                    saveAllMessages(currentTurn);
                }
            }, 3000);
        }, 5000);
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
                            savedListDao.save(subject, folderName);
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
                    savedListDao.save(subject, folderName);
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
                    pendingSubject = null;
                }
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @android.webkit.JavascriptInterface
    public void onLoadComplete(String html) {

        btnLogin.setVisibility(View.GONE);
        htmlDocument = Jsoup.parse(html);
        extractor.setDocument(htmlDocument);
        IFilter filter = filterManager.getFilter();
        if (filter != null) {
            runOnUiThread(filter::applyFilter);
        }
    }
}