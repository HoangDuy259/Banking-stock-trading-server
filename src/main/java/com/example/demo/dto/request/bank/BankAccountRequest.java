package com.example.demo.dto.request.bank;

import com.example.demo.utils.enums.BankAccountStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class BankAccountRequest {

    @NotNull(message = "User ID is required")
    Long id;

    BankAccountStatus status;
}
