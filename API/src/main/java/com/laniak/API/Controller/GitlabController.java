package com.laniak.API.Controller;

import com.laniak.API.Configurations.GitlabConfiguration;
import com.laniak.API.Model.FileData;
import com.laniak.API.Model.ItemData;
import com.laniak.library.HTTPServiceModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;


@SpringBootApplication(scanBasePackages = "com.laniak.library")
@RestController
public class GitlabController
{
    @Autowired
    private HTTPServiceModule httpServiceModule;

    @Autowired
    private GitlabConfiguration config;

    @GetMapping("/folders")
    public ItemData[] getRecursiveRootData(@RequestParam(name = "recursive" , defaultValue = "false") String recOption)
    {
        //Map headers = new HashMap();
        //headers.put("PRIVATE_TOKEN" , config.getKey());
        //WebClient client = httpServiceModule.getWebClientWithCustomHeader(config.getBaseUrl(),headers);
        ItemData[] data;
        String path = config.getBaseUrl() + "/tree";
        if(recOption.equals("true"))
        {
            path = path + "?recursive=true";
        }

        WebClient client = httpServiceModule.getWebClient(config.getBaseUrl());
        WebClient.ResponseSpec response = httpServiceModule.getData( path , client);
        data = response.bodyToMono(ItemData[].class).block();
        return data;
    }

    @GetMapping("/folder/{folderId}")
    public ItemData[] getFolderData(@PathVariable(value = "folderId") String folderId)
    {
        ItemData[] data;
        String path = config.getBaseUrl() + "/tree?path=" + folderId;
        WebClient client = httpServiceModule.getWebClient(config.getBaseUrl());
        WebClient.ResponseSpec response = httpServiceModule.getData( path , client);
        data = response.bodyToMono(ItemData[].class).block();
        return data;
    }


    @GetMapping("/folder/{folderId}/file/{fileId}")
    public FileData getFileData(@PathVariable(value = "folderId") String folderId , @PathVariable(value = "fileId") String fileId)
    {
        FileData data;
        URI uri = null;
        String queryString =  folderId + "%2F" + fileId.replace("." , "%2E");
        String path = config.getBaseUrl() + "/files/" + queryString + "?ref=master";

        try {
            uri = new URI(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        WebClient client = httpServiceModule.getWebClient(config.getBaseUrl());
        WebClient.ResponseSpec response = httpServiceModule.getData( uri , client);
        data = response.bodyToMono(FileData.class).block();
        return data;
    }

}
