package ru.itis.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {
    private Long idOfUser;
    private String firstNameOfUser;
    private String surnameOfUser;
    private String countryOfUser;
    private String cityOfUser;
    private Date ageOfUser;
    private String genderOfUser;
    private String usernameOfUser;
    private String passwordOfUser;
}
