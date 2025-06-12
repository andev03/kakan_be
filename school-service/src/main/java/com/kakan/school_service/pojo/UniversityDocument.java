package com.kakan.school_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "universities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDocument {
    @Id
    private String id;
    @Field(value = "name")
    private String name;

    @Field(value = "url")
    private String url;

    @Field(value ="thong_tin_chung")
    private GeneralInfo thongTinChung;

    @Field(value ="thong_tin_tuyen_sinh")
    private AdmissionInfo thongTinTuyenSinh;

    @Field(value ="nganh_tuyen_sinh")
    private List<Major> nganhTuyenSinh;

    @Field(value ="diem_chuan")
    private List<AdmissionScore> diemChuan;
}
