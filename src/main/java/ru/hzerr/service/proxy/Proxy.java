package ru.hzerr.service.proxy;

public class Proxy {

    private String ip;
    private String port;
    private ProxyType proxyType;

    private Proxy(String ip, String port, ProxyType proxyType) {
        this.setIp(ip);
        this.setPort(port);
        this.setProxyType(proxyType);
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    public static Proxy from(String ip, String port, ProxyType proxyType) {
        return new Proxy(ip, port, proxyType);
    }
}
