# Vehicle Management

Ứng dụng console Java quản lý phương tiện giao thông tại showroom, dữ liệu lưu trong file `vehicle.dat`.
Bài tập **LAB211 – J1.L.P00 – The Vehicle Management (Read and Write File)**.

- **Tác giả:** _<tên của bạn>_
- **Ngôn ngữ:** Java (JDK 11 trở lên, khuyến nghị 17)
- **Giao diện:** Console (menu dạng số)
- **Lưu trữ:** Object file (`vehicle.dat`) qua `Serializable`

---

## 1. Chức năng

Menu chính:

| # | Chức năng | Mô tả |
|---|-----------|-------|
| 1 | Add new vehicle | Nhập thông tin xe, kiểm tra hợp lệ, báo thành công/thất bại, hỏi có tiếp tục thêm không |
| 2 | Check exist vehicle | Kiểm tra ID xe có trong `vehicle.dat` hay không (`Existed Vehicle` / `No Vehicle Found!`) |
| 3 | Update vehicle | Cập nhật theo ID; **để trống = giữ nguyên giá trị cũ**; dữ liệu mới được kiểm tra hợp lệ |
| 4 | Delete vehicle | Xóa theo ID, có hỏi xác nhận trước khi xóa |
| 5 | Search vehicle | 5.1 Tìm theo tên (chứa chuỗi, sắp xếp tên **giảm dần**) · 5.2 Tìm theo ID |
| 6 | Display vehicle list | 6.1 Hiển thị tất cả · 6.2 Hiển thị xe có giá **nhỏ hơn** giá nhập (sắp xếp giá **giảm dần**) |
| 7 | Save all vehicles to file | Ghi danh sách hiện tại xuống `vehicle.dat` |
| 8 | Print all vehicles from file | Đọc và in dữ liệu đã lưu trong `vehicle.dat` |
| 9 | Quit | Thoát; nếu có thay đổi chưa lưu sẽ hỏi có muốn lưu không |

### Thuộc tính của Vehicle

`idVehicle`, `nameVehicle`, `colorVehicle`, `priceVehicle`, `brandVehicle`, `type`, `productYear`

### Quy tắc kiểm tra dữ liệu (regex trong `Acceptable`)

| Trường | Quy tắc |
|--------|---------|
| ID | `V` + 3 chữ số (ví dụ `V001`), không được trùng |
| Name | 2–30 ký tự: chữ cái, chữ số, khoảng trắng |
| Color | 2–15 chữ cái |
| Price | Số thực lớn hơn 0 |
| Brand | 2–20 ký tự: chữ cái, chữ số, khoảng trắng |
| Type | 2–15 chữ cái |
| Product year | 1900 – 2099 |

> Các quy tắc có thể chỉnh trong `util/Acceptable.java`.

---

## 2. Cấu trúc project

```
VehicleManagement/
├── src/
│   ├── model/        Vehicle.java            # Dữ liệu (Serializable)
│   ├── dao/          VehicleDAO.java         # Interface truy cập dữ liệu
│   │                 VehicleFileDAO.java     # Đọc/ghi vehicle.dat
│   ├── service/      VehicleService.java     # Nghiệp vụ trên danh sách
│   ├── view/         Menu.java               # Hiển thị menu, nhận lựa chọn
│   │                 VehicleView.java        # In bảng danh sách xe
│   ├── controller/   VehicleController.java  # Điều phối: nhập liệu → service → view
│   ├── util/         Acceptable.java         # Mẫu regex và hàm kiểm tra
│   │                 Inputter.java           # Nhập dữ liệu từ bàn phím
│   └── main/         Program.java            # Điểm khởi chạy
├── lib/
├── bin/
└── vehicle.dat                               # Tạo ra sau lần Save đầu tiên
```

---

## 3. Kiến trúc

Chương trình theo mô hình **MVC / nLayers** kết hợp **DAO pattern**:

```
Program → Controller → Service → DAO → vehicle.dat
              ↓
        View (Menu, VehicleView)
```

| Tầng | Trách nhiệm | Không làm |
|------|-------------|-----------|
| `model` | Mô tả dữ liệu (`Vehicle`) | Nghiệp vụ, I/O |
| `dao` | Đọc/ghi file | Kiểm tra trùng ID, sắp xếp, in màn hình |
| `service` | Nghiệp vụ: add, update, delete, search, theo dõi trạng thái đã lưu | Chạm file trực tiếp, in màn hình |
| `view` | Hiển thị menu và bảng kết quả | Nghiệp vụ |
| `controller` | Nhập liệu, gọi service, gọi view | Đọc/ghi file |
| `util` | Công cụ dùng chung: nhập liệu, kiểm tra regex | Phụ thuộc vào tầng khác |

### Một số điểm thiết kế

- **Validation bằng regex** (`Acceptable` + `Inputter`) thay vì `try...catch`, nhập sai thì nhắc và nhập lại.
- **Inputter** tái sử dụng được cho mọi trường (`getString`, `getInt`, `getDouble`, thêm `getOptionalString` cho Update và `getYesNo` cho xác nhận), chỉ dùng **một `Scanner`** cho toàn chương trình.
- **DAO là interface**: sau này đổi sang database chỉ cần thay lớp triển khai, `service` không phải sửa.
- **Đọc file** lặp đến khi gặp `EOFException`; **ghi/đọc** dùng `try-with-resources` để tự đóng luồng.
- **Cờ `isSaved`** trong service để cảnh báo khi thoát mà chưa lưu.

---

## 4. Cách chạy

### Yêu cầu

- JDK 11 trở lên (`java -version`, `javac -version`)
- VS Code + **Extension Pack for Java**
- Không cần thư viện ngoài (thư mục `lib` có thể để trống)

### Chạy trong VS Code

1. Mở thư mục `VehicleManagement` bằng **File → Open Folder** (thư mục chứa `src`, `bin`, `lib`).
2. Đảm bảo `.vscode/settings.json` có:
   ```json
   {
       "java.project.sourcePaths": ["src"],
       "java.project.outputPath": "bin",
       "java.project.referencedLibraries": ["lib/**/*.jar"]
   }
   ```
3. Mở `src/main/Program.java` và bấm **Run** phía trên hàm `main` (hoặc `F5`).

> Chương trình đọc bàn phím bằng `Scanner`, nên cần chạy trong **Terminal** (`"console": "integratedTerminal"` trong `launch.json`).

### Chạy bằng dòng lệnh

```bash
# Từ thư mục gốc project
javac -d bin -sourcepath src src/main/Program.java
java -cp bin main.Program
```

---

## 5. Hướng dẫn sử dụng nhanh

1. Chọn `1` để thêm vài xe.
2. Chọn `6` → `1` để xem danh sách, `6` → `2` để lọc theo giá.
3. Chọn `5` để tìm theo ID hoặc tên.
4. Chọn `7` để **lưu xuống file**.
5. Chọn `9` để thoát; lần chạy sau dữ liệu được nạp lại từ `vehicle.dat`.

### Lưu ý về dữ liệu

- **Add / Update / Delete chỉ thay đổi dữ liệu trong bộ nhớ.** Dữ liệu chỉ vào `vehicle.dat` khi chọn **7 (Save)** hoặc đồng ý lưu khi **Quit**.
- Chức năng **2** và **8** đọc từ file, nên xe vừa thêm mà chưa lưu sẽ chưa xuất hiện ở hai chức năng này.
- Đổi cấu trúc class `Vehicle` (thêm/bớt thuộc tính) có thể làm file `vehicle.dat` cũ không đọc được; khi đó hãy xóa file cũ.

---

## 6. Ví dụ dữ liệu nhập

```
ID:    V001
Name:  Civic
Color: White
Price: 25000
Brand: Honda
Type:  Sedan
Year:  2022
```
