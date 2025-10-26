package com.pm.billing_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class BillingGrpcService extends BillingServiceImplBase{

    @Override
    public void createBillingAccount (billing.BillingRequest billingRequest,
//                                      That object lets your server send data to the client in real time, multiple times, over a single connection.
//                                      With gRPC streaming, the connection stays open after the request — allowing:
//                                      the server to send multiple updates to the client,
//                                      or the client to send multiple messages to the server,
//                                      or both sides to stream continuously (bidirectional streaming).
//                                      No need to reopen a new request for every update.
//                                      unary RPC 1 request → 1 response (like REST)
                                      StreamObserver<billing.BillingResponse> responseObserver){
        log.info("createBillingAccount request received {} ", billingRequest.toString());

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("ACTIVE")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted(); // close connection
    }
}
