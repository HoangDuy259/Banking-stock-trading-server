package com.example.demo.dto.request.bank;

import com.example.demo.utils.enums.AccountStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BankAccountRequest {

    @NotNull(message = "User ID is required")
    Long id;

    AccountStatus status;
}
