package ru.hzerr.configuration.database;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

public class ProxyTest {

    @Test
    public void proxyTest() throws IOException, InterruptedException {
        System.out.println(HttpClient.newHttpClient().send(HttpRequest.newBuilder().GET().uri(URI.create("https://www.proxy-list.download/api/v1/get?type=socks5")).build(), respInfo ->
                HttpResponse.BodySubscribers.ofString(Charset.defaultCharset())
        ).body());
    }
}
