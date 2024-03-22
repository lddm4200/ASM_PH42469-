package com.example.asm_ph42469.Modal;

public class SanPham {
    private String _id,anh,maSp,tenSp;
    private Double gia;
    private int soLuong;

    public SanPham() {
    }

    public SanPham(String anh, String maSp, String tenSp, Double gia, int soLuong) {
        this.anh = anh;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public SanPham(String _id, String anh, String maSp, String tenSp, Double gia, int soLuong) {
        this._id = _id;
        this.anh = anh;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public SanPham(String maSp, String tenSp, Double gia, int soLuong) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
