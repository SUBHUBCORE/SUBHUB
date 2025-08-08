package io.subHub.modules.project.util;

import io.subHub.common.configBean.ConfigBean;
import io.subHub.modules.grpc.identity.CreatePidWithThemeReply;
import io.subHub.modules.grpc.identity.CreatePidWithThemeRequest;
import io.subHub.modules.grpc.identity.IdentityPFGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class CreatePidUtil {


    public static String createPidByAddress(String address,ConfigBean configBean){
        ManagedChannel channel = ManagedChannelBuilder.forTarget(configBean.getGrpc_url())
                .usePlaintext()
                .build();
        IdentityPFGrpc.IdentityPFBlockingStub blockingStub = IdentityPFGrpc.newBlockingStub(channel);
        //Step 1: Set the request body
        CreatePidWithThemeRequest request = CreatePidWithThemeRequest
                .newBuilder()
                .setAddress(address)
                .setTheme(configBean.getGrpc_platform())
                .build();

        //Define response
        CreatePidWithThemeReply response;
        String pid = null;
        try {
            response = blockingStub.createPidWithTheme(request);
            pid = response.getPid();
            channel.shutdown();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
        return pid;
    }

}
