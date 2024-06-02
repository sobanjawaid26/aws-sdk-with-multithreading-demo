package com.awssdkstarter.aws_sdk_test.repository;

import com.awssdkstarter.aws_sdk_test.model.BucketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketDetailRepository extends JpaRepository<BucketDetail, String> {
}
