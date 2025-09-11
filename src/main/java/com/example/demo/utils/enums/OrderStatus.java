package com.example.demo.utils.enums;

public enum OrderStatus {
    PENDING,         // Đơn hàng mới tạo, đang chờ khớp
    FILLED,          // Đã khớp hoàn toàn
    PARTIAL_FILLED,  // Khớp một phần
    CANCELLED,       // Bị hủy
    REJECTED;        // Bị từ chối
}
