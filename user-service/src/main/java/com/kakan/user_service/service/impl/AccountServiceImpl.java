package com.kakan.user_service.service.impl;

import com.google.type.DateTime;
import com.kakan.account.grpc.*;
import com.kakan.user_service.dto.UserInformationGrpcDto;
import com.kakan.user_service.dto.request.LoginRequest;
import com.kakan.user_service.dto.response.AccountResponse;
import com.kakan.user_service.mapper.UserInformationMapper;
import com.kakan.user_service.model.UserPrincipal;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.repository.UserInformationRepository;
import com.kakan.user_service.service.AccountService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@GrpcService
public class AccountServiceImpl extends UserServiceGrpc.UserServiceImplBase implements AccountService {


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    final UserInformationRepository userInformationRepository;

    final UserInformationMapper userInformationMapper;

    @Autowired
    private TokenService tokenService;

    final AuthenticationManager authenticationManager;

    public AccountServiceImpl(UserInformationRepository userInformationRepository, UserInformationMapper userInformationMapper, AuthenticationManager authenticationManager) {
        this.userInformationRepository = userInformationRepository;
        this.userInformationMapper = userInformationMapper;
        this.authenticationManager = authenticationManager;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    public Account getAccountById(int id) {
        return accountRepository.findAccountById(id);
    }

    public AccountResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.getUsername(),
                                    loginRequest.getPassword()));

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Account account = userPrincipal.getAccount();

            if (!account.getIsActive()) {
                throw new NotFoundException("Tài khoản đã bị vô hiệu hóa!");
            }

            //tạo token cho tài khoản
            AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
            if (authentication.isAuthenticated()) {
                accountResponse.setToken(tokenService.generateToken(account));
            }
            return accountResponse;
        } catch (NotFoundException e) {
            // Nếu tài khoản bị vô hiệu hóa
            throw new NotFoundException("Tài khoản đã bị vô hiệu hóa!");
        } catch (BadCredentialsException e) {
            // Nếu thông tin tài khoản hoặc mật khẩu sai
            throw new EntityNotFoundException("Email hoặc mật khẩu sai!");
        } catch (Exception e) {
            // Xử lý các lỗi khác
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình đăng nhập, vui lòng thử lại sau.");
        }
    }

    @Override
    public void getUsersByIds(UserIdListRequest request, StreamObserver<UserListResponse> responseObserver) {
        try {
            List<Integer> ids = request.getUserIdsList();

            List<UserInformationGrpcDto> users = userInformationMapper.toGrpcDtoList(userInformationRepository.findAllByAccountIdIn(ids));

            UserListResponse.Builder responseBuilder = UserListResponse.newBuilder();

            for (UserInformationGrpcDto user : users) {
                LocalDate dobDate = user.getDob();

                DateTime dob = dobDate != null ? DateTime.newBuilder()
                        .setYear(dobDate.getYear())
                        .setMonth(dobDate.getMonthValue())
                        .setDay(dobDate.getDayOfMonth())
                        .build() : DateTime.getDefaultInstance();

                UserResponse userResponse = UserResponse.newBuilder()
                        .setId(user.getAccountId())
                        .setFullName(user.getFullName())
                        .setGender(Optional.ofNullable(user.getGender()).orElse(false))
                        .setDob(dob)
                        .setPhone(Optional.ofNullable(user.getPhone()).orElse(""))
                        .setAddress(Optional.ofNullable(user.getAddress()).orElse(""))
                        .setAvatarUrl(Optional.ofNullable(user.getAvatarUrl()).orElse(""))
                        .build();
                responseBuilder.addUsers(userResponse);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Server error: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    @Override
    public void getUserById(UserIdRequest request, StreamObserver<UserResponse> responseObserver) {
        try {
            Integer id = request.getUserIds();
            UserInformationGrpcDto user = userInformationMapper.toGrpcDto(userInformationRepository.findById(id).orElse(null));

            LocalDate dobDate = user.getDob();

            DateTime dob = dobDate != null ? DateTime.newBuilder()
                    .setYear(dobDate.getYear())
                    .setMonth(dobDate.getMonthValue())
                    .setDay(dobDate.getDayOfMonth())
                    .build() : DateTime.getDefaultInstance();

            UserResponse userResponse = UserResponse.newBuilder()
                    .setId(user.getAccountId())
                    .setFullName(user.getFullName())
                    .setGender(Optional.ofNullable(user.getGender()).orElse(false))
                    .setDob(dob)
                    .setPhone(Optional.ofNullable(user.getPhone()).orElse(""))
                    .setAddress(Optional.ofNullable(user.getAddress()).orElse(""))
                    .setAvatarUrl(Optional.ofNullable(user.getAvatarUrl()).orElse(""))
                    .build();

            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Server error: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
