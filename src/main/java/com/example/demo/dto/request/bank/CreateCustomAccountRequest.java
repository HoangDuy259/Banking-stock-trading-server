package com.example.demo.dto.request.bank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomAccountRequest
{
    @NotNull(message = "Account Number is required")
    String accountNumber;
}