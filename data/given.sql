-- Xóa database nếu đã tồn tại
IF DB_ID('HotelManagement') IS NOT NULL
BEGIN
    ALTER DATABASE HotelManagement SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE HotelManagement;
END
GO

-- Create Database
CREATE DATABASE HotelManagement;
GO

USE HotelManagement;
GO

-- ==========================
-- 1. Staff Table (Tạo trước để GUEST và TAX_CONFIG có thể reference)
-- ==========================
CREATE TABLE STAFF (
    StaffID INT IDENTITY(1,1) PRIMARY KEY,
    FullName NVARCHAR(100) NOT NULL,
    Role NVARCHAR(50) CHECK (Role IN ('Receptionist', 'Manager', 'Housekeeping', 'ServiceStaff', 'Admin')),
    Username NVARCHAR(50) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    Phone NVARCHAR(20),
    Email NVARCHAR(100)
);

-- ==========================
-- 2. Guest Table
-- ==========================
CREATE TABLE GUEST (
    GuestID INT IDENTITY(1,1) PRIMARY KEY,
    FullName NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(20) UNIQUE,
    Email NVARCHAR(100) UNIQUE,
    Address NVARCHAR(200),
    IDNumber NVARCHAR(50) NOT NULL UNIQUE, 
    DateOfBirth DATE,
    Username NVARCHAR(50) NOT NULL UNIQUE,
    Password NVARCHAR(255) NOT NULL
);

-- ==========================
-- 3. Room Type Table
-- ==========================
CREATE TABLE ROOM_TYPE (
    RoomTypeID INT IDENTITY(1,1) PRIMARY KEY,
    TypeName NVARCHAR(50) NOT NULL,
    Capacity INT NOT NULL CHECK (Capacity > 0),
    PricePerNight DECIMAL(10,2) NOT NULL CHECK (PricePerNight >= 0)
);

-- ==========================
-- 4. Room Table
-- ==========================
CREATE TABLE ROOM (
    RoomID INT IDENTITY(1,1) PRIMARY KEY,
    RoomNumber NVARCHAR(10) UNIQUE NOT NULL,
    RoomTypeID INT NOT NULL,
    Status NVARCHAR(20) CHECK (Status IN ('Available', 'Occupied', 'Dirty', 'Maintenance')),
    FOREIGN KEY (RoomTypeID) REFERENCES ROOM_TYPE(RoomTypeID)
);

-- ==========================
-- 5. Booking Table
-- ==========================
CREATE TABLE BOOKING (
    BookingID INT IDENTITY(1,1) PRIMARY KEY,
    GuestID INT NOT NULL,
    RoomID INT NOT NULL,
    CheckInDate DATE NOT NULL,
    CheckOutDate DATE NOT NULL,
    BookingDate DATE DEFAULT GETDATE(),
    Status NVARCHAR(20) CHECK (Status IN ('Reserved', 'Checked-in', 'Checked-out', 'Canceled')),
    TotalGuests INT DEFAULT 1,
    SpecialRequests NVARCHAR(500),
    PaymentStatus NVARCHAR(20) CHECK (PaymentStatus IN ('Pending', 'Deposit Paid', 'Guaranteed', 'Paid')) DEFAULT 'Pending',
    CancellationDate DATE,
    CancellationReason NVARCHAR(255),
    FOREIGN KEY (GuestID) REFERENCES GUEST(GuestID),
    FOREIGN KEY (RoomID) REFERENCES ROOM(RoomID),
    CHECK (CheckOutDate > CheckInDate)
);

-- ==========================
-- 6. Service Table
-- ==========================
CREATE TABLE SERVICE (
    ServiceID INT IDENTITY(1,1) PRIMARY KEY,
    ServiceName NVARCHAR(100) NOT NULL,
    ServiceType NVARCHAR(50),
    Price DECIMAL(10,2) NOT NULL CHECK (Price >= 0)
);

-- ==========================
-- 7. Booking_Service Table
-- ==========================
CREATE TABLE BOOKING_SERVICE (
    Booking_Service_ID INT IDENTITY(1,1) PRIMARY KEY,
    BookingID INT NOT NULL,
    ServiceID INT NOT NULL,
    Quantity INT DEFAULT 1 CHECK (Quantity > 0),
    ServiceDate DATE DEFAULT GETDATE(),
    Status NVARCHAR(20) CHECK (Status IN ('Requested', 'In Progress', 'Completed')) DEFAULT 'Requested',
    AssignedStaffID INT,
    RequestTime DATETIME DEFAULT GETDATE(),
    CompletionTime DATETIME,
    FOREIGN KEY (BookingID) REFERENCES BOOKING(BookingID),
    FOREIGN KEY (ServiceID) REFERENCES SERVICE(ServiceID),
    FOREIGN KEY (AssignedStaffID) REFERENCES STAFF(StaffID)
);

-- ==========================
-- 8. Tax Configuration Table
-- ==========================
CREATE TABLE TAX_CONFIG (
    TaxConfigID INT IDENTITY(1,1) PRIMARY KEY,
    TaxName NVARCHAR(100) NOT NULL,
    TaxRate DECIMAL(5,2) NOT NULL,
    Description NVARCHAR(255),
    EffectiveFrom DATE NOT NULL,
    EffectiveTo DATE NULL,
    CreatedBy INT NOT NULL,
    CreatedDate DATETIME DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1,
    FOREIGN KEY (CreatedBy) REFERENCES STAFF(StaffID)
);

-- ==========================
-- 9. Invoice Table
-- ==========================
CREATE TABLE INVOICE (
    InvoiceID INT IDENTITY(1,1) PRIMARY KEY,
    BookingID INT NOT NULL UNIQUE,
    IssueDate DATE DEFAULT GETDATE(),
    TotalAmount DECIMAL(12,2) NOT NULL CHECK (TotalAmount >= 0),
    Status NVARCHAR(20) CHECK (Status IN ('Unpaid', 'Paid', 'Canceled')),
    RoomCharges DECIMAL(12,2) DEFAULT 0,
    ServiceCharges DECIMAL(12,2) DEFAULT 0,
    TaxConfigID INT,
    TaxAmount DECIMAL(12,2) DEFAULT 0,
    Discount DECIMAL(12,2) DEFAULT 0,
    FinalAmount DECIMAL(12,2) DEFAULT 0,
    FOREIGN KEY (BookingID) REFERENCES BOOKING(BookingID),
    FOREIGN KEY (TaxConfigID) REFERENCES TAX_CONFIG(TaxConfigID),
    CONSTRAINT CHK_Invoice_Amounts CHECK (
        RoomCharges >= 0 AND
        ServiceCharges >= 0 AND
        TaxAmount >= 0 AND
        Discount >= 0 AND
        FinalAmount >= 0
    )
);

-- ==========================
-- 10. Payment Table
-- ==========================
CREATE TABLE PAYMENT (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    BookingID INT NOT NULL,
    PaymentDate DATE DEFAULT GETDATE(),
    Amount DECIMAL(12,2) NOT NULL CHECK (Amount >= 0),
    PaymentMethod NVARCHAR(50) CHECK (PaymentMethod IN ('Cash', 'Credit Card', 'Debit Card', 'Online')),
    Status NVARCHAR(20) CHECK (Status IN ('Pending', 'Completed', 'Failed')),
    FOREIGN KEY (BookingID) REFERENCES BOOKING(BookingID)
);

-- ==========================
-- 11. Housekeeping Task Table
-- ==========================
CREATE TABLE HOUSEKEEPING_TASK (
    TaskID INT IDENTITY(1,1) PRIMARY KEY,
    RoomID INT NOT NULL,
    StaffID INT,
    TaskType NVARCHAR(50) CHECK (TaskType IN ('Regular', 'Deep', 'Post-Checkout')),
    Status NVARCHAR(20) CHECK (Status IN ('Pending', 'In Progress', 'Completed')),
    AssignedDate DATE DEFAULT GETDATE(),
    CompletedDate DATE,
    Priority NVARCHAR(20) CHECK (Priority IN ('Low', 'Medium', 'High', 'Urgent')),
    FOREIGN KEY (RoomID) REFERENCES ROOM(RoomID),
    FOREIGN KEY (StaffID) REFERENCES STAFF(StaffID)
);

-- ==========================
-- 12. Maintenance Issue Table
-- ==========================
CREATE TABLE MAINTENANCE_ISSUE (
    IssueID INT IDENTITY(1,1) PRIMARY KEY,
    RoomID INT NOT NULL,
    ReportedByStaffID INT NOT NULL,
    IssueDescription NVARCHAR(500),
    ReportDate DATE DEFAULT GETDATE(),
    Status NVARCHAR(20) CHECK (Status IN ('Reported', 'In Progress', 'Resolved')),
    ResolvedByStaffID INT,
    ResolutionDate DATE,
    FOREIGN KEY (RoomID) REFERENCES ROOM(RoomID),
    FOREIGN KEY (ReportedByStaffID) REFERENCES STAFF(StaffID),
    FOREIGN KEY (ResolvedByStaffID) REFERENCES STAFF(StaffID)
);

-- ==========================
-- INDEXES FOR OPTIMIZATION
-- ==========================

-- Tối ưu query kiểm tra phòng trống
CREATE INDEX IX_Booking_Room_Dates ON BOOKING(RoomID, CheckInDate, CheckOutDate) 
INCLUDE (Status);

CREATE INDEX IX_Room_Status_Type ON ROOM(Status, RoomTypeID);

-- Tối ưu các report và tìm kiếm booking
CREATE INDEX IX_Booking_Status_Dates ON BOOKING(Status, CheckInDate, CheckOutDate);

-- Tối ưu quản lý công việc dọn phòng
CREATE INDEX IX_Housekeeping_Status_Date ON HOUSEKEEPING_TASK(Status, AssignedDate);

-- Tối ưu quản lý dịch vụ
CREATE INDEX IX_BookingService_Status_Date ON BOOKING_SERVICE(Status, ServiceDate);

-- ==========================
-- SAMPLE DATA
-- ==========================

-- STAFF (Insert trước vì các bảng khác reference)
INSERT INTO STAFF (FullName, Role, Username, Password, Phone, Email)
VALUES
(N'Nguyễn Dương Thuận', 'Admin', 'admin', '123', '0901234567', 'admin@hotel.com'),
(N'Trần Thị Hoa', 'Manager', 'manager', '111', '0907654321', 'manager@hotel.com'),
(N'Lê Quốc Huy', 'Receptionist', 'recept', '123', '0911111111', 'recept@hotel.com'),
(N'Phạm Thị Mai', 'Housekeeping', 'house', '123', '0922222222', 'house@hotel.com'),
(N'Đặng Văn Bình', 'ServiceStaff', 'service', '123', '0933333333', 'service@hotel.com');

-- GUEST
INSERT INTO GUEST (FullName, Phone, Email, Address, IDNumber, DateOfBirth, Username, Password)
VALUES
(N'Lê Minh Tuấn', '0988888881', 'tuan@gmail.com', N'Gia Lai', '0123456789', '1990-05-10', 'tuan', '123'),
(N'Lê Thị Hồng', '0988888882', 'hong@gmail.com', N'Đà Nẵng', '9876543210', '1995-02-14', 'hong', '123'),
(N'Trần Quốc Bảo', '0988888883', 'bao@gmail.com', N'TP.HCM', '4567891230', '1987-11-22', 'bao', '123');

-- ROOM_TYPE
INSERT INTO ROOM_TYPE (TypeName, Capacity, PricePerNight)
VALUES 
(N'Single', 1, 50.00),
(N'Double', 2, 80.00),
(N'Suite', 4, 150.00);

-- ROOM
INSERT INTO ROOM (RoomNumber, RoomTypeID, Status)
VALUES 
('101', 1, 'Available'),
('102', 2, 'Available'),
('201', 3, 'Dirty'),
('202', 2, 'Occupied'),
('203', 3, 'Available'),
('301', 3, 'Maintenance');

-- SERVICE
INSERT INTO SERVICE (ServiceName, ServiceType, Price)
VALUES
(N'Breakfast', 'Food', 10.00),
(N'Laundry', 'Laundry', 5.00),
(N'Spa Massage', 'Spa', 30.00);

-- TAX_CONFIG
INSERT INTO TAX_CONFIG (TaxName, TaxRate, Description, EffectiveFrom, CreatedBy)
VALUES
(N'VAT', 10.00, N'Thuế giá trị gia tăng 10%', '2025-01-01', 1);

-- BOOKING
INSERT INTO BOOKING (GuestID, RoomID, CheckInDate, CheckOutDate, Status, TotalGuests, SpecialRequests, PaymentStatus)
VALUES
(1, 1, '2025-10-08', '2025-10-10', 'Checked-in', 1, N'Gần cửa sổ', 'Deposit Paid'),
(2, 2, '2025-10-09', '2025-10-12', 'Reserved', 2, N'Cần thêm gối', 'Pending'),
(3, 3, '2025-10-05', '2025-10-07', 'Checked-out', 1, NULL, 'Paid');

-- BOOKING_SERVICE
INSERT INTO BOOKING_SERVICE (BookingID, ServiceID, Quantity, Status, AssignedStaffID, ServiceDate, CompletionTime)
VALUES
(1, 1, 2, 'Completed', 5, '2025-10-08', '2025-10-08 09:00'),
(1, 2, 1, 'In Progress', 5, '2025-10-09', NULL),
(3, 3, 1, 'Completed', 5, '2025-10-06', '2025-10-06 18:00');

-- INVOICE
INSERT INTO INVOICE (BookingID, TotalAmount, Status, RoomCharges, ServiceCharges, TaxConfigID, TaxAmount, Discount, FinalAmount)
VALUES
(1, 132.00, 'Paid', 100.00, 20.00, 1, 12.00, 0, 132.00),
(3, 165.00, 'Paid', 150.00, 0.00, 1, 15.00, 0, 165.00);

-- PAYMENT
INSERT INTO PAYMENT (BookingID, Amount, PaymentMethod, Status)
VALUES
(1, 132.00, 'Credit Card', 'Completed'),
(3, 165.00, 'Cash', 'Completed');

-- HOUSEKEEPING_TASK
INSERT INTO HOUSEKEEPING_TASK (RoomID, StaffID, TaskType, Status, AssignedDate, Priority)
VALUES
(1, 4, 'Regular', 'In Progress', '2025-10-09', 'Medium'),
(2, NULL, 'Post-Checkout', 'Pending', '2025-10-09', 'High'),
(3, 4, 'Deep', 'Completed', '2025-10-07', 'High');

-- MAINTENANCE_ISSUE
INSERT INTO MAINTENANCE_ISSUE (RoomID, ReportedByStaffID, IssueDescription, Status, ResolvedByStaffID, ResolutionDate)
VALUES
(2, 4, N'Máy lạnh không hoạt động', 'Resolved', 2, '2025-10-08'),
(4, 2, N'Cửa phòng bị kẹt', 'Reported', NULL, NULL);

GO