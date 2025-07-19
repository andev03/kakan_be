package com.kakan.school_service.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class GeneralInfo {
    @Field(value = "ten_truong")
    private String tenTruong;
    @Field(value = "ten_tieng_anh")
    private String tenTiengAnh;
    @Field(value = "ma_truong")
    private String maTruong;
    @Field(value = "loai_truong")
    private String loaiTruong;
    @Field(value = "he_dao_tao")
    private String heDaoTao;
    @Field(value = "dia_chi")
    private String diaChi;
    @Field(value = "sdt")
    private String sdt;
    @Field(value = "email")
    private String email;
    @Field(value = "website")
    private String website;
    @Field(value = "facebook")
    private String facebook;
}
