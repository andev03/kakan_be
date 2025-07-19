package com.kakan.payment_service.mapper;

import com.kakan.payment_service.dto.response.PaymentDto;
import com.kakan.payment_service.pojo.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    List<Payment> toDtoList(List<PaymentDto> comments);

    PaymentDto toDto(Payment comment);
}
