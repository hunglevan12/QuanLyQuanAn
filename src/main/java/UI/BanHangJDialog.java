/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import DAO.HoaDonDAO;
import DAO.MonAnDAO;
import DAO.NhanVienDAO;
import Database.Auth;
import Database.DB;
import Database.MsgBox;
import Database.XDate;
import Database.XDecimal;
import Database.check;
import entity.HoaDon;

import entity.MonAn;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author ACER
 */
public class BanHangJDialog extends javax.swing.JDialog {

    HoaDonDAO hddao = new HoaDonDAO();
    DecimalFormat df = new DecimalFormat("#########"); 
    MonAnDAO madao = new MonAnDAO();
    int row = -1;
    int index =0;
    /**
     * Creates new form BanHangJDialog
     */
    public BanHangJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        init();
        HienThi();
    }

    void init() {
        setTitle("QUẢN LÝ BÁN HÀNG");
        setLocationRelativeTo(null);
        setTotal();
        FillTable(hddao.ChuaThanhToan());
        fillcombobox();
    }

    void HienThi() {
        txtMaNV.setText(Auth.user.getMaNV());
        txtNgayThanhToan.setText(XDate.toString1(new Date(), "dd/MM/yyyy"));
    }
     
     //lấy thông tin từ đt HoaDon đưa lên form
    void setForm(HoaDon model) {
        txtHoaDon.setText(String.valueOf(model.getIDHoaDon()));
        txtMaNV.setText(String.valueOf(Auth.user.getMaNV()));
        txtTenKH.setText(String.valueOf(model.getTenKH()));
        cboTenMon.setToolTipText(String.valueOf(model.getMaMon()));
        cboTenMon.setSelectedItem(madao.selectById(model.getMaMon()));
        txtBan.setText(String.valueOf(model.getBan()));
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));
        txtThanhTien.setText(String.valueOf(model.getThanhTien()));
//        txtNgayThanhToan.setText(XDate.toString1(model.getNgayThanhToan()));
       
        lblTrangThai.setText(String.valueOf(model.getTrangThai()));
    }
    
    //lấy thông tin trên form điền vào đt HoaDon
    //return chuyenDe
    HoaDon getForm() {
        HoaDon model = new HoaDon();
        model.setIDHoaDon(String.valueOf(txtHoaDon.getText()));
        model.setMaNV(Auth.user.getMaNV());
        model.setTenKH(String.valueOf(txtTenKH.getText()));
        MonAn monan = (MonAn) cboTenMon.getSelectedItem();
        model.setMaMon(monan.getMaMon());
        model.setBan(String.valueOf(txtBan.getText()));
        model.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        model.setThanhTien(Double.valueOf(txtThanhTien.getText()));
//        model.setNgayThanhToan(XDate.toDate(lblNgayThanhToan.getText()));
       

        return model;

    }

    void selectComboBox() {
//        madao.selectAll().forEach(monan -> {
//            if (monan.getTenMon().equalsIgnoreCase((String) cboTenMon.getSelectedItem())) {
//                txtGiaTien.setText(String.valueOf(monan.getGiaTien()));
//               
//            }
//        });
          MonAn monan = (MonAn) cboTenMon.getSelectedItem();
          lblGiaTien.setText(df.format(monan.getGiaTien()));
    }

    

    void fillcombobox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenMon.getModel();
        model.removeAllElements();
        try {
            List<MonAn> list = madao.selectAll();
            if(!list.isEmpty()){
              for (MonAn ma : list) {
                model.addElement(ma);
            }   
            }
                    
           
        } catch (Exception e) {
            MsgBox.alert(this, e.getMessage());
        }
    }

    

    void FillTable(List<HoaDon> list) {
        DefaultTableModel model = (DefaultTableModel) tbBanHang1.getModel();
        model.setRowCount(0);
        try {

            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getIDHoaDon(),
                    hd.getMaNV(),
                    hd.getTenKH(),
                    hd.getMaMon(),
                    hd.getBan(),
                    hd.getSoLuong(),
                    hd.getThanhTien(),
                    XDate.toString1(hd.getNgayThanhToan(), "dd/MM/yyyy"),
                    hd.getTrangThai()};
                 
               
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, e.getMessage());
        }
    }

    void setTotal() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        MonAn monan = (MonAn) cboTenMon.getSelectedItem();
                        if (!txtSoLuong.getText().isEmpty()) {
                            int count = Integer.parseInt(txtSoLuong.getText());
                            
                            double total = (count * monan.getGiaTien());
                            txtThanhTien.setText(df.format(total));
                        } else {
                            txtThanhTien.setText("");
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BanHangJDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }.start();
    }

    
   
    
    void insert(){
        HoaDon model = getForm();
            try{
                hddao.insertHoaDon(model);
                FillTable(hddao.ChuaThanhToan());
                clearForm();
                MsgBox.alert(this, "Order thành công");
            }catch (Exception e){
                MsgBox.alert(this, e.getMessage());
            }
        
    }
    
    HoaDon UpdateHoaDon(){
        HoaDon model = new HoaDon();
        model.setIDHoaDon(txtHoaDon.getText());
        model.setTrangThai("Đã Thanh Toán");
        return model;
    }
      void update() {
        HoaDon model =  UpdateHoaDon();           
            hddao.update(model);
            lblTrangThai.setText("Đã Thanh Toán");
            this.FillTable(hddao.ChuaThanhToan());
            MsgBox.alert(this, " Thanh Toán Thành Công!!!");
    }
      void edit(){
        try{
            String idhoadon = (String)tbBanHang1.getValueAt(this.row, 0);
            HoaDon model = hddao.selectById(idhoadon);
            if(model !=null){
                setForm(model);
                Tabs.setSelectedIndex(0);
            }   
               
            }catch(Exception e){
               JOptionPane.showMessageDialog(this, e.initCause(e));
        }
    }
      
     void clearForm(){
        txtHoaDon.setText("");
        txtTenKH.setText("");
        cboTenMon.setSelectedIndex(0);
        txtBan.setText("");
        txtSoLuong.setText("");
        txtThanhTien.setText("");
        lblTrangThai.setText("");  
    }
     
     void ClearHoaDon(){
         txtInHoaDon.setText("");
     }
     
//      public boolean checkBan(JTextField txt) {
//        txt.setBackground(white);
//        if (hddao.selectBan(txt.getText()) == null) {
//            return true;
//        } else {
//            txt.setBackground(pink);
//            MsgBox.alert(this, txtBan.getName() + " Bàn đã có khách.");
//            return false;
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnOrder = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        Tabs = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtHoaDon = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtBan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboTenMon = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        txtThanhTien = new javax.swing.JTextField();
        lblGiaTien = new javax.swing.JLabel();
        txtNgayThanhToan = new javax.swing.JTextField();
        lblTrangThai = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtInHoaDon = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbBanHang1 = new javax.swing.JTable();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\shopping_cart_32px.png")); // NOI18N
        jLabel1.setText("QUẢN LÝ BÁN HÀNG");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnOrder.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\create_order_24px.png")); // NOI18N
        btnOrder.setText("Order");
        btnOrder.setBorder(null);
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        btnHoaDon.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\microsoft_word_32px.png")); // NOI18N
        btnHoaDon.setText("In Hóa Đơn");
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        btnThanhToan.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\pay_24px.png")); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\reset_24px.png")); // NOI18N
        btnReset.setText("Reset Hóa Đơn");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThanhToan)
                .addGap(174, 174, 174)
                .addComponent(btnHoaDon)
                .addGap(150, 150, 150)
                .addComponent(btnReset))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHoaDon)
                    .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        Tabs.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setForeground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 2));

        jLabel2.setText("ID Hóa Đơn:");

        jLabel4.setText("Mã NV:");

        jLabel5.setText("Món:");

        jLabel6.setText("Bàn: ");

        jLabel7.setText("Giá Tiền:");

        jLabel8.setText("Số Lượng:");

        jLabel9.setText("Thành Tiền:");

        jLabel10.setText("Trạng Thái:");

        cboTenMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenMonActionPerformed(evt);
            }
        });

        jLabel12.setText("Ngày Thanh Toán:");

        jLabel13.setText("Tên Khách Hàng");

        lblGiaTien.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayThanhToan)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboTenMon, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtHoaDon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaNV)
                    .addComponent(lblGiaTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtThanhTien)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12))
                        .addGap(0, 136, Short.MAX_VALUE))
                    .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(lblTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn Và In Hóa Đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel4.setForeground(new java.awt.Color(0, 153, 153));
        jPanel4.setToolTipText("");

        txtInHoaDon.setColumns(20);
        txtInHoaDon.setRows(5);
        txtInHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txtInHoaDon);

        tbBanHang1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbBanHang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Hóa Đơn", "Mã NV", "Tên KH", "Mã Món", "Bàn", "Số Lượng", "Thành Tiền", "Ngày Thanh Toán", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbBanHang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbBanHang1MousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbBanHang1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        Tabs.addTab("Hóa Đơn", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(429, 429, 429))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tabs, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(Tabs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        if (
                 check.checkNullText(txtHoaDon)
                && check.checkNullText(txtTenKH)
                && check.checkNullText(txtBan)
                && check.checkNullText(txtSoLuong)){
            if (
                     check.checkName(txtTenKH)
                    && check.checksoluong(txtSoLuong)
                    ){
                    insert();        
            }
        }
    }//GEN-LAST:event_btnOrderActionPerformed
    
    void printHoaDon(){
          HoaDon hd = hddao.printHoaDon(txtHoaDon.getText());
        txtInHoaDon.append("\t\t QUÁN ĂN RICE FRESH \n\n  "
                + "                \t\t HÓA ĐƠN BÁN HÀNG \n\n"
                + "========================================================================================\n\n"
                + "ĐC:222 Trần Duy Hưng,Cầu Giấy,Hà Nội \n\n"
                + "SDT: 0359623642 \n\n"
                + "========================================================================================\n\n"
                + "Mã Hóa Đơn:              \t\t\t" + txtHoaDon.getText()                    + "\n\n"
                + "Mã Nhân Viên:            \t\t\t" + txtMaNV.getText()                      + "\n\n"
                + "Tên Khách Hàng:          \t\t\t" + hd.getTenKH()                          + "\n\n"
                + "Món:                     \t\t\t" + hddao.SanPham(txtHoaDon.getText())     + "\n\n"
                + "Bàn:                     \t\t\t" + hd.getBan()                            + "\n\n"
                + "Thành Tiền:              \t\t\t" + hddao.GiaTien(txtHoaDon.getText())     + "\n\n"
                + "Số Lượng:                \t\t\t" + hddao.soluong(txtHoaDon.getText())     + "\n\n"
                + "Ngày Thanh Toán:         \t\t\t" + hd.getNgayThanhToan()                  + "\n\n"
                + "Trạng Thái:              \t\t\t" + "Đã Thanh Toán"                        + "\n\n"
                + "=========================================================================================\n\n"
                + "Thành Tiền:              \t\t\t" + XDecimal.ad.format(hd.getThanhTien())  + "\n\n"   
                        
                + "=========================================================================================\n\n"     
                + "\t\t CẢM ƠN QUÝ KHÁCH VÀ HẸN GẶP LẠI \n\n"
                + "Mọi thắc mắc xin liên hệ hotline: 18005555 "
        );
    }
    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
             printHoaDon();
             update();       
             
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        try {
            // TODO add your handling code here:
            txtInHoaDon.print();
        } catch (PrinterException ex) {
            Logger.getLogger(BanHangJDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void cboTenMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenMonActionPerformed
        // TODO add your handling code here:
        selectComboBox();
    }//GEN-LAST:event_cboTenMonActionPerformed

    private void tbBanHang1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBanHang1MousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tbBanHang1.rowAtPoint(evt.getPoint());
            if (this.row >= 0) {
                this.edit();
//                Tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tbBanHang1MousePressed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        this.ClearHoaDon();
    }//GEN-LAST:event_btnResetActionPerformed
    
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
            java.util.logging.Logger.getLogger(BanHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BanHangJDialog dialog = new BanHangJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Tabs;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboTenMon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblGiaTien;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JTable tbBanHang1;
    private javax.swing.JTextField txtBan;
    private javax.swing.JTextField txtHoaDon;
    private javax.swing.JTextArea txtInHoaDon;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayThanhToan;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtThanhTien;
    // End of variables declaration//GEN-END:variables

   
}
