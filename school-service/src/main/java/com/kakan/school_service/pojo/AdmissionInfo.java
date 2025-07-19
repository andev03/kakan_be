package com.kakan.school_service.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class AdmissionInfo {
    @Field(value ="thoi_gian_xet_tuyen")
    private String thoiGianXetTuyen;
    @Field(value ="doi_tuong_tuyen_sinh")
    private String doiTuongTuyenSinh;
    @Field (value ="pham_vi_tuyen_sinh")
    private String phamViTuyenSinh;
    @Field(value ="phuong_thuc_tuyen_sinh")
    private String phuongThucTuyenSinh;
    @Field(value ="nguong_dau_vao")
    private String nguongDauVao;
    @Field(value ="hoc_phi")
    private String hocPhi;
}
