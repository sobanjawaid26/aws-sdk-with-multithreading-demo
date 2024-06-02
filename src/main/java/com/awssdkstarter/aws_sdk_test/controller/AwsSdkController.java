package com.awssdkstarter.aws_sdk_test.controller;

import com.amazonaws.Response;
import com.awssdkstarter.aws_sdk_test.service.IAwsSdkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/aws-sdk")
public class AwsSdkController {

    @Autowired
    IAwsSdkService awsSdkService;

    @GetMapping(path = "/getJobResult/{jobId}")
    public ResponseEntity getJobResult(@PathVariable("jobId") Integer jobId) {
        awsSdkService.getJobResult(jobId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/getDiscoveryResult/{service}")
    public ResponseEntity getDiscoveryResult(@PathVariable("service") String service) {
        List<String> res = awsSdkService.getDiscoveryResult(service);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
