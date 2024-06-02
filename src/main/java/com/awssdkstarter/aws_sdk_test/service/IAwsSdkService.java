package com.awssdkstarter.aws_sdk_test.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface IAwsSdkService {

    String getJobResult(Integer jobId);
}
