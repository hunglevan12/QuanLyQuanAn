/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package UI;

import DAO.MonAnDAO;
import Database.MsgBox;
import Database.XImage;
import Database.check;
import entity.MonAn;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;







/**
 *
 * @author tquan
 */
public class MonAnJDialog extends javax.swing.JDialog {
    JFileChooser fileChooser = new JFileChooser();
    MonAnDAO dao = new MonAnDAO(); 
    
    int row = -1;
    /**
     * Creates new form ChuyenDeJDialog
     */
    public MonAnJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        fillTable();
    }
    void init(){
        setTitle("QUẢN LÝ MÓN ĂN");
        setLocationRelativeTo(null);
        this.row = -1;
        this.updateStatus();
    }
    void fillTable(){
        DefaultTableModel model = (DefaultTableModel)tbMonAn.getModel();
        model.setRowCount(0);
        try{
//             List<MonAn>list = dao.selectAll();
             String keyword = txtTimKiem.getText();
             List<MonAn> list = dao.selectByKeyword(keyword);
            for(MonAn kh:list){
                Object[] row ={
                    kh.getMaMon(),
                    kh.isLoai()?"Thức Ăn":"Thức Uống",
                    kh.getTenMon(),
                    kh.getDonVi(),
                    kh.getSoLuong(),
                    kh.getGiaTien(),
                    kh.getHinh()                   
                };
                model.addRow(row);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    
    void chonanh(){
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            Image img= icon.getImage();
            lblHinh.setIcon(new ImageIcon(img.getScaledInstance(lblHinh.getWidth(), 
                    lblHinh.getHeight(), Image.SCALE_SMOOTH)));
            lblHinh.setToolTipText(file.getName());
        }
    }
    
    void setForm(MonAn model){
        txtMaMon.setText(model.getMaMon());
        rdoThucAn.setSelected(model.isLoai());
        rdoThucUong.setSelected(!model.isLoai());
        txtTen.setText(model.getTenMon());
        txtDonVi.setText(model.getDonVi());
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));
        txtGiaTien.setText(String.valueOf(model.getGiaTien()));
        lblHinh.setToolTipText(model.getHinh());
        if(model.getHinh() != null){
            lblHinh.setIcon(XImage.read(model.getHinh()));
            /*
            ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
            void setIcon(ImageIcon icon) set Icon cho lbl
            */
        }else{
            lblHinh.setIcon(XImage.read("noImage.png"));
        }
    }
    MonAn getForm(){
        MonAn mn = new MonAn();
        mn.setMaMon(txtMaMon.getText());
        mn.setLoai(rdoThucAn.isSelected());
        mn.setTenMon(txtTen.getText());
        mn.setDonVi(txtDonVi.getText());
        mn.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        mn.setGiaTien(Double.valueOf(txtGiaTien.getText()));
        mn.setHinh(lblHinh.getToolTipText());
        return mn;
    }
      
      void insert(){
        MonAn model = getForm();
        try{
            if( dao.insert(model) ==1){
                this.fillTable();
                this.clear();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công");
            }
            
        }
        catch(HeadlessException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
      }
      
      void update() {
        MonAn model;
        model = getForm();
        try {
            dao.update(model);
            this.clear();
            this.fillTable();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, e.getMessage());
        }
    }
     
    //xóa bản ghi trong CSDL theo maCD lấy trên form
    //load lại bảng
    //xóa trắng form, chuyển sang insertable
    void delete() {
        if (MsgBox.confirm(this, "Bạn có muốn xóa hay không?")) {
            String mamon = txtMaMon.getText();
            try {
                dao.delete(mamon);
                this.fillTable();
                this.clear();
                MsgBox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, e.getMessage());
            }
        }
    }
    
    
    
    void clear() {
        this.setForm(new MonAn());
        rdoThucAn.setSelected(true);
    }
    //lấy maCD theo index, lấy đt chuyenDe từ CSDL theo maCD 
    //hiển thị thông tin từ đt chuyenDe lên form, chuyển sang editable
    void edit() {
        try {
            String MaMon = (String) tbMonAn.getValueAt(this.row, 0);
            MonAn monan = dao.selectById(MaMon);
            if (monan != null) {
                this.setForm(monan);
                
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    
    public boolean checkNullHinh(){
        if(lblHinh.getToolTipText()!=null){
            return true;
        }else{
            MsgBox.alert(this, "Không được để trống hình.");
            return false;
        }
    }
    
    public boolean checkTrungMa(JTextField txt) {
        txt.setBackground(white);
        if (dao.selectById(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(this,"Mã Món"+ txtMaMon.getText()+ " đã tồn tại.");
            return false;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        lblTitle = new javax.swing.JLabel();
        Tabs = new javax.swing.JTabbedPane();
        pnlEdit = new javax.swing.JPanel();
        lblHinhANh = new javax.swing.JLabel();
        tabs = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaMon = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDonVi = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtGiaTien = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        rdoThucAn = new javax.swing.JRadioButton();
        rdoThucUong = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMonAn = new javax.swing.JTable();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(153, 153, 153));
        lblTitle.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\Real Food for Meals_24px.png")); // NOI18N
        lblTitle.setText("QUẢN LÝ MÓN ĂN");

        Tabs.setBackground(new java.awt.Color(255, 255, 255));

        pnlEdit.setBackground(new java.awt.Color(255, 255, 255));

        lblHinhANh.setForeground(new java.awt.Color(0, 0, 204));

        tabs.setBackground(new java.awt.Color(255, 255, 255));
        tabs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabsMousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Mã Món:");

        jLabel3.setText("Loại:");

        jLabel4.setText("Tên:");

        jLabel5.setText("Đơn Vị:");

        jLabel6.setText("Số Lượng:");

        jLabel7.setText("Giá Tiền:");

        jLabel8.setText("Hình:");

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhMousePressed(evt);
            }
        });

        buttonGroup1.add(rdoThucAn);
        rdoThucAn.setText("Thức Ăn");

        buttonGroup1.add(rdoThucUong);
        rdoThucUong.setText("Thức Uống");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 50, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(txtGiaTien))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDonVi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoThucAn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdoThucUong)
                                .addGap(6, 6, 6))
                            .addComponent(txtMaMon, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuong))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rdoThucAn)
                    .addComponent(rdoThucUong))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        tbMonAn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbMonAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Món", "Loai", "Tên", "Đơn Vị", "Số Lượng ", "Giá Tiền", "Hình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbMonAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbMonAnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbMonAnMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbMonAn);

        btnSua.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\edit_24px.png")); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\delete_trash_24px.png")); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\add_24px.png")); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\change_user_26px.png")); // NOI18N
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabsLayout = new javax.swing.GroupLayout(tabs);
        tabs.setLayout(tabsLayout);
        tabsLayout.setHorizontalGroup(
            tabsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabsLayout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(tabsLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE))))
        );
        tabsLayout.setVerticalGroup(
            tabsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditLayout = new javax.swing.GroupLayout(pnlEdit);
        pnlEdit.setLayout(pnlEditLayout);
        pnlEditLayout.setHorizontalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinhANh, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(768, Short.MAX_VALUE))
            .addGroup(pnlEditLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlEditLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlEditLayout.setVerticalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditLayout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(lblHinhANh, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addGap(226, 226, 226)
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast)))
            .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlEditLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        Tabs.addTab("THỰC ĐƠN", pnlEdit);

        txtTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnTimKiem.setIcon(new javax.swing.ImageIcon("D:\\caijava\\PMQuanLyQuanAn\\src\\main\\resources\\search_16px.png")); // NOI18N
        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel1.setText("Tìm Kiếm Món Ăn:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Tabs)
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnTimKiem)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle)
                .addGap(385, 385, 385))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabsMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabsMousePressed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if(     
                check.checkNullText(txtTen)&&
                check.checkNullText(txtDonVi)&&
                check.checkNullText(txtSoLuong)&&
                check.checkNullText(txtGiaTien)&&
                checkNullHinh()){
            if(     
                    check.checkTen(txtTen)&&
                    check.checkName(txtDonVi)&&
                    check.checksoluong(txtSoLuong)&&
                    check.checkGiaTien(txtGiaTien)){             
                     update();
                }
            } 
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if(check.checkNullText(txtMaMon)&&
                check.checkNullText(txtTen)&&
                check.checkNullText(txtDonVi)&&
                check.checkNullText(txtSoLuong)&&
                check.checkNullText(txtGiaTien)&&
                checkNullHinh()){
            if(check.checkMaMon(txtMaMon)&&
                    check.checkTen(txtTen)&&
                    check.checkName(txtDonVi)&&
                    check.checksoluong(txtSoLuong)&&
                    check.checkGiaTien(txtGiaTien)){
                if(checkTrungMa(txtMaMon)){
                    insert();
                }
            }
        }       
    }//GEN-LAST:event_btnThemActionPerformed

    private void tbMonAnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMonAnMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbMonAnMouseReleased

    private void tbMonAnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMonAnMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tbMonAn.rowAtPoint(evt.getPoint());
            if (this.row >= 0) {
                this.edit();
                Tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tbMonAnMousePressed

    private void lblHinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMousePressed
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
            chonanh();
        }
    }//GEN-LAST:event_lblHinhMousePressed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        this.fillTable();
        this.clear();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed
    
   void first(){
        row =0;
        edit();
    }
    
    void prev(){
        if(row>0){
            row--;
            edit();
        }
    }
    
    void next() {
        if(row < tbMonAn.getRowCount() - 1){
           row++;
           edit();
         }
    }
    void last(){
        row = tbMonAn.getRowCount() -1;
        edit();
    }
    
        
    
    void updateStatus(){
        boolean edit = this.row >=0;
        boolean first = this.row == 0;
        boolean last = this.row == tbMonAn.getRowCount() -1;
        txtMaMon.setEditable(!edit);
        // insert thì ko update,delete
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(!edit);
        btnXoa.setEnabled(!edit);
        
        btnFirst.setEnabled(!edit &&!first);
        btnPrev.setEnabled(!edit &&!first);
        btnNext.setEnabled(!edit &&!first);
        btnLast.setEnabled(!edit &&!first);
    }   
    
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
            java.util.logging.Logger.getLogger(MonAnJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonAnJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonAnJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonAnJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MonAnJDialog dialog = new MonAnJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblHinhANh;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JRadioButton rdoThucAn;
    private javax.swing.JRadioButton rdoThucUong;
    private javax.swing.JPanel tabs;
    private javax.swing.JTable tbMonAn;
    private javax.swing.JTextField txtDonVi;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMaMon;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
