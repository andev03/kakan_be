package com.kakan.school_service.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Major {
    @Field(value = "STT")
    private String stt;

    @Field(value = "ten_nganh")
    private String tenNganh;

    @Field(value = "ma_nganh")
    private String maNganh;

    @Field(value = "to_hop_xet_tuyen")
    private String toHopXetTuyen;

    @Field(value = "chi_tieu")
    private String chiTieu;

    @Field(value = "ghi_chu")
    private String ghiChu;
}
