package com.laniak.library;

import com.sun.jndi.toolkit.url.Uri;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
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
        return  client;
    }

    public WebClient.ResponseSpec getData(String forwardPath  , WebClient client)  {
        return  client.get()
                .uri(forwardPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }

    public WebClient.ResponseSpec getData(URI forwardPath  , WebClient client)  {
        return  client.get()
                .uri(forwardPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }

    public WebClient.ResponseSpec postData(URI forwardPath, Object body, Class modelclass, WebClient client)
    {
        return client.post()
                .uri(forwardPath)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(body),modelclass)
                .retrieve();
    }


    public WebClient.ResponseSpec putData(URI forwardPath, Object body, Class modelclass, WebClient client)
    {
        return client.put()
                .uri(forwardPath)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(body),modelclass)
                .retrieve();
    }

    public WebClient.ResponseSpec deleteData(URI forwardPath, Object body, Class modelclass, WebClient client)
    {
        return client.method(HttpMethod.DELETE)
                .uri(forwardPath)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(body),modelclass)
                .retrieve();
    }

}
