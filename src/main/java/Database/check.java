/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import entity.NhanVien;
import static java.awt.Color.pink;
import static java.awt.Color.red;
import static java.awt.Color.white;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ACER
 */
public class check {
//    1-10 kí tự
//    a-z, A-Z, 0-9
//     */
    public static boolean checkMaNV(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{4,10}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải có 4-10 kí tự\n  chữ thường,hoa hoặc số.");
            return false;
        }
    }
    
    
    public static boolean checkIDHoaDon(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{3,10}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải có 3-10 kí tự\n  bất kì và bắt đầu bằng HD (VD:HD1)");
            return false;
        }
    }
   

    /*
    đúng 5 kí tự
    a-z, A-Z, 0-9
     */
    public static boolean checkMaMon(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{3,10}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải có đúng 3 đến 10 kí tự\n bất kì");
            return false;
        }
    }
    
    public static boolean checkBan(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{3,10}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " 3-10 kí tự chữ hoặc số và không phải kí tự đặc biệt.");
            return false;
        }
    }
    
    //pass từ 3-16 kí tự
    public static boolean checkPass(JPasswordField txt) {
        txt.setBackground(white);
        if (txt.getPassword().length > 2 && txt.getPassword().length < 17) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải có từ 3-16 kí tự.");
            return false;
        }
    }

    public static boolean isValidDate(String inDate) {

        if (inDate == null) {
            return false;
        }

        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (inDate.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }

        dateFormat.setLenient(false);

        try {
            //parse the inDate parameter
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    //định dạng dd/MM/yyyy (hoặc d/M/yyyy nếu là số 0 đứng trước)
    public static boolean checkDate(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        if (isValidDate(id)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " không đúng định dạng dd/MM/yyyy");
            return false;
        }
    }

    //gồm các ký tự chữ đấu cách
    //từ 3-25 kí tự
    public static boolean checkName(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{3,25}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải là tên tiếng việt hoặc không đấu\ntừ 3-25 kí tự");
            return false;
        }
    }

    //bất kì kí tự nào
    //từ 3-50 kí tự
    public static boolean checkTen(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = ".{3,50}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải từ 3-50 kí tự.");
            return false;
        }
    }

    

    //gồm 10 số 
    //các đầu 3 số của nhà mạng
    public static boolean checkSDT(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "(086|096|097|098|032|033|034|035|036|037|038|039|089|090|093|070|079|077|078|076|088|091|094|083|084|085|081|082|092|056|058|099|059)[0-9]{7}";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải gồm 10 số\nđúng các đầu số của nhà mạng.");
            return false;
        }
    }

    public static boolean checkEmail(JTextField txt) {
        txt.setBackground(white);
        String id = txt.getText();
        String rgx = "^[a-zA-Z][a-zA-Z0-9_\\.]{2,32}@[a-zA-Z0-9]{2,10}(\\.[a-zA-Z0-9]{2,4}){1,2}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " không đúng định dạng");
            return false;
        }
    }

    //gio là int >0
    public static boolean checkSoLuong(JTextField txt) {
        txt.setBackground(white);
        try {
            int sl = Integer.parseInt(txt.getText());
            if (sl >= 0) {
                return true;
            } else {
                txt.setBackground(pink);
                MsgBox.alert(txt.getRootPane(), txt.getName() + " phải lớn hơn bằng 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải là số .");
            return false;
        }
    }

    //học phí là float >0
    public static boolean checkGiaTien(JTextField txt) {
        txt.setBackground(white);
        try {
            float hp = Float.parseFloat(txt.getText());
            if (hp >= 0) {
                return true;
            } else {
                txt.setBackground(pink);
                MsgBox.alert(txt.getRootPane(), txt.getName() + " phải là lớn hơn bằng 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải là số .");
            return false;
        }
    }

    public static boolean checksoluong(JTextField txt) {
        txt.setBackground(white);
        try {
            float sl = Integer.parseInt(txt.getText());
            if ((sl >= 0 && sl <= 1000 )) {
                return true;
            } else {
                txt.setBackground(pink);
                MsgBox.alert(txt.getRootPane(), txt.getName() + " Số Lượng phải là trong khoảng 0-1000 (không phải là số âm).");
                return false;
            }
        } catch (NumberFormatException e) {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), txt.getName() + " phải là số.");
            return false;
        }
    }

    public static boolean checkNullText(JTextField txt) {
        txt.setBackground(white);
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), "Không được để trống " + txt.getName());
            return false;
        }
    }

    public static boolean checkNullText(JTextArea txt) {
        txt.setBackground(white);
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), "Không được để trống " + txt.getName());
            return false;
        }
    }

    public static boolean checkNullPass(JPasswordField txt) {
        txt.setBackground(white);
        if (txt.getPassword().length > 0) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBox.alert(txt.getRootPane(), "Không được để trống " + txt.getName());
            return false;
        }
    }
    
}
