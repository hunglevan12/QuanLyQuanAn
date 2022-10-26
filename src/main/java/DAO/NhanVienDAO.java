/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.DB;
import entity.MonAn;
import entity.NhanVien;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class NhanVienDAO extends SystemDAO<NhanVien,String> {
    String INSERT_SQL = "INSERT INTO NhanVien(MaNV,Password,VaiTro,HoTen,NgaySinh,GioiTinh,SDT)VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL ="UPDATE NhanVien SET Password=?,VaiTro=?,HoTen=?,NgaySinh=?,GioiTinh=?,SDT=? WHERE MaNV=?";
    String UPDATEPASS_SQL ="UPDATE NhanVien SET Password=?,VaiTro=?,HoTen=? WHERE MaNV=?";
    String DELETE_SQL="DELETE FROM NhanVien Where MaNV=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT *FROM NhanVien where MaNV = ?";

    @Override
    public int insert(NhanVien entity) {
        return DB.update(INSERT_SQL, entity.getMaNV(),entity.getPassword(),entity.isVaiTro(),
                entity.getHoTen(),entity.getNgaySinh(),entity.getGioiTinh(),entity.getSDT());
    }

    @Override
    public int update(NhanVien entity) {
        return DB.update(UPDATE_SQL, entity.getPassword(),entity.isVaiTro(),entity.getHoTen(),
                entity.getNgaySinh(),entity.getGioiTinh(),entity.getSDT(),entity.getMaNV());
    }
    
    public int updatePass(NhanVien entity){
        return DB.update(UPDATEPASS_SQL, entity.getPassword(),entity.isVaiTro(),entity.getHoTen(),entity.getMaNV());
    }

    @Override
    public void delete(String id) {
         DB.update(DELETE_SQL, id);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); 
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien>list = new ArrayList<NhanVien>();
        try{
            ResultSet rs = DB.query(sql, args);
            while(rs.next()){
                NhanVien model = new NhanVien();
                model.setMaNV(rs.getString("MaNV"));
                model.setPassword(rs.getString("Password"));
                model.setVaiTro(rs.getBoolean("VaiTro"));
                model.setHoTen(rs.getString("HoTen"));
                model.setNgaySinh(rs.getDate("NgaySinh"));
                model.setGioiTinh(rs.getBoolean("GioiTinh"));
                 model.setSDT(rs.getString("SDT"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return  list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }   
    public List<NhanVien> selectByKeyword(String keyword){
        String sql="SELECT * FROM NhanVien WHERE HoTen LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
}


