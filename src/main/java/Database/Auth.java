/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import entity.NhanVien;

/**
 *
 * @author ACER
 */
public class Auth {
    public static NhanVien user = null;
    /**
     * Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất
     */
    public static void clear() {
        Auth.user = null;
    }
    /**
     * Kiểm tra xem đăng nhập hay chưa
     * @return 
     */
    public static boolean isLogin() {
        return Auth.user != null;
    }
     
     /**
     * Kiểm tra xem có phải là trưởng phòng hay không
     */
    public static boolean isManager() {
        return Auth.isLogin() && user.isVaiTro();
    }
}
