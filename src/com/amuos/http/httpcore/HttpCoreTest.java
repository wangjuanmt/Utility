package com.amuos.http.httpcore;

import org.apache.http.*;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Test;

/**
 * Created by juan.wang on 12/30/16.
 */
public class HttpCoreTest {
    @Test
    public void httpRequestTest() {
        HttpRequest request = new BasicHttpRequest("GET", "/",
                HttpVersion.HTTP_1_1);

        System.out.println(request.getRequestLine().getMethod());
        System.out.println(request.getRequestLine().getUri());
        System.out.println(request.getProtocolVersion());
        System.out.println(request.getRequestLine().toString());
    }

    @Test
    public void httpResponseTest() {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");

        System.out.println(response.getProtocolVersion());
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getStatusLine().getReasonPhrase());
        System.out.println(response.getStatusLine().toString());
    }

    @Test
    public void httpMessagePropertiesTest() {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");
        response.addHeader("Set-Cookie",
                "c1=a; path=/; domain=localhost");
        response.addHeader("Set-Cookie",
                "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
        Header h1 = response.getFirstHeader("Set-Cookie");
        System.out.println(h1);
        Header h2 = response.getLastHeader("Set-Cookie");
        System.out.println(h2);
        Header[] hs = response.getHeaders("Set-Cookie");
        System.out.println(hs.length);

        // There is an efficient way to obtain all headers of a given type using the HeaderIterator interface.
        HeaderIterator it = response.headerIterator("Set-Cookie");

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    @Test
    public void httpMessageParseTest() {
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");
        response.addHeader("Set-Cookie",
                "c1=a; path=/; domain=localhost");
        response.addHeader("Set-Cookie",
                "c2=b; path=\"/\", c3=c; domain=\"localhost\"");

        HeaderElementIterator it = new BasicHeaderElementIterator(
                response.headerIterator("Set-Cookie"));

        // parse HTTP messages into individual header elements.
        while (it.hasNext()) {
            HeaderElement elem = it.nextElement();
            System.out.println(elem.getName() + " = " + elem.getValue());
            NameValuePair[] params = elem.getParameters();
            for (int i = 0; i < params.length; i++) {
                System.out.println(" " + params[i]);
            }
        }
    }

}
