package com.laniak.library;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface IHTTPService
{
    public WebClient getWebClient(String uri);
    public Mono<String> getData(String forwardPath  , WebClient client);
}
