package com.example.Spring_starter.dto;

import com.example.Spring_starter.model.UserRole;

/** Client se aane wale user create/update fields ka API contract. */
public record UserRequestDto(
        String name,
        String email,
        String phone,
        UserRole role,
        AddressDto address
) {
}
