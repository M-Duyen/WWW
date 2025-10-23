# Employee Management System

## Thông tin sinh viên
- **Họ tên:** Nguyen Thi My Duyen
- **MSSV:** 22721461
- **Môn học:** Lập trình WWW - Java

## Mô tả dự án
Hệ thống quản lý nhân viên và phòng ban với đầy đủ chức năng CRUD, tìm kiếm nâng cao.

## Công nghệ sử dụng
- Spring Boot 3.5.6
- Spring Data JPA
- Thymeleaf & JSP
- MariaDB
- Bootstrap 5.3.3
- Maven

## Chức năng chính

### 1. Quản lý Phòng ban (Department)
- Xem danh sách phòng ban
- Thêm phòng ban mới
- Chỉnh sửa thông tin phòng ban
- Xóa phòng ban
- Tìm kiếm phòng ban theo tên
- Xem danh sách nhân viên theo phòng ban

### 2. Quản lý Nhân viên (Employee)
- Xem danh sách nhân viên
- Thêm nhân viên mới
- Chỉnh sửa thông tin nhân viên
- Xóa nhân viên
- Tìm kiếm nhân viên:
  - Theo tên
  - Theo chức vụ (role)
  - Theo khoảng lương (salary range)
  - Theo độ tuổi (age range)
  - Theo ngày sinh (date of birth)

## Cấu trúc Database

### Table: departments
```sql
CREATE TABLE departments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(255) NOT NULL
);
```

### Table: employees
```sql
CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    salary DOUBLE NOT NULL,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);
```

## Cấu hình

### application.properties
```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/emp_db
spring.datasource.username=root
spring.datasource.password=123456789
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Hướng dẫn chạy

1. Tạo database:
```sql
CREATE DATABASE emp_db;
```

2. Cập nhật thông tin kết nối trong `application.properties`

3. Chạy ứng dụng:
```bash
mvn spring-boot:run
```

4. Truy cập:
- Home: http://localhost:8080/home
- Departments: http://localhost:8080/departments
- Employees: http://localhost:8080/employees

## API Endpoints

### Department APIs
- GET `/departments` - Danh sách phòng ban
- GET `/departments/new` - Form thêm phòng ban
- POST `/departments` - Tạo phòng ban mới
- GET `/departments/edit/{id}` - Form sửa phòng ban
- POST `/departments/edit/{id}` - Cập nhật phòng ban
- GET `/departments/delete/{id}` - Xóa phòng ban
- GET `/departments/search?search={name}` - Tìm kiếm phòng ban

### Employee APIs
- GET `/employees` - Danh sách nhân viên
- GET `/employees/new` - Form thêm nhân viên
- POST `/employees/add` - Tạo nhân viên mới
- GET `/employees/edit/{id}` - Form sửa nhân viên
- POST `/employees/edit/{id}` - Cập nhật nhân viên
- GET `/employees/delete/{id}` - Xóa nhân viên
- GET `/employees/search?search={name}` - Tìm theo tên
- GET `/employees/search/role?role={role}` - Tìm theo chức vụ
- GET `/employees/search/salary?minSalary={min}&maxSalary={max}` - Tìm theo lương
- GET `/employees/search/age?minAge={min}&maxAge={max}` - Tìm theo tuổi
- GET `/employees/search/dob/between?startDate={start}&endDate={end}` - Tìm theo ngày sinh
- GET `/employees/department/{deptId}` - Nhân viên theo phòng ban

## Screenshots

### 1. Home Page
Trang chủ với menu điều hướng đến Department và Employee

### 2. Department List
Danh sách phòng ban với các chức năng CRUD

### 3. Employee List
Danh sách nhân viên với tìm kiếm nâng cao

### 4. Employee Form
Form thêm/sửa nhân viên với validation

## Ghi chú
- Hệ thống hỗ trợ cả Thymeleaf và JSP
- Sử dụng Bootstrap 5 cho giao diện
- Validation dữ liệu phía client và server
- Thông báo success/error sau mỗi thao tác
