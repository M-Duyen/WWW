package iuh.fit.ongk_crud_danhmuc.dao;

import iuh.fit.ongk_crud_danhmuc.model.DanhMuc;

import java.util.List;

public interface DanhMucInterface {
    List<DanhMuc> getAllDanhMuc();

    DanhMuc getDanhMucById(int id);

    void addDanhMuc(DanhMuc danhMuc);

    void updateDanhMuc(DanhMuc danhMuc);

    boolean deleteDanhMuc(int id);

    List<DanhMuc> searchDanhMuc(String value);
}
