package com.awssdkstarter.aws_sdk_test.service;

import com.awssdkstarter.aws_sdk_test.threads.EC2InstancesThread;
import com.awssdkstarter.aws_sdk_test.threads.S3BucketThread;
import org.springframework.stereotype.Service;

@Service
public class AwsSdkService implements IAwsSdkService{
    @Override
    public String getJobResult(Integer jobId) {
        System.out.println("AwsSdkService.getJobResult() is getting executed from thread : " +
                Thread.currentThread().getName());
        Thread tS3 = new Thread(new S3BucketThread());
        Thread tEC2 = new Thread(new EC2InstancesThread());
        tS3.start();
        tEC2.start();
        return "JobId-" + jobId;
    }

}
