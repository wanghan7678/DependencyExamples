package com.example.wanghan.controller;

import com.example.wanghan.service.DependencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;


@RestController
@RequestMapping("/checkdependency")
public class CheckDependenciesResource
{
    private final DependencyService dependencyService;

    public CheckDependenciesResource(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    @GetMapping("/{filepath}")
    public ResponseEntity<Boolean> checkDependency(@PathVariable("filepath") String filePathEncoded) throws Exception
    {
        String filePath = new String(Base64.getDecoder().decode(filePathEncoded));
        Boolean ifPassed = dependencyService.checkDependencies(filePath);
        return new ResponseEntity<>(ifPassed, HttpStatus.OK);
    }

}
