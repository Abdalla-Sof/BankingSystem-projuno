package com.banking.accountservice.dto;

import com.banking.accountservice.entity.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountRequest {

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @NotNull(message = "Account type is required")
    private AccountType accountType;
}