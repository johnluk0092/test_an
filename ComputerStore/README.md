**Phần mềm Bán Linh Kiện Máy Tính – ComputerStore**

**Đồ án cuối kỳ môn Lập trình trên thiết bị di động**  
ComputerStore là ứng dụng bán hàng dành riêng cho cửa hàng kinh doanh linh kiện máy tính, mang đến trải nghiệm mua sắm trực tuyến dễ dàng và hiệu quả cho người dùng.

---

### **Tính năng nổi bật của ứng dụng**  
1. **Hiển thị danh sách sản phẩm**: Dễ dàng duyệt qua các sản phẩm đa dạng, từ linh kiện phần cứng đến phụ kiện máy tính.  
2. **Phân loại sản phẩm theo danh mục**: Người dùng có thể nhanh chóng tìm thấy sản phẩm mong muốn thông qua các danh mục rõ ràng.  
3. **Tìm kiếm và sắp xếp sản phẩm**: Hỗ trợ bộ lọc thông minh để tìm kiếm sản phẩm theo giá, tên, hoặc tính năng.  
4. **Thêm sản phẩm vào giỏ hàng**: Cho phép quản lý giỏ hàng với giao diện thân thiện và thao tác dễ dàng.  
5. **Xem lịch sử đơn hàng**: Cung cấp thông tin chi tiết về các giao dịch đã thực hiện.  
6. **Đặt hàng trực tuyến**: Trải nghiệm mua sắm liền mạch từ chọn sản phẩm đến thanh toán.  

---

### **Tổng quan kỹ thuật**  
- **Xác thực và mã hóa**: Tích hợp Firebase Authentication để đảm bảo an toàn thông tin người dùng.  
- **Cơ sở dữ liệu**: Sử dụng Firestore để quản lý dữ liệu theo thời gian thực và SQLite thông qua Room để lưu trữ cục bộ.  
- **Kiến trúc ứng dụng**: Phát triển theo mô hình MVVM kết hợp Dependency Injection (Hilt) để tối ưu hóa và tái sử dụng mã nguồn.  

---

### **Tiêu chí đạt được của đồ án**  
- **Màn hình giao diện**: Vượt chỉ tiêu với **10 màn hình** thay vì chỉ 5, đáp ứng mọi thao tác của người dùng.  
- **Sử dụng layout**: Kết hợp nhiều loại layout khác nhau để nâng cao trải nghiệm giao diện.  
- **Giao tiếp giữa các màn hình**: Tích hợp **Intent** để liên kết các tính năng như đặt hàng, xem chi tiết sản phẩm, và quản lý tài khoản.  
- **Validation**: Kiểm tra và xác thực dữ liệu người dùng khi đăng ký và đăng nhập.  
- **WebView**: Hỗ trợ xem thông tin chính sách dịch vụ trực tiếp trong ứng dụng thông qua phần **Tài khoản > Chính sách dịch vụ**.  
- **Kết nối API**: Tương tác với API để lấy dữ liệu sản phẩm và lưu trữ vào Firestore.  
- **SQLite**: Lưu trữ dữ liệu cục bộ nhằm tối ưu hóa hiệu suất ứng dụng.  
- **Bảo mật**: Tăng cường bảo mật thông tin qua Firebase Authentication và mã hóa dữ liệu.  

---

### **Điểm nổi bật của dự án**  
Ứng dụng **ComputerStore** không chỉ đáp ứng tốt yêu cầu của một cửa hàng bán linh kiện máy tính mà còn đặt trọng tâm vào việc nâng cao trải nghiệm người dùng, đảm bảo tính bảo mật, hiệu suất cao và sự chuyên nghiệp trong thiết kế. Đây là một dự án đầy tiềm năng, thể hiện sự sáng tạo và khả năng áp dụng công nghệ vào thực tế của nhóm phát triển.