package com.awssdkstarter.aws_sdk_test.dto;

import lombok.Data;

@Data
public class S3ObjectRequest {
    String bucketName;
    String pattern;
}
