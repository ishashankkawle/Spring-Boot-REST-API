package com.laniak.library;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

}
