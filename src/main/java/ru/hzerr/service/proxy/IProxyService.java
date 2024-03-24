package ru.hzerr.service.proxy;

import java.io.IOException;
import java.util.List;

public interface IProxyService {

    List<Proxy> getProxies() throws IOException, InterruptedException;
    List<Proxy> getProxies(ProxyType proxyType) throws IOException, InterruptedException;
}
