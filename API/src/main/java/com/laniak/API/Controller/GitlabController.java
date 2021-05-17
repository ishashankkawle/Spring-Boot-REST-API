package com.laniak.API.Controller;

import com.laniak.API.Configurations.GitlabConfiguration;
import com.laniak.API.Model.*;
import com.laniak.library.HTTPServiceModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.net.URISyntaxException;
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
    public ItemData[] getRecursiveRootData(@RequestParam(name = "recursive" , defaultValue = "false") String recOption)
    {
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
        String queryString =  folderId.replace(" " , "%20") + "%2F" + fileId.replace("." , "%2E");
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

    @PostMapping("/folder/{folderId}/file/{fileId}")
    public HTTPOutput writeFileData(@RequestBody FileContentData body , @PathVariable(value = "folderId") String folderId , @PathVariable(value = "fileId") String fileId)
    {
        URI uri = null;
        String queryString =  folderId.replace(" " , "%20") + "%2F" + fileId.replace("." , "%2E");
        String path = config.getBaseUrl() + "/files/" + queryString + "?branch=master";

        try
        {
            uri = new URI(path);
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        Map headers = new HashMap();
        headers.put("PRIVATE-TOKEN" , config.getKey());
        headers.put("Content-Type" , MediaType.APPLICATION_JSON);
        headers.put("Accept" , "*/*");
        HTTPOutput output;
        WebClient client = httpServiceModule.getWebClientWithCustomHeader(config.getBaseUrl(),headers);
        System.out.println("URI : " + uri.toString());

        try
        {
            WebClient.ResponseSpec response = httpServiceModule.postData(uri , body , FileContentData.class , client);
            response.bodyToMono(FileWriteOutput.class).block();
            System.out.println("Write Operation Completed");
            output =  new HTTPOutput(HttpStatus.OK.toString(), "File updated Successfully");
        }
        catch (WebClientResponseException error)
        {
            System.out.println("Write Operation Failed");
            System.out.println(error.getResponseBodyAsString());
            output = new HTTPOutput(error.getStatusCode().toString() , error.getMessage());
        }
        return output;
    }


    @PutMapping("/folder/{folderId}/file/{fileId}")
    public HTTPOutput updateFileData(@RequestBody FileContentData body , @PathVariable(value = "folderId") String folderId , @PathVariable(value = "fileId") String fileId)
    {
        URI uri = null;
        String queryString =  folderId.replace(" " , "%20") + "%2F" + fileId.replace("." , "%2E");
        String path = config.getBaseUrl() + "/files/" + queryString + "?branch=master";

        try
        {
            uri = new URI(path);
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        Map headers = new HashMap();
        headers.put("PRIVATE-TOKEN" , config.getKey());
        headers.put("Content-Type" , MediaType.APPLICATION_JSON);
        headers.put("Accept" , "*/*");
        HTTPOutput output;
        WebClient client = httpServiceModule.getWebClientWithCustomHeader(config.getBaseUrl(),headers);
        System.out.println("URI : " + uri.toString());

        try
        {
            WebClient.ResponseSpec response = httpServiceModule.putData(uri , body , FileContentData.class , client);
            response.bodyToMono(FileWriteOutput.class).block();
            System.out.println("Write Operation Completed");
            output =  new HTTPOutput(HttpStatus.OK.toString(), "File updated Successfully");
        }
        catch (WebClientResponseException error)
        {
            System.out.println("Write Operation Failed");
            System.out.println(error.getResponseBodyAsString());
            output = new HTTPOutput(error.getStatusCode().toString() , error.getMessage());
        }
        return output;
    }

    @DeleteMapping("/folder/{folderId}/file/{fileId}")
    public HTTPOutput deleteFile(@RequestBody FileContentData body , @PathVariable(value = "folderId") String folderId , @PathVariable(value = "fileId") String fileId)
    {
        URI uri = null;
        String queryString =  folderId.replace(" " , "%20") + "%2F" + fileId.replace("." , "%2E");
        String path = config.getBaseUrl() + "/files/" + queryString + "?branch=master";

        try
        {
            uri = new URI(path);
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        Map headers = new HashMap();
        headers.put("PRIVATE-TOKEN" , config.getKey());
        headers.put("Content-Type" , MediaType.APPLICATION_JSON);
        headers.put("Accept" , "*/*");
        HTTPOutput output;
        WebClient client = httpServiceModule.getWebClientWithCustomHeader(config.getBaseUrl(),headers);
        System.out.println("URI : " + uri.toString());

        try
        {
            WebClient.ResponseSpec response = httpServiceModule.deleteData(uri , body , CommitMessage.class , client);
            response.bodyToMono(FileWriteOutput.class).block();
            output =  new HTTPOutput(HttpStatus.OK.toString(), "File deleted Successfully");
        }
        catch (WebClientResponseException error)
        {
            System.out.println(error.getResponseBodyAsString());
            output = new HTTPOutput(error.getStatusCode().toString() , error.getMessage());
        }
        return output;
    }
}
