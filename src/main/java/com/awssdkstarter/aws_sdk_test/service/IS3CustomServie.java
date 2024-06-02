package com.awssdkstarter.aws_sdk_test.service;

import java.util.List;

public interface IS3CustomServie {

    List<String> getS3BucketObjectLike(String bucket, String pattern);
}
