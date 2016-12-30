package com.amuos.http.httpclient;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by juan.wang on 12/30/16.
 */
public class HttpClientTest {
    // not work: java.lang.IllegalStateException: Target host must not be null, or set in parameters.
    // It's different from version 4.2 and 4.5
    // will start a new httpclient project using version 4.5
    @Test
    public void httpParametersTest() {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter("http.socket.timeout", new Integer(1000));
        httpclient.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        // HostConfiguration is only supported by httpclient 3.x
//        HostConfiguration hostconfig = new HostConfiguration();
//        hostconfig.setHost("www.yahoo.com");
//        hostconfig.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_0);

        URI uri = null;
        HttpGet httpget = null;

        try {
            uri = new URI("www.yahoo.com");
            httpget = new HttpGet(uri);
            httpget.getParams().setParameter("http.socket.timeout", new Integer(5000));
            // Internally the parameter collections will be linked together
            // by performing the following operations:
            // hostconfig.getParams().setDefaults(httpclient.getParams());
            // httpget.getParams().setDefaults(hostconfig.getParams());

            // httpclient.execute() is used in 4.2; in 4.5, use executeMethod(HttpMethod)
            httpclient.execute(httpget);
            System.out.println(httpget.getParams().getParameter("http.protocol.version"));
            System.out.println(httpget.getParams().getParameter("http.socket.timeout"));
            System.out.println(httpget.getParams().getParameter("http.protocol.content-charset"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpget.releaseConnection();
        }
    }
}
