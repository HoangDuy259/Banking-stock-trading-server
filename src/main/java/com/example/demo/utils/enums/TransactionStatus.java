package com.example.demo.utils.enums;

public enum TransactionStatus {
    START,
    PENDING,    // giao dịch đang chờ xử lý
    SUCCESS,    // giao dịch thành công
    FAILED,     // giao dịch thất bại
    CANCELLED   // giao dịch bị hủy
}
