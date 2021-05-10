package com.laniak.API.Controller;

import com.laniak.library.HTTPServiceModule;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.laniak.library")
@RestController
public class GitlabController
{
    private HTTPServiceModule httpServiceModule;

    @GetMapping("/all")
    public String getAllFolder()
    {
        return "Hello";
    }
}
