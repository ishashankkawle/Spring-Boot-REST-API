package com.laniak.API.Configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class GitlabConfiguration
{
    private String key = "PPqja9g1aKVbsYmhszrL";
    private String baseUrl = "https://gitlab.com/api/v4/projects/8300723/repository";


    public String getKey() {
        return key;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
