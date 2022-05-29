package com.vm.vmmanager.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z]{3,20}")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,32}")
    private String password;

    @Email
    @Size(max = 50)
    private String email;

    @Size(max = 15)
    @Pattern(regexp = "(^[0-9]{15})")
    private String mobileNumber;

    private String role;

}
