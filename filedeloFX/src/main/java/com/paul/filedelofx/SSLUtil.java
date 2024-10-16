package com.paul.filedelofx;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

public class SSLUtil {
    // Méthode pour obtenir un OkHttpClient qui ignore les certificats SSL

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Création d'un TrustManager qui n'exige aucune vérification des certificats
            final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
            };

            // Installation de ce TrustManager dans un SSLContext
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Création d'un SSLSocketFactory à partir de notre SSLContext
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Construction d'un OkHttpClient avec notre SSLSocketFactory et un HostnameVerifier permissif
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            return builder.cookieJar(new JavaNetCookieJar(cookieManager)).build();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
