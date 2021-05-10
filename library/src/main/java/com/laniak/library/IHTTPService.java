package com.laniak.library;

import org.springframework.web.reactive.function.client.WebClient;

public interface IHTTPService
{
    public WebClient getWebClient(String uri);
    public WebClient.ResponseSpec getData(String forwardPath  , WebClient client);
}
