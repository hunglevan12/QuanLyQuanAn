/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author ACER
 */
public class MonAn {
    private String MaMon;
    private boolean Loai;
    private String TenMon;
    private String DonVi;
    private int SoLuong;
    private Double GiaTien;
    private String Hinh;

    public MonAn() {
    }

    public MonAn(String MaMon, boolean Loai, String TenMon, String DonVi, int SoLuong, Double GiaTien, String Hinh) {
        this.MaMon = MaMon;
        this.Loai = Loai;
        this.TenMon = TenMon;
        this.DonVi = DonVi;
        this.SoLuong = SoLuong;
        this.GiaTien = GiaTien;
        this.Hinh = Hinh;
    }

    
    
    @Override
    public String toString(){
        return TenMon;
    }

    public String getMaMon() {
        return MaMon;
    }

    public void setMaMon(String MaMon) {
        this.MaMon = MaMon;
    }

    public boolean isLoai() {
        return Loai;
    }

    public void setLoai(boolean Loai) {
        this.Loai = Loai;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }

    public String getDonVi() {
        return DonVi;
    }

    public void setDonVi(String DonVi) {
        this.DonVi = DonVi;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public Double getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(Double GiaTien) {
        this.GiaTien = GiaTien;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }
    
    
    
}
