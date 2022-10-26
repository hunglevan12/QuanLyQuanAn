/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import Database.Auth;
import Database.DB;
import Database.MsgBox;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;


/**
 *
 * @author ACER
 */
public class MainJFrame extends javax.swing.JFrame {

    /**
     * Creates new form Main1JFrame
     */
    public MainJFrame() {
        initComponents();
        init();
    }
    

    void getanh(){
        BufferedImage pane1=null;
        try {
            pane1=ImageIO.read(getClass().getClassLoader().getResource("image/logoquanan.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//         lb.setPreferredSize(new Dimension(JPNHOME.getWidth(),JPNHOME.getHeight()));
         ImageIcon icon =  new ImageIcon(pane1);
         Image img = icon.getImage();
          ImageIcon i =new ImageIcon(img.getScaledInstance(JPNHOME.getWidth(),JPNHOME.getHeight() , Image.SCALE_SMOOTH));
          JLabel lb = new JLabel(i);
         JPNHOME.removeAll();
         JPNHOME.setLayout(new BorderLayout());
         JPNHOME.add(lb);
         JPNHOME.setPreferredSize(new Dimension(1920,1080));
         JPNHOME.repaint();
    }
    void init(){
        setTitle("HỆ THỐNG QUẢN LÝ QUÁN ĂN");
        setLocationRelativeTo(null);
        new ChaoJDialog(this, true).setVisible(true);
        new DangNhapJDialog(this, true);
        setExtendedState(MainJFrame.MAXIMIZED_BOTH);
        this.startDongHo(); 
        lblRole.setText(Auth.user.isVaiTro()?"Admin":"Nhân Viên");
        lblTen.setText(Auth.user.getHoTen());
        getanh();
    }
     void setRole() {
        if (Auth.isLogin()) {
            lblRole.setText(Auth.user.isVaiTro()?"Admin":"Nhân Viên");
            lblTen.setText(Auth.user.getHoTen());
        }else{
            lblRole.setText(!Auth.user.isVaiTro()?"Admin":"Nhân Viên");
            lblTen.setText(Auth.user.getHoTen());
        }
    }
    void startDongHo(){
        SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss a");
        new Timer(1000, (ActionEvent e) -> {
            lblDongHo.setText(formater.format(new Date()));
    }).start();
    }
    void openWelcome(){
        new ChaoJDialog(this,true).setVisible(true);
    }
    void openDangNhap(){
        new DangNhapJDialog(this, true).setVisible(true);
        this.setRole();
    }
    void openDoiMatKhau(){
        if(Auth.isLogin()){
            new DoiMatKhauJDialog(this, true).setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(this, "Vui Lòng Đăng Nhập");
        }
    }
    void dangXuat(){
         if(Auth.isLogin()){
            if(MsgBox.confirm(this, "Bạn Có Muốn Đăng Xuất Không?")){
                Auth.clear();
                new DangNhapJDialog(this, true).setVisible(true);
                this.setRole();
//                lblRole.setText(User.user.getHoTen());
            }  
        }
        else {
           Auth.clear();
           MsgBox.alert(this, "Bạn Đã Đăng Xuất"); 
        }
    }
    void ketThuc(){
        JOptionPane.showMessageDialog(this, "Bạn Có Muốn Đăng Xuất Không?");
            System.exit(0);
        
    }
    
    void openNhanVien(){
        if(Auth.isLogin()){
            if(!Auth.isManager()){
                MsgBox.alert(this, "Chỉ Admin Mới Có Quyền Truy Cập Vào Quản Lý Nhân Viên");
            }else{
                NhanVienJDialog nv = new NhanVienJDialog(this,true);
                nv.setVisible(true);
            }
        }
        else{
            MsgBox.alert(this, "Vui Lòng Đăng Nhập");
        }
    }
    
    
    void openMonAn(){
        if(Auth.isLogin()){
            new MonAnJDialog(this, true).setVisible(true);
        }
        else{
            MsgBox.alert(this, "Vui Lòng Đăng Nhập");
        }
    }
    void openBanHang(){
        if(Auth.isLogin()){
            new BanHangJDialog(this, true).setVisible(true);
        }
        else{
            MsgBox.alert(this, "Vui Lòng Đăng Nhập");
        }
    }
    void openThongKe(){
        if(Auth.isLogin()){
            if(!Auth.isManager()){
                MsgBox.alert(this, "Chỉ Admin Mới Có Quyền Truy Cập Vào Thống Kê");
            }else{
                ThongKeJDialog nv = new ThongKeJDialog(this,true);
                nv.setVisible(true);
            }
        }
        else{
            MsgBox.alert(this, "Vui Lòng Đăng Nhập");
        }
    }

    
     void openAbout(){
           new GioiThieuJDialog(this, true).setVisible(true);
     }
     
     void openWebsite(){
        try {
           Desktop.getDesktop().browse(new File("help/index.html").toURI());
             } 
           catch (IOException ex) {
             MsgBox.alert(this, "Không Tìm Thấy File Hướng Dẫn !");
             }
       }

     
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ToolBar = new javax.swing.JToolBar();
        lblTrangThai = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnNhanVien = new javax.swing.JButton();
        btnMonAn = new javax.swing.JButton();
        btnBanHang = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        btnThongTin = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblDongHo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        JPNHOME = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        openDangNhap = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        openDangXuat = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        openDoiMatKhau = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        openNhanVien = new javax.swing.JMenuItem();
        openMonAn = new javax.swing.JMenuItem();
        openBanHang = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        openDoanhThu = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        ToolBar.setRollover(true);

        lblTrangThai.setBackground(new java.awt.Color(255, 255, 255));
        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTrangThai.setForeground(new java.awt.Color(0, 153, 153));
        lblTrangThai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Red star.png"))); // NOI18N
        lblTrangThai.setText("    Quản Lý Quán Ăn");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/User.png"))); // NOI18N
        btnNhanVien.setText("NHÂN VIÊN");
        btnNhanVien.setFocusable(false);
        btnNhanVien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNhanVien.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnNhanVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });

        btnMonAn.setBackground(new java.awt.Color(255, 255, 255));
        btnMonAn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Steak-icon.png"))); // NOI18N
        btnMonAn.setText("MÓN ĂN");
        btnMonAn.setFocusable(false);
        btnMonAn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMonAn.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnMonAn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMonAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonAnActionPerformed(evt);
            }
        });

        btnBanHang.setBackground(new java.awt.Color(255, 255, 255));
        btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Full basket.png"))); // NOI18N
        btnBanHang.setText("BÁN HÀNG");
        btnBanHang.setFocusable(false);
        btnBanHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBanHang.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnBanHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanHangActionPerformed(evt);
            }
        });

        btnThongKe.setBackground(new java.awt.Color(255, 255, 255));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Statistics.png"))); // NOI18N
        btnThongKe.setText("THỐNG KÊ");
        btnThongKe.setFocusable(false);
        btnThongKe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThongKe.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnThongKe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnDangXuat.setBackground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Right.png"))); // NOI18N
        btnDangXuat.setText("ĐĂNG XUẤT");
        btnDangXuat.setFocusable(false);
        btnDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangXuat.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnDangXuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        btnThongTin.setBackground(new java.awt.Color(255, 255, 255));
        btnThongTin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Info.png"))); // NOI18N
        btnThongTin.setText("THÔNG TIN");
        btnThongTin.setFocusable(false);
        btnThongTin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThongTin.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnThongTin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongTinActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        lblDongHo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Alarm.png"))); // NOI18N
        lblDongHo.setText("10:55 PM");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(lblDongHo)
                .addGap(51, 51, 51))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(lblDongHo)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMonAn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThongKe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNhanVien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMonAn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBanHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThongKe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThongTin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Xin Chào:");

        lblRole.setBackground(new java.awt.Color(255, 255, 255));
        lblRole.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\checked_user_male_32px.png")); // NOI18N
        lblRole.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTen.setBackground(new java.awt.Color(255, 255, 255));
        lblTen.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\ok_26px.png")); // NOI18N
        lblTen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JPNHOME, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(JPNHOME, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jMenuBar1.setBackground(new java.awt.Color(51, 51, 51));
        jMenuBar1.setMaximumSize(new java.awt.Dimension(300, 32769));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(300, 33));

        openDangNhap.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\gear_24px.png")); // NOI18N
        openDangNhap.setText("Hệ Thống");
        openDangNhap.setMargin(new java.awt.Insets(5, 5, 5, 5));

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/User.png"))); // NOI18N
        jMenuItem2.setText("Đăng nhập");
        jMenuItem2.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        openDangNhap.add(jMenuItem2);

        openDangXuat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Right.png"))); // NOI18N
        openDangXuat.setText("Đăng xuất");
        openDangXuat.setMargin(new java.awt.Insets(5, 5, 5, 5));
        openDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDangXuatActionPerformed(evt);
            }
        });
        openDangNhap.add(openDangXuat);
        openDangNhap.add(jSeparator1);

        openDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh.png"))); // NOI18N
        openDoiMatKhau.setText("Đổi mật khẩu");
        openDoiMatKhau.setMargin(new java.awt.Insets(5, 5, 5, 5));
        openDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDoiMatKhauActionPerformed(evt);
            }
        });
        openDangNhap.add(openDoiMatKhau);
        openDangNhap.add(jSeparator2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\Exit button.png")); // NOI18N
        jMenuItem1.setText("Kết thúc");
        jMenuItem1.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        openDangNhap.add(jMenuItem1);

        jMenuBar1.add(openDangNhap);

        jMenu2.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\management_24px.png")); // NOI18N
        jMenu2.setText("Quản Lý");
        jMenu2.setMargin(new java.awt.Insets(5, 5, 5, 5));

        openNhanVien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/User group.png"))); // NOI18N
        openNhanVien.setText("Nhân Viên");
        openNhanVien.setMargin(new java.awt.Insets(5, 5, 5, 5));
        openNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openNhanVienActionPerformed(evt);
            }
        });
        jMenu2.add(openNhanVien);

        openMonAn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openMonAn.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\Steak-icon.png")); // NOI18N
        openMonAn.setText("Món Ăn");
        openMonAn.setMargin(new java.awt.Insets(5, 5, 5, 5));
        openMonAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMonAnActionPerformed(evt);
            }
        });
        jMenu2.add(openMonAn);

        openBanHang.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openBanHang.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\Add to basket.png")); // NOI18N
        openBanHang.setText("Bán Hàng");
        openBanHang.setMargin(new java.awt.Insets(5, 5, 5, 5));
        openBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBanHangActionPerformed(evt);
            }
        });
        jMenu2.add(openBanHang);

        jMenuBar1.add(jMenu2);

        jMenu4.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\statistics_24px.png")); // NOI18N
        jMenu4.setText("Thống Kê");
        jMenu4.setMargin(new java.awt.Insets(5, 5, 5, 5));

        openDoanhThu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.ALT_DOWN_MASK));
        openDoanhThu.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\Bar chart.png")); // NOI18N
        openDoanhThu.setText("DoanhThu");
        openDoanhThu.setMargin(new java.awt.Insets(5, 5, 5, 5));
        openDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDoanhThuActionPerformed(evt);
            }
        });
        jMenu4.add(openDoanhThu);

        jMenuBar1.add(jMenu4);

        jMenu6.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\help_24px.png")); // NOI18N
        jMenu6.setText("Trợ Giúp");
        jMenu6.setMargin(new java.awt.Insets(5, 5, 5, 5));

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Globe.png"))); // NOI18N
        jMenuItem13.setText("Hướng dẫn sử dụng");
        jMenuItem13.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem13);
        jMenu6.add(jSeparator5);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Text.png"))); // NOI18N
        jMenuItem14.setText("Giới thiệu sản phẩm");
        jMenuItem14.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem14);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTen, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(ToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(ToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        // button Káº¿t thÃºc:
        this.openNhanVien();
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnMonAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonAnActionPerformed
        openMonAn();
        
    }//GEN-LAST:event_btnMonAnActionPerformed

    private void btnBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanHangActionPerformed
        openBanHang();
        
    }//GEN-LAST:event_btnBanHangActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // button 
        openThongKe();
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongTinActionPerformed
        // Button 
        openAbout();
    }//GEN-LAST:event_btnThongTinActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        dangXuat();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        openDangNhap();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void openDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDangXuatActionPerformed
        // TODO add your handling code here:
        dangXuat();
    }//GEN-LAST:event_openDangXuatActionPerformed

    private void openDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDoiMatKhauActionPerformed
        // TODO add your handling code here:
       openDoiMatKhau();
  
    }//GEN-LAST:event_openDoiMatKhauActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        ketThuc();
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void openNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openNhanVienActionPerformed
        // TODO add your handling code here:
        this.openNhanVien();
        
    }//GEN-LAST:event_openNhanVienActionPerformed

    private void openMonAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMonAnActionPerformed
        // TODO add your handling code here:
        openMonAn();
    }//GEN-LAST:event_openMonAnActionPerformed

    private void openBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBanHangActionPerformed
        // TODO add your handling code here:
        openBanHang();
    }//GEN-LAST:event_openBanHangActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        openAbout();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        openWebsite();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void openDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDoanhThuActionPerformed
        // TODO add your handling code here:
        openThongKe();
    }//GEN-LAST:event_openDoanhThuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JPNHOME;
    private javax.swing.JToolBar ToolBar;
    private javax.swing.JButton btnBanHang;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnMonAn;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnThongTin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JMenuItem openBanHang;
    private javax.swing.JMenu openDangNhap;
    private javax.swing.JMenuItem openDangXuat;
    private javax.swing.JMenuItem openDoanhThu;
    private javax.swing.JMenuItem openDoiMatKhau;
    private javax.swing.JMenuItem openMonAn;
    private javax.swing.JMenuItem openNhanVien;
    // End of variables declaration//GEN-END:variables
}
