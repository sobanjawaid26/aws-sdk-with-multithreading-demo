package com.awssdkstarter.aws_sdk_test.controller;

import com.awssdkstarter.aws_sdk_test.service.IAwsSdkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/aws-sdk")
public class AwsSdkController {

    @Autowired
    IAwsSdkService awsSdkService;

    @GetMapping(path = "/getJobResult/{jobId}")
    public String getJobResult(@PathVariable("jobId") Integer jobId) {
        return awsSdkService.getJobResult(jobId);
    }
}
