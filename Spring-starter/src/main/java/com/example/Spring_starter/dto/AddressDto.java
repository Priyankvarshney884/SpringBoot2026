package com.example.Spring_starter.dto;

/** API boundary par address fields carry karne wala immutable DTO. */
public record AddressDto(
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {
}
