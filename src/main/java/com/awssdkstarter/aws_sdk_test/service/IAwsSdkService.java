package com.awssdkstarter.aws_sdk_test.service;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IAwsSdkService {

    void getJobResult(Integer jobId);
    List<String> getDiscoveryResult(String service);
}
