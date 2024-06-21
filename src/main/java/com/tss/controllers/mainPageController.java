package com.tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for displaying environment variables and project data on main page "index.html"
 * The listed data is declared in the application.properties file
 */
@Controller
@RequestMapping("/")
public class mainPageController {

    @Autowired
    BuildProperties buildProperties;

    @Value("${myparams.jdkversion}")
    String myjdkversion;

    @Value("${myparams.springbootversion}")
    String springbootversion;

    @Value("${application.name}")
    String applicationName;

    @Value("${build.version}")
    String buildVersion;

    @Value("${build.timestamp}")
    String buildTimestamp;

    
    @GetMapping({"", "/"})
    public String getPage(Model model) {
        String artifacteApp = buildProperties.getArtifact();
        String versionApp = buildProperties.getVersion();
        String springVersion=SpringVersion.getVersion();
        model.addAttribute("myjdkversion",myjdkversion);
        model.addAttribute("springbootversion",springbootversion);
        
        model.addAttribute("springVersion",springVersion);
        
        model.addAttribute("applicationName",applicationName);
        model.addAttribute("buildVersion",buildVersion);
        model.addAttribute("buildTimestamp",buildTimestamp);

        return "index";
    }
}
