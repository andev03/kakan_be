package com.kakan.user_service.mapper;

import com.kakan.user_service.dto.response.AccountInformationDto;
import com.kakan.user_service.dto.response.UserInformationDto;
import com.kakan.user_service.pojo.UserInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserInformationMapper {
    @Mapping(source = "id", target = "id")
    List<UserInformationDto> toDtoList(List<UserInformation> userInformations);
}
