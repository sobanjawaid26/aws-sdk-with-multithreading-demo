package com.awssdkstarter.aws_sdk_test.service;

import com.awssdkstarter.aws_sdk_test.threads.EC2InstancesThread;
import com.awssdkstarter.aws_sdk_test.threads.S3BucketThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwsSdkService implements IAwsSdkService{
    @Autowired
    S3BucketThread s3BucketThread;
    @Autowired
    EC2InstancesThread ec2InstancesThread;
    @Override
    public String getJobResult(Integer jobId) {
        System.out.println("AwsSdkService.getJobResult() is getting executed from thread : " +
                Thread.currentThread().getName());
        Thread tS3 = new Thread(s3BucketThread);
        Thread tEC2 = new Thread(ec2InstancesThread);
        tS3.start();
        tEC2.start();
        return "JobId-" + jobId;
    }

}
