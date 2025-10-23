package com.example.nguyenthimyduyen_tuan05_bai06.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TinTuc {
    private int maTT;
    private String tieuDe;
    private String noiDung;
    private String lienKet;
    private DanhMuc danhMuc;
}
