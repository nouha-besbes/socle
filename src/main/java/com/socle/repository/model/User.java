package com.socle.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "T_USER")
public class User extends Base implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "USR_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "USR_LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "USR_EMAIL_ADRESS", nullable = false)
    private String email;

    @Column(name = "USR_PASSWORD", nullable = false)
    private String password;

}
