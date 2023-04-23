package com.kapital.onlinepaymentgateway.kapitalecommerce.util;

import com.kapital.onlinepaymentgateway.kapitalecommerce.error.CustomSslException;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.response.ResponseMessage;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Component
public class HttpUtil {

    @Value("${kapital.keystore.path}")
    private String kapitalKeystorePath;

    @Value("${kapital.keystore.password}")
    private String kapitalKeystorePassword;

    public HttpClient createHttpClientWithSsl() {
        try {
            var ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(new BufferedInputStream(new FileInputStream(kapitalKeystorePath)), kapitalKeystorePassword.toCharArray());
            var sslContext = SSLContexts.custom().loadKeyMaterial(ks, kapitalKeystorePassword.toCharArray()).loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            var sslConnectionFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            var registry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslConnectionFactory).register("http", new PlainConnectionSocketFactory()).build();
            return HttpClients.custom().setConnectionManager(new BasicHttpClientConnectionManager(registry)).setSSLSocketFactory(sslConnectionFactory).build();
        } catch (CertificateException | KeyStoreException | KeyManagementException | UnrecoverableKeyException |
                 NoSuchAlgorithmException | IOException e) {
            throw new CustomSslException(ResponseMessage.SSL_EXCEPTION_OCCURRED, e, e.getMessage());
        }

    }

    @SneakyThrows
    public HttpResponse sendPostRequest(String url, StringEntity body) {
       var client = createHttpClientWithSsl();
        var post = new HttpPost(url);
        post.setEntity(body);
        post.setHeader("Content-Type", "application/xml");
        return client.execute(post);
    }
}
