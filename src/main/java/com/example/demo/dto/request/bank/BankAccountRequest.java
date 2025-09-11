package com.example.demo.dto.request.bank;

import com.example.demo.utils.enums.BankAccountStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class BankAccountRequest {

    @NotNull(message = "User ID is required")
    Long id;

    @NotBlank(message = "Account number is required")
    @Size(min = 5, max = 50, message = "Account number must be between 5 and 50 characters")
    String accountNumber;

    BankAccountStatus status;
}
