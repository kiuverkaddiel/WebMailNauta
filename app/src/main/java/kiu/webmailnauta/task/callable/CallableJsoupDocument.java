package kiu.webmailnauta.task.callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;

public class CallableJsoupDocument implements Callable<Document> {
    private final String url;

    public CallableJsoupDocument(String url) {
        this.url = url;
    }

    @Override
    public Document call() throws Exception {
        return Jsoup.connect(url).get();
    }
}
