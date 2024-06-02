package com.awssdkstarter.aws_sdk_test.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Ec2Detail {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String instanceId;
    private String ImageId;
    private String InstanceType;
    private String state;
    private String monitoringState;
}
