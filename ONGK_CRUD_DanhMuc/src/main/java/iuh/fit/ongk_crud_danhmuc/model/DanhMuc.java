package iuh.fit.ongk_crud_danhmuc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DanhMuc {
    private int maDM;
    private String tenDanhMuc;
    private String nguoiQuanLy;
    private String ghiChu;

}
