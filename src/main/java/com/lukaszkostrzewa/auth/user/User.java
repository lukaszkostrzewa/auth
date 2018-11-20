package com.lukaszkostrzewa.auth.user;

import com.lukaszkostrzewa.auth.user.validator.UniqueLogin;
import com.lukaszkostrzewa.auth.user.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Lukasz Kostrzewa (SG0221165)
 * @since Nov 17, 2018
 */
@Entity
@Data
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(unique = true)
    @Size(min = 5, message = "{user.validation.login.length}")
    @UniqueLogin(groups = New.class)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{user.validation.login.alphaNumeric}")
    private String login;
    @NotNull
    @ValidPassword
    @Size(min = 8, message = "{user.validation.password.length}")
    private String password;

    interface New {
    }

    public interface Existing {
    }
}
