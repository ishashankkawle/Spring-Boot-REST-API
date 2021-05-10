package com.laniak.API.Controller;

import com.laniak.library.HTTPServiceModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetlabController
{
    @Autowired
    private HTTPServiceModule httpServiceModule;

    @GetMapping("/All")
    public String getAllFolder()
    {
        return "Hello";
    }
}
