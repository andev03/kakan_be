package com.kakan.school_service.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class AdmissionScore {
    @Field(value = "STT")
    private String stt;

    @Field(value = "ten_nganh")
    private String tenNganh;

    @Field("nam_2021")
    private String nam2021;

    @Field("nam_2022")
    private String nam2022;

    @Field("nam_2023")
    private String nam2023;

    @Field("nam_2024")
    private String nam2024;
}
