package com.awssdkstarter.aws_sdk_test.repository;

import com.awssdkstarter.aws_sdk_test.model.S3Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3DetailRepository extends JpaRepository<S3Detail, String> {
}
