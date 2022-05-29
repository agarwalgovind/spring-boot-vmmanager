package com.vm.vmmanager.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponseDto {

    private final String token;
    private final String type = "Bearer";
    private final String username;
    private final String email;

}
