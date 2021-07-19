package com.example.yeecc.awss3;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpClient {
    //target url
    private static String url = "http://localhost:8080/test";

    public static HttpResponse excute(String targetJsonStr) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient closeableHttpClient = getIgnoreSSLClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","application/json");

        StringEntity stringEntity = new StringEntity(targetJsonStr);
        httpPost.setEntity(stringEntity);

        HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
        return httpResponse;
    }

    public static CloseableHttpClient getIgnoreSSLClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        }).build();

        CloseableHttpClient client = HttpClients.custom()
                .setSslcontext(sslContext)
                .useSystemProperties()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        return client;
    }
}
