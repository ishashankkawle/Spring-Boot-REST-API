package com.laniak.library;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class HTTPServiceModule implements IHTTPService
{

    public WebClient getWebClient(String baseUri)
    {
        return WebClient.create(baseUri);
    }

    public WebClient getWebClientWithCustomHeader( String baseUri , Map headerMap)
    {
        WebClient client = WebClient.builder()
                .defaultHeaders(httpHeaders -> headerMap.forEach((x,y)-> httpHeaders.add(x.toString() , y.toString())))
                .build();
        System.out.println("INSIDE TAR : " + client.toString());
        return  client;
    }

    public Mono<String> getData(String forwardPath  , WebClient client)
    {
        Mono<String> data =  client
                .get()
                .uri(forwardPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(d -> {
                    System.out.println("SUCCESS : " + d.toString());
                })
                .doOnError(e -> System.out.println("ERROR"));

        return data;
    }

}
