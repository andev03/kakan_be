package com.kakan.forum_service.mapper;

import com.kakan.forum_service.dto.ReportDto;
import com.kakan.forum_service.pojo.Report;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {

    ReportDto toDto(Report report);

    List<ReportDto> toDtoList(List<Report> reports);
}
