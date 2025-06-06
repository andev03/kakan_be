package com.kakan.user_service.mapper;

import com.kakan.user_service.dto.response.OrderDto;
import com.kakan.user_service.pojo.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    List<OrderDto> toDtoList(List<Order> orders);
}
