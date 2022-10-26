/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import entity.NhanVien;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class DB {
    private static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl="jdbc:sqlserver://localhost;database=QuanLyQuanAn";
    private static String username="sa";
    private static String password="123456";
 static{
        try {            
            Class.forName(driver);
        } 
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    //xay dựng PreparedStatement
    
    public static PreparedStatement getStmt(String sql, Object...args) throws SQLException{
        Connection connection = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;
        if(sql.trim().startsWith("{")){ 
            pstmt = connection.prepareCall(sql);// store procedure
        }
        else{
            pstmt = connection.prepareStatement(sql);//
        }
        for(int i=0;i<args.length;i++){
            pstmt.setObject(i + 1, args[i]);//  ps.setString(1,hv.getHoten());
        }
        return pstmt;
    }
    public static ResultSet query(String sql, Object...args) {
        try {
            PreparedStatement stmt = DB.getStmt(sql, args);
            return stmt.executeQuery();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static Object value(String sql, Object...args) {
        try {
            ResultSet rs = DB.query(sql, args);
            if(rs.next()){
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
 }
        public static int update(String sql,Object...args){
            try{
                PreparedStatement stmt = DB.getStmt(sql, args);
                try{
                    if(stmt.executeUpdate()>0){
                        return 1;
                    }
                    
                }
                finally{
                    stmt.getConnection().close();
                }
            }
            catch(SQLException e){
                throw new RuntimeException(e);
            }
            return 0;
        }
}