package kiu.webmailnauta.task.callable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.Map;
import java.util.concurrent.Callable;

public class CallableJsoupBitmapCaptcha implements Callable<Bitmap> {
    private final String url;
    private final Map<String, String> cookies;

    public CallableJsoupBitmapCaptcha(String url, Map<String, String> cookies) {
        this.url = url;
        this.cookies = cookies;
    }

    @Override
    public Bitmap call() throws Exception {

        Connection.Response response = Jsoup
                .connect(url)
                .cookies(cookies)
                .ignoreContentType(true)
                .execute();

        byte[] bitmapData = response.bodyAsBytes();

        return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
    }
}
