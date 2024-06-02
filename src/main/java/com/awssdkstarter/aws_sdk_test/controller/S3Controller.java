package com.awssdkstarter.aws_sdk_test.controller;

import com.awssdkstarter.aws_sdk_test.dto.S3ObjectRequest;
import com.awssdkstarter.aws_sdk_test.service.IAwsSdkService;
import com.awssdkstarter.aws_sdk_test.service.IS3CustomServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/aws-s3")
public class S3Controller {

    @Autowired
    IS3CustomServie is3CustomServie;

    @PostMapping(path = "/getS3BucketObjectLike")
    public ResponseEntity getS3BucketObjectLike(@RequestBody S3ObjectRequest s3ObjectRequest) {
        List<String> res = is3CustomServie.getS3BucketObjectLike(
                s3ObjectRequest.getBucketName(), s3ObjectRequest.getPattern());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
