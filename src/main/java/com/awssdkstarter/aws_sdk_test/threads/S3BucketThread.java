package com.awssdkstarter.aws_sdk_test.threads;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public class S3BucketThread implements Runnable{
    @Override
    public void run() {
        listBuckets();
    }

    public static void listBuckets() {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1) //Mumbai
                .build();
        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("From Thread : " + Thread.currentThread().getName() +
                    ", bucket name -> " + b.getName());
        }
    }
}
