package util;

public interface Acceptable {
    // Các "mẫu mô tả dữ liệu" (regex) - chỉnh lại theo yêu cầu thực tế
    String ID_VALID      = "^[Vv]\\d{3}$";            // V001, V002, ...
    String NAME_VALID    = "^[A-Za-z0-9 ]{2,30}$";    // 2-30 ký tự chữ, số, khoảng trắng
    String COLOR_VALID   = "^[A-Za-z ]{2,15}$";       // chỉ chữ cái
    String BRAND_VALID   = "^[A-Za-z0-9 ]{2,20}$";
    String TYPE_VALID    = "^[A-Za-z ]{2,15}$";
    String INTEGER_VALID = "^\\d+$";                  // số nguyên không âm
    String PRICE_VALID   = "^\\d+(\\.\\d+)?$";        // số thực không âm
    String YEAR_VALID    = "^(19|20)\\d{2}$";         // 1900 - 2099

    static boolean isValid(String data, String pattern) {
        return data != null && data.matches(pattern);
    }
}
