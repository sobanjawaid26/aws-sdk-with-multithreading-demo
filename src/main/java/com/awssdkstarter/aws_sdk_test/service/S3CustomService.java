package com.awssdkstarter.aws_sdk_test.service;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.awssdkstarter.aws_sdk_test.model.BucketDetail;
import com.awssdkstarter.aws_sdk_test.repository.BucketDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class S3CustomService implements IS3CustomServie{

    @Autowired
    BucketDetailRepository bucketDetailRepository;

    public List<String> getS3BucketObjectLike(String bucket, String pattern) {
        String regex
                = "([^\\s]+(\\.(?i)(jpe?g|txt|mq5|bmp))$)";
        List<String> list = new ArrayList<>();
        String REGION = "ap-south-1";      // Region name
        System.out.println("Bucket name: " + bucket);

        // Instantiates a client
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION).build();

        try {
            if (s3client.doesBucketExistV2(bucket)) {
                System.out.println("Listing objects ...");
                // List objects in a Bucket
                ObjectListing objectListing = s3client.listObjects(new ListObjectsRequest()
                        .withBucketName(bucket));
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    System.out.println(" - " + objectSummary.getKey() + "  " +
                            "(size = " + objectSummary.getSize() + ")");
                    if(isRegexMatching(pattern, objectSummary.getKey()))
                        list.add(objectSummary.getKey());
                }
                System.out.println("Listed");

            } else {
                System.out.println("Error: Bucket does not exist!!");
            }
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, " +
                    "which means your request made it " +
                    "to Amazon S3, but was rejected with an error response " +
                    "for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, " +
                    "which means the client encountered " +
                    "an internal error while trying to communicate" +
                    " with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        s3client.shutdown();

        return list;
    }

    @Override
    public Integer getS3BucketObjectCount(String bucket) {
        String REGION = "ap-south-1";      // Region name
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION).build();
        int count = 0;
        try {
            if (s3client.doesBucketExistV2(bucket)) {
                ObjectListing objectListing = s3client.listObjects(new ListObjectsRequest()
                        .withBucketName(bucket));
                count = objectListing.getObjectSummaries().size();
            } else {
                System.out.println("Error: Bucket does not exist!!");
            }
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException");
        }
        s3client.shutdown();
        return count;
    }

    @Override
    public void getS3BucketObjectsAndSave(String bucket) {
        List<BucketDetail> list = new ArrayList<>();
        String REGION = "ap-south-1";      // Region name
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION).build();

        try {
            if (s3client.doesBucketExistV2(bucket)) {
                ObjectListing objectListing = s3client.listObjects(new ListObjectsRequest()
                        .withBucketName(bucket));
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    System.out.println(" - " + objectSummary.getKey() + "  " +
                            "(size = " + objectSummary.getSize() + ")");
                    BucketDetail bucketDetail = new BucketDetail();
                    bucketDetail.setBucketName(bucket);
                    bucketDetail.setObjectName(objectSummary.getKey());
                    bucketDetail.setSize(Long.toString(objectSummary.getSize()));
                    list.add(bucketDetail);
                }
                bucketDetailRepository.saveAll(list);
            } else {
                System.out.println("Error: Bucket does not exist!!");
            }
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, " +
                    "which means your request made it " +
                    "to Amazon S3, but was rejected with an error response " +
                    "for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, " +
                    "which means the client encountered " +
                    "an internal error while trying to communicate" +
                    " with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        s3client.shutdown();
    }

    public static boolean isRegexMatching(String pattern, String str)
    {
        str = str.substring(str.length() - 5);
        String regex
                = "([^\\s]+(\\.(?i)(jpe?g|txt|mq5|bmp))$)";
        Pattern p = Pattern.compile(pattern);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        boolean res = m.matches();
        return res;
    }
}

