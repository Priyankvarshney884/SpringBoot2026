package com.example.Spring_starter.dto;

import com.example.Spring_starter.model.UserRole;

/** Client ko user operation ke baad bheje jaane wale fields ka API contract. */
public record UserResponseDto(
        Long id,
        String name,
        String email,
        String phone,
        UserRole role,
        AddressDto address
) {
}
