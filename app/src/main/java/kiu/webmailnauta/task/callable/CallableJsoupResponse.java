package kiu.webmailnauta.task.callable;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;

public class CallableJsoupResponse implements Callable<Document> {

    private final Connection connection;

    public CallableJsoupResponse(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Document call() throws Exception {
        return connection.execute().parse();
    }
}
