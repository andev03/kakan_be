package com.kakan.user_service.mapper;

import com.kakan.user_service.dto.response.AccountDto;
import com.kakan.user_service.pojo.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    List<AccountDto> toDtoList(List<Account> accounts);
}
