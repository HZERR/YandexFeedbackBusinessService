package ru.hzerr.service.proxy;

public enum ProxyType {
    HTTP("http"),
    HTTPS("https"),
    SOCKS4("socks4"),
    SOCKS5("socks5");

    private final String proxyType;

    ProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public String getProxyTypeAsString() {
        return proxyType;
    }
}
