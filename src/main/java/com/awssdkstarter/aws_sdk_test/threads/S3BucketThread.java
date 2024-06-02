package com.awssdkstarter.aws_sdk_test.threads;

import com.amazonaws.regions.Regions;
import com.awssdkstarter.aws_sdk_test.repository.S3DetailRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.awssdkstarter.aws_sdk_test.model.S3Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class S3BucketThread implements Runnable{

    @Autowired
    S3DetailRepository s3DetailRepository;
    @Override
    public void run() {
        listBuckets();
    }

    public void listBuckets() {
        List<S3Detail> list = new ArrayList<>();
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1) //Mumbai
                .build();
        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            S3Detail s3Detail = new S3Detail();
            s3Detail.setBucketName(b.getName());
            list.add(s3Detail);
            System.out.println("From Thread : " + Thread.currentThread().getName() +
                    ", bucket name -> " + b.getName());
        }
        s3DetailRepository.saveAll(list);
    }
}
