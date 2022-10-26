/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.DB;
import entity.MonAn;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class MonAnDAO extends SystemDAO<MonAn,String>{
    String INSERT_SQL = "INSERT INTO MonAn(MaMon,Loai,TenMon,DonVi,SoLuong,GiaTien,Hinh)VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL ="UPDATE MonAn SET Loai=?,TenMon=?,DonVi =?,SoLuong=?,GiaTien=?,Hinh=? WHERE MaMon=?";
    String DELETE_SQL="DELETE FROM MonAn Where MaMon=?";
    String SELECT_ALL_SQL = "SELECT * FROM MonAn";
    String SELECT_BY_ID_SQL = "SELECT *FROM MonAn where MaMon = ?";

    @Override
    public int insert(MonAn entity) {
        return DB.update(INSERT_SQL, entity.getMaMon(),entity.isLoai(),entity.getTenMon(),
                entity.getDonVi(),entity.getSoLuong(),entity.getGiaTien(),entity.getHinh());
    }

    @Override
    public int update(MonAn entity) {
        return DB.update(UPDATE_SQL,entity.isLoai(),entity.getTenMon(),entity.getDonVi(),
                entity.getSoLuong(),entity.getGiaTien(),entity.getHinh(), entity.getMaMon());
    }

    @Override
    public void delete(String id) {
        DB.update(DELETE_SQL, id);
    }

    @Override
    public MonAn selectById(String id) {
        List<MonAn> list = selectBySql(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0); 
    }

    @Override
    public List<MonAn> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<MonAn> selectBySql(String sql, Object... args) {
        List<MonAn>list = new ArrayList<MonAn>();
        try{
            ResultSet rs = DB.query(sql, args);
            while(rs.next()){
                MonAn entity = new MonAn();
                entity.setMaMon(rs.getString("MaMon"));
                entity.setLoai(rs.getBoolean("Loai"));
                entity.setTenMon(rs.getString("TenMon"));
                entity.setDonVi(rs.getString("DonVi"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setGiaTien(rs.getDouble("GiaTien"));
                entity.setHinh(rs.getString("Hinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return  list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    
    }
    public List<MonAn> selectByKeyword(String keyword){
        String sql="SELECT * FROM MonAn WHERE TenMon LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
}
