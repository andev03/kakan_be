package com.kakan.user_service.service.impl;

import com.kakan.user_service.dto.request.LoginRequest;
import com.kakan.user_service.dto.request.RegisterRequest;
import com.kakan.user_service.dto.response.AccountResponse;
import com.kakan.user_service.enums.AccountRole;
import com.kakan.user_service.exception.DuplicateEntity;
import com.kakan.user_service.model.UserPrincipal;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.pojo.UserInformation;
import com.kakan.user_service.repository.AccountRepository;
import com.kakan.user_service.repository.UserInformationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;





    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserInformationRepository userInformationRepository;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    @Autowired
    private TokenService tokenService;



    public AccountResponse register (RegisterRequest registerRequest) {
        try {
            // Kiểm tra confirmPassword trước khi tiếp tục
            if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                throw new IllegalArgumentException("Mật khẩu và xác nhận mật khẩu không khớp!");
            }
            // Kiểm tra trùng lặp email và username trước khi lưu vào cơ sở dữ liệu
            if (accountRepository.existsByEmail(registerRequest.getEmail())) {
                throw new DuplicateEntity("Email này đã được sử dụng!");
            }

            if (accountRepository.existsByUserName(registerRequest.getUsername())) {
                throw new DuplicateEntity("Username này đã tồn tại!");
            }

            Account account = modelMapper.map(registerRequest, Account.class);

            //auto set role student
            account.setIsActive(true);
            account.setRole(AccountRole.STUDENT.name());

            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            account.setEmail(registerRequest.getEmail());
            account.setUserName(registerRequest.getUsername());

            Account newAccount = accountRepository.save(account);

            UserInformation userInformation = new UserInformation();
            userInformation.setFullName(registerRequest.getFullName());
            userInformation.setAccount(newAccount);
            userInformation.setGender(registerRequest.getGender());
            userInformation.setDob(registerRequest.getDob());

            userInformationRepository.save(userInformation);
            return modelMapper.map(account, AccountResponse.class);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình đăng ký: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi không xác định: " + e.getMessage());
        }
    }



    public void logout(String token) {
        tokenService.invalidateToken(token);
    }

    public AccountResponse loginByGoogle(String email, String name) {
        // Kiểm tra xem người dùng có tồn tại không
        Account account = accountRepository.findAccountByEmail(email);

        if (account == null) {
            // Nếu người dùng chưa tồn tại, tạo mới
            account = new Account();
            account.setEmail(email);
            account.setUserName(name);  // Sử dụng tên từ Google làm username
            account.setPassword(passwordEncoder.encode(email));  // Mã hóa email thành mật khẩu
            account.setRole(AccountRole.STUDENT.name());  // Đặt role mặc định là CUSTOMER
            accountRepository.save(account);

            UserInformation userInformation = new UserInformation();
            userInformation.setAccount(account);
            userInformation.setFullName(name);
            userInformationRepository.save(userInformation);

        }

        // Nếu tài khoản đã tồn tại thì bỏ qua việc cập nhật và tạo mới

        // Tạo JWT cho người dùng
        String token = tokenService.generateToken(account);

        // Trả về thông tin người dùng, role và token
        AccountResponse response = new AccountResponse();
        if(account.getIsActive()){
            response.setId(account.getId());
            response.setUserName(account.getUserName());
            response.setEmail(account.getEmail());
            response.setRole(account.getRole());  // Trả về role của người dùng
            response.setToken(token);// Trả về token
            return  response;
        }

        return null;
    }

    @Transactional
    public AccountResponse processOAuthPostLogin(OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        String fullName = oauthUser.getAttribute("name");// nếu có

        // 1) Tạo hoặc lấy account
        Account account = accountRepository.findAccountByEmail(email);
        if(account == null) {
            account = new Account();
            account.setUserName(fullName);
            account.setEmail(email);
            account.setPassword(passwordEncoder.encode(email));         // hoặc null, vì dùng OAuth2
            account.setIsActive(true);
            account.setRole("STUDENT");         // default role
            accountRepository.save(account);

            UserInformation userInformation = new UserInformation();
            userInformation.setAccount(account);
            userInformation.setFullName(fullName);
            userInformationRepository.save(userInformation);
        }

        // Tạo JWT cho người dùng
        String token = tokenService.generateToken(account);


        AccountResponse response = new AccountResponse();
        if(account.getIsActive()){
            response.setId(account.getId());
            response.setUserName(account.getUserName());
            response.setEmail(account.getEmail());
            response.setRole(account.getRole());  // Trả về role của người dùng
            response.setToken(token);// Trả về token
            return  response;
        }

        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUserName(userName);
        if (account == null){
            throw new UsernameNotFoundException("User not found with email: " + userName);
        }
        return new UserPrincipal(account);
    }





}
