package com.nitinsurana;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class UrlTask implements Callable<Boolean>, Supplier<Boolean> {
    private String httpUrl;

    public UrlTask(String httpUrl) {
        super();
        this.httpUrl = httpUrl;
    }


    @Override
    public Boolean call() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpHead httpHead = new HttpHead(this.httpUrl);
        CloseableHttpResponse response1 = httpclient.execute(httpHead);
        try {
            int code = response1.getStatusLine().getStatusCode();
            if (code == 200) {
                return Boolean.TRUE;
            }
        } finally {
            response1.close();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean get() {
        try {
            return call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
