package iuh.fit.ongk_crud_danhmuc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TinTuc {
    private int id;
    private String tieuDe;
    private String noiDungTT;
    private String lienKet;
    private DanhMuc danhMuc;

}
