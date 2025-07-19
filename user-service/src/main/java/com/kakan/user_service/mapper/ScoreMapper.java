package com.kakan.user_service.mapper;

import com.kakan.user_service.dto.response.ScoreDto;
import com.kakan.user_service.pojo.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScoreMapper {
}
