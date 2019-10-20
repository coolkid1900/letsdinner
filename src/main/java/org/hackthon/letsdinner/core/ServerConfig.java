package org.hackthon.letsdinner.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServerConfig{
    @Value("${app.httpUrl}")
    private String httpUrl;
    @Value("${app.wsUrl}")
    private String wsUrl;

    public String getHttpUrl()
    {
        return httpUrl;
    }

    public String getWsUrl()
    {
        return wsUrl;
    }
}
