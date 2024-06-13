package com.adobe.aem.support.proxy.external;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public final class ExternalProxyRequest {

    private final CloseableHttpClient httpclient;
    private String url;

    public ExternalProxyRequest(CloseableHttpClient httpclient, String url) {
        this.httpclient = httpclient;
        this.url = url;
    }

    public CloseableHttpResponse makeRequest() throws IOException {
        HttpGet get = new HttpGet(this.url);
        return this.httpclient.execute(get);
    }

    public static class Builder {
        private String url;
        private CloseableHttpClient httpclient = HttpClients.createDefault();

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setHttpClient(CloseableHttpClient httpclient) {
            this.httpclient = httpclient;
            return this;
        }

        public ExternalProxyRequest build() {
            return new ExternalProxyRequest(httpclient, url);
        }

    }

}
