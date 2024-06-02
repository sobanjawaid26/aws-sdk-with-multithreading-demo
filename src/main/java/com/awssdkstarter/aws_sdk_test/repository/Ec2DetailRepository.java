package com.awssdkstarter.aws_sdk_test.repository;

import com.awssdkstarter.aws_sdk_test.model.Ec2Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ec2DetailRepository extends JpaRepository<Ec2Detail, String> {
}
