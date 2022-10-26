/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.DB;
import Database.XDecimal;
import entity.HoaDon;

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
public class HoaDonDAO extends SystemDAO<HoaDon,String>  {
    
    String SELECT_BY_ID_SQL = "SELECT HoaDon.IDHoaDon,HoaDon.MaNV,HoaDon.TenKH,HoaDonChiTiet.MaMon,HoaDon.Ban,\n" +
"HoaDonChiTiet.SoLuong,HoaDonChiTiet.ThanhTien,HoaDonChiTiet.NgayThanhToan,HoaDon.TrangThai \n" +
"FROM HoaDon  join HoaDonChiTiet \n" +
"on HoaDon.IDHoaDon = HoaDonChiTiet.IDHoaDon and HoaDonChiTiet.IDHoaDon = ?";
    String SELECT_ALL_HD = "SELECT HoaDon.IDHoaDon,HoaDon.MaNV,HoaDon.TenKH,HoaDonChiTiet.MaMon,HoaDon.Ban,"
            + "HoaDonChiTiet.SoLuong,HoaDonChiTiet.ThanhTien,HoaDonChiTiet.NgayThanhToan,HoaDon.TrangThai \n" +
"FROM HoaDon  join HoaDonChiTiet \n" +
"on HoaDon.IDHoaDon = HoaDonChiTiet.IDHoaDon ";
//    String SELECT_BY_BAN = "SELECT *FROM HoaDon where Ban = ?";
    public int insertHoaDon(HoaDon entity) {
        String SQL = "{Call sp_TruSoLuong(?,?,?,?,?,?,?)}";
        return DB.update(SQL, 
                entity.getIDHoaDon(),
                entity.getMaNV(),
                entity.getTenKH(),
                entity.getMaMon(),
                entity.getBan(),
                entity.getSoLuong(),
                entity.getThanhTien()                          
        );
    }

    @Override
    public int update(HoaDon entity) {
        String SQL = "update HoaDon set TrangThai = ? where IDHoaDon = ?";
        return DB.update(SQL,              
                entity.getTrangThai(),
                entity.getIDHoaDon()
        );
    }
    

    
   
//    public HoaDon selectBan(String id) {
//        List<HoaDon> list = selectBySql(SELECT_BY_BAN,id);
//        if(list.isEmpty()){
//            return null;
//        }
//        return list.get(0);
//    }

    @Override
    public HoaDon selectById(String id) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

   public String inhoadon(String hd){
        String ad = "";
        try {
            String sql = "select ma.TenMon from HoaDonChiTiet as ct,MonAn as ma \n" +
"                    where ct.MaMon = ma.MaMon and ct.IDHoaDon = ?";
            ResultSet rs = DB.query(sql, hd);
            while (rs.next()) {                
                ad += rs.getString(1)+",";
            }
        } catch (Exception e) {
        }
       return ad;
    }
   
   
   public List<HoaDon> ChuaThanhToan() {
        String sql = "select hd.IDHoaDon , hd.MaNV , hd.TenKH ,ct.MaMon, hd.Ban , ct.SoLuong ,"
                + " ct.ThanhTien,ct.NgayThanhToan,hd.TrangThai from HoaDon as hd,HoaDonChiTiet as ct \n" +
"where hd.IDHoaDon = ct.IDHoaDon  and hd.TrangThai = N'Chưa Thanh Toán'";
        List<HoaDon> hd = new ArrayList<>();
        ResultSet rs = DB.query(sql);
        try {
            while (rs.next()) {
                HoaDon h = new HoaDon();
                h.setIDHoaDon(rs.getString("IDHoaDon"));
                h.setMaNV(rs.getString("MaNV"));
                h.setTenKH(rs.getString("TenKH"));
                h.setMaMon(rs.getString("MaMon"));
                h.setBan(rs.getString("Ban"));
                h.setSoLuong(rs.getInt("SoLuong"));
                h.setThanhTien(rs.getDouble("ThanhTien"));
                h.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                h.setTrangThai(rs.getString("TrangThai"));
                hd.add(h);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hd;
    }
    
   
   public String SanPham(String hd) {
        String tensp = "";
        try {
            String sql = "select ma.TenMon from HoaDonChiTiet as ct,MonAn as ma where ct.MaMon = ma.MaMon and ct.idHoaDon = ?";
            ResultSet rs = DB.query(sql, hd);
            while (rs.next()) {
                tensp += rs.getString(1) + " , ";
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tensp;
    }

    public String GiaTien(String hd) {
        String tensp = "";
        try {
            String sql = "select ma.GiaTien from HoaDonChiTiet as ct,MonAn as ma where ct.MaMon = ma.MaMon and ct.idHoaDon = ?";
            ResultSet rs = DB.query(sql, hd);
            while (rs.next()) {
                tensp += XDecimal.ad.format(rs.getDouble(1)) + " , ";
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tensp;
    }
    public String soluong(String hd) {
        String tensp = "";
        try {
            String sql = "select ct.SoLuong from HoaDonChiTiet as ct,MonAn as ma where ct.MaMon = ma.MaMon and ct.IDHoaDon = ?";
            ResultSet rs = DB.query(sql, hd);
            while (rs.next()) {
                tensp += rs.getInt(1) + " , ";
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tensp;
    }

    public HoaDon printHoaDon(String idHoaDon) {
        HoaDon hd = new HoaDon();
        try {
            String sql = "select HoaDon.IDHoaDon,HoaDon.TenKH,HoaDonChiTiet.NgayThanhToan,Ban,TrangThai,sum(SoLuong) Soluong,sum(ThanhTien) from HoaDonChiTiet \n" +
"                    Join HoaDon \n" +
"                   on HoaDonChiTiet.IDHoaDon=HoaDon.IDHoaDon \n" +
"           where HoaDonChiTiet.IDHoaDon = ? \n" +
"                  Group by HoaDon.IDHoaDon,NgayThanhToan,TenKH,Ban,TrangThai";
            ResultSet rs = DB.query(sql, idHoaDon);
            while (rs.next()) {
                hd.setIDHoaDon(rs.getString(1));
                hd.setTenKH(rs.getString(2));
                hd.setNgayThanhToan(rs.getDate(3));
                hd.setBan(rs.getString(4));
                hd.setTrangThai(rs.getString(5));
                hd.setSoLuong(rs.getInt(6));
                hd.setThanhTien(rs.getDouble(7));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hd;
    }
    
    public List<HoaDon> selectAllHD(){
        return this.selectBySql(SELECT_ALL_HD);
    }
    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon>list = new ArrayList<HoaDon>();
        try{
            ResultSet rs = DB.query(sql, args);
            while(rs.next()){
                HoaDon entity = new HoaDon();
                entity.setIDHoaDon(rs.getString("IDHoaDon"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setTenKH(rs.getString("TenKH"));
                entity.setMaMon(rs.getString("MaMon"));
                entity.setBan(rs.getString("Ban"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setThanhTien(rs.getDouble("ThanhTien"));
                entity.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                entity.setTrangThai(rs.getString("TrangThai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return  list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    
    }

    @Override
    public int insert(HoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HoaDon> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
 }

    
    

