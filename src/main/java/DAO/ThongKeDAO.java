/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.DB;
import entity.HoaDon;
import entity.MonAn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class ThongKeDAO {
    public List<Object[]> getDoanhThu(int nam) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql ="{CALL sp_ThongKeDoanhThu(?)}";
                rs = DB.query(sql, nam);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaMon"),
                        rs.getString("MaNV"),
                        rs.getInt("SoLuong"),
                        rs.getDate("NgayThanhToan"),
                        rs.getDouble("ThanhTien")
                    };
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Integer> selectMonth() {
        String sql = "SELECT DISTINCT MONTH (NgayThanhToan) MONTH from HoaDonChiTiet ORDER BY MONTH Desc";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = DB.query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
     
    public List<Object[]> getDoanhThuThang(int thang) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql ="{CALL sp_ThongKeThang(?)}";
                rs = DB.query(sql, thang);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaMon"),
                        rs.getString("MaNV"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("ThanhTien")
                    };
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<Object[]> top5() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql ="{CALL sp_Top5}";
                rs = DB.query(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaMon"),
                        rs.getInt("SoLuongTB"),
                        rs.getInt("Tong"),
                       
                    };
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
 
}       
    

