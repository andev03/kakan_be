package com.kakan.user_service.mapper;

import com.kakan.user_service.dto.response.TransactionDto;
import com.kakan.user_service.pojo.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    List<TransactionDto> toDtoList(List<Transaction> transactions);
}
