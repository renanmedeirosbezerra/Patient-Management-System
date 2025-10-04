package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    /*
        Override the createBillingAccount created at the proto command. StreamObserver is a tool that allows back and forth
        communication and response (as needed at a chat, for exemple.
         */
    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request received {}", billingRequest.toString());

        //Business logic - e.g. to database perfom calculations, etc

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("active")
                .build();

        responseObserver.onNext(response);//sendo a response back to the client
        responseObserver.onCompleted();//the rresponse is completed, and we ended the cycle fos this response
    }
}
