package com.kakan.user_service.controller;

import com.kakan.user_service.dto.request.LoginRequest;
import com.kakan.user_service.dto.request.RegisterRequest;
import com.kakan.user_service.dto.response.AccountDto;
import com.kakan.user_service.dto.response.AccountResponse;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.exception.DuplicateEntity;
import com.kakan.user_service.mapper.AccountMapper;
import com.kakan.user_service.pojo.Account;
import com.kakan.user_service.service.AccountService;
import com.kakan.user_service.service.impl.AuthenticationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController {

    final AccountService accountService;

    final AccountMapper accountMapper;
    final AuthenticationServiceImpl Au;

    public AccountController(AccountService accountService, AccountMapper accountMapper, AuthenticationServiceImpl au) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        Au = au;
    }

    @GetMapping("/test")
    public List<AccountDto> responseDto() {
        return accountMapper.toDtoList(accountService.getAllAccounts());
    }

    @GetMapping("/test2/{id}")
    public AccountDto getAccountById(@PathVariable int id) {
        return accountMapper.toDto(accountService.getAccountById(id));
    }
//    @GetMapping("/test")
//    public ResponseDto responseDto(){
//        return accountService.responses();
//    }

    @GetMapping("/")
    public String Hello(HttpServletRequest request) {
        return "Hello, this is user-service!" + request.getSession().getId();
    }

    @PostMapping("/register")
    public ResponseDto<String> register(@RequestBody RegisterRequest account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getDefaultMessage());
            }
            return  new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Validation failed",
                    errors.toString()
            );
        }
        try{
            accountService.register(account);
            return new ResponseDto<String>(200, "Đăng ký thành công", "Vui lòng đăng nhập để tiếp tục");

        }catch (IllegalArgumentException e) {
            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);

        } catch (DuplicateEntity e) {
            return new ResponseDto<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);

        } catch (DataIntegrityViolationException e) {
            // Xử lý lỗi cơ sở dữ liệu
            return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi trong quá trình đăng ký, vui lòng thử lại sau.", null);

        } catch (Exception e) {
            // Xử lý các lỗi khác
            return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Đã xảy ra lỗi không xác định, vui lòng thử lại sau.", null);
        }
      }

    @PostMapping("/login")
    public ResponseDto<AccountResponse> login(@RequestBody LoginRequest request){
        try{
            AccountResponse accountResponse = accountService.login(request);
            return  new ResponseDto<>(200, "Đăng nhập thành công", accountResponse);
        }catch (NotFoundException e) {
            return new ResponseDto<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        } catch (EntityNotFoundException e) {
            return new ResponseDto<>(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null);
        } catch (Exception e) {
            return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        // Cắt bỏ tiền tố "Bearer " nếu token có tiền tố
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        accountService.logout(token);
        return ResponseEntity.ok("Đăng xuất thành công.");
    }
}
