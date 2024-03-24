package ru.hzerr.service.proxy;

import org.apache.commons.lang3.RandomUtils;
import ru.hzerr.fx.engine.core.annotation.Registered;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Registered
public class ProxyService implements IProxyService {

    private final String API_URL = "https://www.proxy-list.download/api/v1/get";
    private final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private final HttpResponse.BodyHandler<String> DEFAULT_BODY_HANDLER = responseInfo -> HttpResponse.BodySubscribers.ofString(Charset.defaultCharset());

    @Override
    public List<Proxy> getProxies() throws IOException, InterruptedException {
        int proxyTypeAsInteger = RandomUtils.nextInt(0, 4);
        ProxyType proxyType = switch (proxyTypeAsInteger) {
            case 0:
                yield ProxyType.HTTP;
            case 1:
                yield ProxyType.HTTPS;
            case 2:
                yield ProxyType.SOCKS4;
            case 3:
                yield ProxyType.SOCKS5;
            default:
                throw new IllegalStateException(STR."Unexpected value: \{proxyTypeAsInteger}");
        };

        return getProxies(proxyType);
    }

    @Override
    public List<Proxy> getProxies(ProxyType proxyType) throws IOException, InterruptedException {
        String body = HTTP_CLIENT.send(HttpRequest.newBuilder().GET().uri(createURI(proxyType.getProxyTypeAsString())).build(), DEFAULT_BODY_HANDLER).body();
        return toList(body, proxyType);
    }

    private URI createURI(String proxyType) {
        return URI.create(STR."\{API_URL}?type=\{proxyType}");
    }

    private List<Proxy> toList(String body, ProxyType proxyType) {
        return Arrays.stream(body.split("\n\r"))
                .map(data -> {
                    String[] splitData = data.split(":");
                    return Proxy.from(splitData[0], splitData[1], proxyType);
                }).collect(Collectors.toList());
    }
}
