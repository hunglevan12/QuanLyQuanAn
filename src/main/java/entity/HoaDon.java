/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class HoaDon {
    private String IDHoaDon;
    private String MaNV;
    private String TenKH;
    private String MaMon;
    private String Ban;
    private int SoLuong;
    private Double ThanhTien;
    private Date NgayThanhToan;
    private String TrangThai;

     public HoaDon() {
     }

    
    
    public HoaDon(String IDHoaDon, String MaNV, String TenKH, String Ban, String TrangThai) {
        this.IDHoaDon = IDHoaDon;
        this.MaNV = MaNV;
        this.TenKH = TenKH;
        this.Ban = Ban;
        this.TrangThai = TrangThai;
    }

    public HoaDon(String IDHoaDon, String MaMon, int SoLuong, Double ThanhTien, Date NgayThanhToan) {
        this.IDHoaDon = IDHoaDon;
        this.MaMon = MaMon;
        this.SoLuong = SoLuong;
        this.ThanhTien = ThanhTien;
        this.NgayThanhToan = NgayThanhToan;
    }

    public HoaDon(String IDHoaDon, String MaNV, String TenKH, String MaMon, String Ban, int SoLuong, Double ThanhTien, Date NgayThanhToan, String TrangThai) {
        this.IDHoaDon = IDHoaDon;
        this.MaNV = MaNV;
        this.TenKH = TenKH;
        this.MaMon = MaMon;
        this.Ban = Ban;
        this.SoLuong = SoLuong;
        this.ThanhTien = ThanhTien;
        this.NgayThanhToan = NgayThanhToan;
        this.TrangThai = TrangThai;
    }

   
    
    
     public String toString(){
        return IDHoaDon;
    }
    public String getIDHoaDon() {
        return IDHoaDon;
    }

    public void setIDHoaDon(String IDHoaDon) {
        this.IDHoaDon = IDHoaDon;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getMaMon() {
        return MaMon;
    }

    public void setMaMon(String MaMon) {
        this.MaMon = MaMon;
    }

    public String getBan() {
        return Ban;
    }

    public void setBan(String Ban) {
        this.Ban = Ban;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public Double getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(Double ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public Date getNgayThanhToan() {
        return NgayThanhToan;
    }

    public void setNgayThanhToan(Date NgayThanhToan) {
        this.NgayThanhToan = NgayThanhToan;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }  
}
