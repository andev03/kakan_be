package com.kakan.user_service.mapper;

import com.kakan.user_service.dto.response.SubjectDto;
import com.kakan.user_service.pojo.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectMapper {

    List<SubjectDto> toDtoList(List<Subject> subjects);
    public static final Map<String, String> SUBJECT_NAME_MAP = Map.of(
            "toan", "toán học",
            "nguvan", "ngữ văn",
            "ngoaingu", "ngoại ngữ",
            "vatli", "vật lý",
            "hoahoc", "hóa học",
            "sinhhoc", "sinh học",
            "lichsu","lịch sử",
            "dialy", "địa lý",
            "congnghe","công nghệ",
            "tinhoc" , "tin học"
    );
}
