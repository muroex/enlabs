package com.enlabs.model.dto;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    @Length(min=2, max = 20)
    @NotEmpty(message = "Please provide your name")
    private String firstName;

    @Length(min=2, max = 20)
    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    @NotEmpty(message = "Please provide an email")
    @Length(min=6, max = 64)
    private String password;

    @Email
    @NotEmpty(message = "Please provide an email")
    private String email;

}
