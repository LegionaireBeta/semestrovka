package ru.itis.repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.dto.SignUpForm;
import ru.itis.model.User;

import java.sql.SQLException;

public class SignUpServiceImpl implements SignUpService{

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    public SignUpServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Override
    public void signUp(SignUpForm form) throws SQLException {

        User user = User.builder()
                .firstNameOfUser(form.getFirstName())
                .surnameOfUser(form.getSurname())
                .ageOfUser(form.getAge())
                .genderOfUser(form.getGender())
                .countryOfUser(form.getCountry())
                .cityOfUser(form.getCity())
                .usernameOfUser(form.getUsername())
                .passwordOfUser(passwordEncoder.encode(form.getPassword()))
                .build();

        accountRepository.save(user);

    }
}
