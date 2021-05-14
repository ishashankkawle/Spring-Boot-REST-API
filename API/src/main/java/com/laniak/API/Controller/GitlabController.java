package com.laniak.API.Controller;

import com.laniak.API.Configurations.GitlabConfiguration;
import com.laniak.library.HTTPServiceModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = "com.laniak.library")
@RestController
public class GitlabController
{
    @Autowired
    private HTTPServiceModule httpServiceModule;

    @Autowired
    private GitlabConfiguration config;

    @GetMapping("/folders")
    public String getAllFolder()
    {
        Map headers = new HashMap();
        headers.put("PRIVATE_TOKEN" , config.getKey());

        WebClient client = httpServiceModule.getWebClientWithCustomHeader(config.getBaseUrl(),headers);
        Mono<String> response = httpServiceModule.getData("/tree",client);
        System.out.println(response.toString());
        return  "null";
    }
}
