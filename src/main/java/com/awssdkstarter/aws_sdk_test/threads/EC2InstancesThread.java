package com.awssdkstarter.aws_sdk_test.threads;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.awssdkstarter.aws_sdk_test.model.Ec2Detail;
import com.awssdkstarter.aws_sdk_test.model.S3Detail;
import com.awssdkstarter.aws_sdk_test.repository.Ec2DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EC2InstancesThread implements Runnable{

    @Autowired
    Ec2DetailRepository ec2DetailRepository;

    @Override
    public void run() {
        listEc2Instances();
    }
    public void listEc2Instances(){
        List<Ec2Detail> list = new ArrayList<>();
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .build();
        boolean done = false;

        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while(!done) {
            DescribeInstancesResult response = ec2.describeInstances(request);

            for(Reservation reservation : response.getReservations()) {
                for(Instance instance : reservation.getInstances()) {
                    Ec2Detail ec2Detail = new Ec2Detail();
                    ec2Detail.setInstanceId(instance.getInstanceId());
                    ec2Detail.setImageId(instance.getImageId());
                    ec2Detail.setInstanceType(instance.getInstanceType());
                    ec2Detail.setState(instance.getState().getName());
                    ec2Detail.setMonitoringState(instance.getMonitoring().getState());

                    list.add(ec2Detail);

                    System.out.printf(
                            "From THREAD : %s , Found instance with id %s, " +
                                    "AMI %s, " +
                                    "type %s, " +
                                    "state %s " +
                                    "and monitoring state %s",
                            Thread.currentThread().getName(),
                            instance.getInstanceId(),
                            instance.getImageId(),
                            instance.getInstanceType(),
                            instance.getState().getName(),
                            instance.getMonitoring().getState());
                    System.out.println();
                }
            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
        }
        ec2DetailRepository.saveAll(list);
    }
}
