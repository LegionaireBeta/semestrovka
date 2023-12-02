package ru.itis.dto;


import lombok.Data;

import java.util.Date;

@Data
public class SignUpForm {
    private String firstName;
    private String surname;
    private String country;
    private String city;
    private Date age;
    private String gender;
    private String username;
    private String password;
}
