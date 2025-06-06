package com.kakan.user_service.mapper;

import com.kakan.user_service.dto.response.SubjectDto;
import com.kakan.user_service.pojo.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectMapper {

    List<SubjectDto> toDtoList(List<Subject> subjects);
}
