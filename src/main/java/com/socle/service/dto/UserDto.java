package com.socle.service.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.socle.service.annotation.UniqueUser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "firstName cannot be null")
    private String firstName;

    @NotBlank(message = "lastName cannot be null")
    private String lastName;

    @UniqueUser
    @Email(message = "Email should be valid")
    @NotBlank(message = "e-mail is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

}
