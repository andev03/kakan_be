package com.kakan.forum_service.service.impl;

import com.kakan.account.grpc.*;
import com.kakan.forum_service.dto.UserInformationDto;
import com.kakan.forum_service.service.CommonService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonServiceImpl implements CommonService {

    @Value("${grpc.host}")
    String host;

    @Override
    public List<String> getAccountNameFromAccountService(List<Integer> accountIds) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, 9090)
                .usePlaintext()
                .build();
        try {
            UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
            UserIdListRequest request = UserIdListRequest.newBuilder().addAllUserIds(accountIds).build();
            UserListResponse response = stub.getUsersByIds(request);

            return response.getUsersList().stream()
                    .map(UserResponse::getFullName)
                    .toList();
        } finally {
            channel.shutdown();
        }
    }

    @Override
    public List<UserInformationDto> getAccountFromAccountService(List<Integer> accountIds) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, 9090)
                .usePlaintext()
                .build();
        try {
            UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
            UserIdListRequest request = UserIdListRequest.newBuilder().addAllUserIds(accountIds).build();
            UserListResponse response = stub.getUsersByIds(request);

            return response.getUsersList().stream()
                    .map(userResponse -> UserInformationDto.builder()
                            .accountId(userResponse.getId())
                            .fullName(userResponse.getFullName())
                            .gender(userResponse.getGender())
                            .dob(LocalDate.of(userResponse.getDob().getYear(), userResponse.getDob().getMonth() + 1, userResponse.getDob().getDay()))
                            .phone(userResponse.getPhone())
                            .address(userResponse.getAddress())
                            .avatarUrl(userResponse.getAvatarUrl())
                            .build())
                    .collect(Collectors.toList());
        } finally {
            channel.shutdown();
        }
    }

    @Override
    public UserInformationDto getAccountByAccountIdFromAccountService(Integer accountId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, 9090)
                .usePlaintext()
                .build();
        try {
            UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
            UserIdRequest request = UserIdRequest.newBuilder().setUserIds(accountId).build();
            UserResponse userResponse = stub.getUserById(request);

            return UserInformationDto.builder()
                    .accountId(userResponse.getId())
                    .fullName(userResponse.getFullName())
                    .gender(userResponse.getGender())
                    .dob(LocalDate.of(userResponse.getDob().getYear(), userResponse.getDob().getMonth(), userResponse.getDob().getDay()))
                    .phone(userResponse.getPhone())
                    .address(userResponse.getAddress())
                    .avatarUrl(userResponse.getAvatarUrl())
                    .build();
        } finally {
            channel.shutdown();
        }
    }
}
