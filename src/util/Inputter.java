package util;

import java.util.Scanner;

public class Inputter {
    
    private Scanner ndl;

    public Inputter() {
        ndl = new Scanner(System.in);
    }

    public String getString(String mess) {
        System.out.print(mess);
        return ndl.nextLine().trim();
    }

    public int getInt(String mess) {
        String s = getString(mess, Acceptable.INTEGER_VALID, "Please enter an integer number!");
        return Integer.parseInt(s);
    }

    public double getDouble(String mess) {
        String s = getString(mess, Acceptable.PRICE_VALID, "Please enter a positive number!");
        return Double.parseDouble(s);
    }

    // ---------- Overloading: nhập kèm kiểm tra bằng regex ----------
    // Lặp lại cho đến khi dữ liệu hợp lệ
    public String getString(String mess, String pattern, String errorMess) {
        while (true) {
            String s = getString(mess);
            if (Acceptable.isValid(s, pattern)) {
                return s;
            }
            System.out.println(errorMess);
        }
    }

    // Dùng cho chức năng Update: để trống = giữ nguyên giá trị cũ,
    // nếu có nhập thì phải hợp lệ. Trả về "" nếu để trống.
    public String getOptionalString(String mess, String pattern, String errorMess) {
        while (true) {
            String s = getString(mess);
            if (s.isEmpty() || Acceptable.isValid(s, pattern)) {
                return s;
            }
            System.out.println(errorMess);
        }
    }

    // Hỏi Yes/No (dùng cho "Continue?", "Are you sure to delete?", ...)
    public boolean getYesNo(String mess) {
        String s = getString(mess + " (Y/N): ", "^[YyNn]$", "Please enter Y or N!");
        return s.equalsIgnoreCase("Y");
    }
}
