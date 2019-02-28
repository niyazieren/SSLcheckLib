package com.niyazieren.sslchecklib;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

public class SslInfoFetcher {

    static {
        init();
    }

    public static void init(){
        MyManager trm = new MyManager();

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            sc.init(null, new TrustManager[] { trm }, null);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    public SslInfo fetchInfo(String url) throws SslInfoFetchException {
        URL destinationURL = null;
        try {
            destinationURL = new URL(url);
        } catch (MalformedURLException e) {
            throw new SslInfoFetchException("Bad URL", e);
        }

        HttpsURLConnection conn = null;
        try {
            conn = (HttpsURLConnection) destinationURL.openConnection();
        } catch (IOException e) {
            throw new SslInfoFetchException("Unable to open connection to URL", e);
        }


        try {
            conn.connect();
        } catch (IOException e) {
            throw new SslInfoFetchException("Unable to connect to URL", e);
        }

        Certificate[] certs;

        try {
            certs = conn.getServerCertificates();
        } catch (SSLPeerUnverifiedException e) {
            throw new SslInfoFetchException("Unable to verify peer", e);
        }

        if (certs == null || certs.length == 0) {
            throw new SslInfoFetchException("No certs reeceived");
        }

        X509Certificate firstCert = (X509Certificate) certs[0];

        boolean valid = false;

        try {
            firstCert.checkValidity();
            valid = true;
        } catch (Exception e) {
        }

        Date startDate = firstCert.getNotBefore();
        Date endDate = firstCert.getNotAfter();

        return new SslInfo(valid, startDate, endDate);
    }

}
