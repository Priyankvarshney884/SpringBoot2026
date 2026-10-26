package com.example.Spring_starter.mapper;

import com.example.Spring_starter.dto.AddressDto;
import com.example.Spring_starter.dto.UserRequestDto;
import com.example.Spring_starter.dto.UserResponseDto;
import com.example.Spring_starter.model.Address;
import com.example.Spring_starter.model.User;
import org.springframework.stereotype.Component;

/** API DTOs aur persistence entities ke beech field mapping karta hai. */
@Component
public class UserMapper {

    public User toEntity(UserRequestDto request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setRole(request.role());
        user.setAddress(toAddressEntity(request.address()));
        return user;
    }

    public UserResponseDto toResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                toAddressDto(user.getAddress())
        );
    }

    private Address toAddressEntity(AddressDto addressDto) {
        // Address optional hai; null input par entity relationship bhi null rahe.
        if (addressDto == null) {
            return null;
        }

        Address address = new Address();
        address.setStreet(addressDto.street());
        address.setCity(addressDto.city());
        address.setState(addressDto.state());
        address.setPostalCode(addressDto.postalCode());
        address.setCountry(addressDto.country());
        return address;
    }

    private AddressDto toAddressDto(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry()
        );
    }
}
