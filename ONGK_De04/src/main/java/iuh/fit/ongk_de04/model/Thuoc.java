package iuh.fit.ongk_de04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Thuoc {
    private int maThuoc;
    private String tenThuoc;
    private double gia;
    private int namSX;
    private LoaiThuoc loaiThuoc;
}
