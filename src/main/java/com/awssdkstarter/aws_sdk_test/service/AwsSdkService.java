package com.awssdkstarter.aws_sdk_test.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.awssdkstarter.aws_sdk_test.model.Ec2Detail;
import com.awssdkstarter.aws_sdk_test.model.S3Detail;
import com.awssdkstarter.aws_sdk_test.threads.EC2InstancesThread;
import com.awssdkstarter.aws_sdk_test.threads.S3BucketThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AwsSdkService implements IAwsSdkService{
    @Autowired
    S3BucketThread s3BucketThread;
    @Autowired
    EC2InstancesThread ec2InstancesThread;
    @Override
    public void getJobResult(Integer jobId) {
        System.out.println("AwsSdkService.getJobResult() is getting executed from thread : " +
                Thread.currentThread().getName());
        Thread tS3 = new Thread(s3BucketThread);
        Thread tEC2 = new Thread(ec2InstancesThread);
        tS3.start();
        tEC2.start();
    }

    @Override
    public List<String> getDiscoveryResult(String service) {
        List<String> res = new ArrayList<>();
        if(service.equalsIgnoreCase("ec2")){
            res = getEc2InstanceIds();
        } else if( service.equalsIgnoreCase("s3")){
            res = getS3BucketNamess();
        }
        return res;
    }

    private List<String> getS3BucketNamess() {
        List<String> list = new ArrayList<>();
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1) //Mumbai
                .build();
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets)
            list.add(b.getName());
        return list;
    }

    public List<String> getEc2InstanceIds(){
        List<String> list = new ArrayList<>();
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .build();
        boolean done = false;

        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while(!done) {
            DescribeInstancesResult response = ec2.describeInstances(request);

            for(Reservation reservation : response.getReservations()) {
                for(Instance instance : reservation.getInstances())
                    list.add(instance.getInstanceId());
            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
        }
        return list;
    }

}
