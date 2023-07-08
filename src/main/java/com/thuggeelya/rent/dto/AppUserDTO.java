package com.thuggeelya.rent.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppUserDTO {

    private String lastName;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
}
