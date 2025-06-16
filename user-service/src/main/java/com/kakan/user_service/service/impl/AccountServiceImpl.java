package com.kakan.user_service.service.impl;

import com.kakan.user_service.dto.request.LoginRequest;
import com.kakan.user_service.dto.request.RegisterRequest;
import com.kakan.user_service.dto.response.AccountResponse;
import com.kakan.user_service.enums.AccountRole;
import com.kakan.user_service.model.UserPrincipal;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ModelMapper modelMapper;
    @Autowired AccountRepository accountRepository;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    @Autowired
    private TokenService tokenService;


    public List<Account> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    public Account getAccountById(int id) {
        return accountRepository.findAccountById(id);
    }

    public AccountResponse register (RegisterRequest registerRequest) {
        // Kiểm tra confirmPassword trước khi tiếp tục
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu và xác nhận mật khẩu không khớp!");
        }
        // Kiểm tra trùng lặp email và username trước khi lưu vào cơ sở dữ liệu
        if (accountRepository.existsByEmail(registerRequest.getEmail())) {
            System.out.println("Email này đã được sử dụng!");
        }


        Account account = modelMapper.map(registerRequest, Account.class);

        try{
            //auto set role student
            account.setIsActive(true);
            account.setRole(AccountRole.STUDENT.name());

            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            account.setEmail(registerRequest.getEmail());
            account.setFullName(registerRequest.getFullName());

            accountRepository.save(account);
            return modelMapper.map(account, AccountResponse.class);
        }catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình đăng ký, vui lòng thử lại sau.");
        }
    }

    public AccountResponse login(LoginRequest loginRequest) {
        try{
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.getEmail(),
                                    loginRequest.getPassword()));

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Account account = userPrincipal.getAccount();
            
            if (!account.getIsActive()) {
                throw new NotFoundException("Tài khoản đã bị vô hiệu hóa!");
            }

            //tạo token cho tài khoản
            AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
            if(authentication.isAuthenticated()){
                accountResponse.setToken(tokenService.generateToken(account));
            }
            return accountResponse;
        }catch (NotFoundException e) {
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

    public void logout(String token) {
        tokenService.invalidateToken(token);
    }
}
