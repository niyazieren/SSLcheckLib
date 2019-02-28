package com.niyazieren.sslchecklib;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class SslInfoFetcherTest {

    @Test
    public void fetchInfoValid() throws SslInfoFetchException {
        SslInfoFetcher fetcher =new SslInfoFetcher();

        SslInfo ınfo = fetcher.fetchInfo("https://google.com");

        assertTrue(ınfo.isValid());
    }

    @Test
    public void fetchInfoInvalid() throws SslInfoFetchException {
        SslInfoFetcher fetcher =new SslInfoFetcher();

        SslInfo info = fetcher.fetchInfo("https://expired.badssl.com");

        assertFalse(info.isValid());

        assertEquals(info.getStartDate().getYear()+1900,2015);
        assertEquals(info.getStartDate().getMonth()+1,4);
        assertEquals(info.getStartDate().getDate(),9);

        assertEquals(info.getEndDate().getYear()+1900,2015);
        assertEquals(info.getEndDate().getMonth()+1,4);
        assertEquals(info.getEndDate().getDate(),13);


    }

    @Test(expected = SslInfoFetchException.class)
    public void fetchInfoInvalidURL() throws SslInfoFetchException {
        SslInfoFetcher fetcher =new SslInfoFetcher();

        fetcher.fetchInfo("NIYAZI");
    }
    @Test(expected = SslInfoFetchException.class)
    public void fetchInfoNotExistURL() throws SslInfoFetchException {
        SslInfoFetcher fetcher =new SslInfoFetcher();

        fetcher.fetchInfo("https://niyaziii.com");
    }
    @Test(expected = SslInfoFetchException.class)
    public void fetchInfoNotSafeURL() throws SslInfoFetchException {
        SslInfoFetcher fetcher =new SslInfoFetcher();

        fetcher.fetchInfo("http://aksis.istanbul.edu.tr");
    }
}