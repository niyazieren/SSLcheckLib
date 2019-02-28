package com.niyazieren.sslchecklib;

public class SslInfoFetchException extends Exception {
    public SslInfoFetchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SslInfoFetchException(String msg) {
        super(msg);
    }
}
