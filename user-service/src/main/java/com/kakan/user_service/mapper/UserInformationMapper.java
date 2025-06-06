package com.kakan.user_service.mapper;

import com.kakan.user_service.dto.response.UserInformationDto;
import com.kakan.user_service.pojo.UserInformation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserInformationMapper {

    List<UserInformationDto> toDtoList(List<UserInformation> userInformations);
}
